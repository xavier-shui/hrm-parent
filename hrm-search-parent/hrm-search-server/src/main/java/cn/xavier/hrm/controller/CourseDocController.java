package cn.xavier.hrm.controller;

import cn.xavier.hrm.config.HighlightResultMapper;
import cn.xavier.hrm.doc.CourseDoc;
import cn.xavier.hrm.query.CourseDocQuery;
import cn.xavier.hrm.repository.CourseDocRepository;
import cn.xavier.hrm.util.AjaxResult;
import cn.xavier.hrm.util.PageListExtra;
import cn.xavier.hrm.util.Pair;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Zheng-Wei Shui
 * @date 12/22/2021
 */
@RestController
@RequestMapping("/courseDoc")
public class CourseDocController {

    @Autowired
    private CourseDocRepository repository;

    @Autowired
    private HighlightResultMapper highlightResultMapper;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @PostMapping("/onlineCourse")
    @PreAuthorize("hasAuthority('courseDoc:add')")
    public AjaxResult onlineCourse(@RequestBody List<CourseDoc> courseDocs) {
        repository.saveAll(courseDocs);
        return AjaxResult.me();
    }

    @PostMapping("/offlineCourse")
    public AjaxResult offlineCourse(@RequestBody List<CourseDoc> courseDocs) {
        repository.deleteAll(courseDocs);
        return AjaxResult.me();
    }

    /*
        GET /hrm/course/_search
        {
            "query": {
            "bool": {
              "must": [
                {
                  "multi_match" : {
                    "query" : "java",
                    "fields" : [ "name", "courseTypeName"],
                    "type" : "best_fields",
                    "tie_breaker" : 0.8
                  }
                }
              ],
              "filter": {
                "range": {
                  "price": {
                    "gte": 25,
                    "lte": 5000
                  }
                }
              }
            }
          },
          "from": 1,
          "size": 3,
          "sort": [
            {
              "price": {
                "order": "desc"
              }
            }
          ],
          "aggs": {
            "courseTypeNameAggs": {
              "terms": {
                "field": "courseTypeName",
                "size": 10
              }
            },
             "tenantNameAggs": {
              "terms": {
                "field": "tenantName",
                "size": 10
              }
            }
          }
    }
    */
    @PostMapping("/queryCourses")
    public AjaxResult queryCourses(@RequestBody CourseDocQuery courseDocQuery) {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();

        //设置高亮显示
        HighlightBuilder.Field field = new HighlightBuilder.Field("name").preTags("<font style='color:red'><b>").postTags("</b></font>");
        builder.withHighlightFields(field);  // 名字高亮

        // 匹配多字段
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        String keyword = courseDocQuery.getKeyword();
        if (!StringUtils.isEmpty(keyword)) {
            boolQueryBuilder.must(QueryBuilders.multiMatchQuery(keyword, "courseTypeName", "name"));
        }

        // 价格过滤, 空的条件要跳过查询，否则结果为空
        // 如果前端没传最高价，只传最低价，所以最高最低分开写
        Float priceMin = courseDocQuery.getPriceMin();
        if (!StringUtils.isEmpty(priceMin)) {
            boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(priceMin));
        }
        Float priceMax = courseDocQuery.getPriceMax();
        if (!StringUtils.isEmpty(priceMax)) {
            boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").lte(priceMax));
        }
        // 机构名字和课程类型
        String tenantName = courseDocQuery.getTenantName();
        if (!StringUtils.isEmpty(tenantName)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("tenantName", tenantName));
        }
        String courseTypeName = courseDocQuery.getCourseTypeName();
        if (!StringUtils.isEmpty(courseTypeName)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("courseTypeName", courseTypeName));
        }
        builder.withQuery(boolQueryBuilder);

        // 分页
        builder.withPageable(PageRequest.of(courseDocQuery.getPage() - 1, courseDocQuery.getRows()));

        // 排序 Enum.valueOf(SortOrder, courseDocQuery.getSortType().toLowerCase())
        // 如果前端没传，默认用价格排序
        String sortField = courseDocQuery.getSortField();
        sortField = StringUtils.isEmpty(sortField) ? "price" : sortField;
        builder.withSort(new FieldSortBuilder(sortField).order(SortOrder.fromString(courseDocQuery.getSortType())));

        // 聚合查询，机构名字，课程类型
        builder.addAggregation(AggregationBuilders.terms("tenantNameAggs").field("tenantName").order(Terms.Order.count(false)).size(20));
        builder.addAggregation(AggregationBuilders.terms("courseTypeAggs").field("courseTypeName").order(Terms.Order.count(false)).size(20));

        // repository不支持高亮
        // Page<CourseDoc> searchResult = repository.search(builder.build());
        AggregatedPage<CourseDoc> searchResult = elasticsearchTemplate.queryForPage(builder.build(), CourseDoc.class, highlightResultMapper);

        // 封装聚合结果
        List<Pair<String, Long>> tenantNameVos = getVoPairs(searchResult, "tenantNameAggs");
        List<Pair<String, Long>> courseTypeVos = getVoPairs(searchResult, "courseTypeAggs");

        Map<String, List<Pair<String, Long>>> map = new HashMap<>();
        map.put("tenantNameVos", tenantNameVos);
        map.put("courseTypeVos", courseTypeVos);
        return AjaxResult.me().setResultObj(new PageListExtra<>(searchResult.getTotalElements(), searchResult.getContent(), map));
    }

    // Pair里装名字和数量
    private List<Pair<String, Long>> getVoPairs(AggregatedPage<CourseDoc> searchResult, String tenantNameAggs2) {
        // StringTerms: Result of the {@link TermsAggregator} when the field is a String.
        StringTerms tenantNameAggs = (StringTerms) searchResult.getAggregation(tenantNameAggs2);
        List<StringTerms.Bucket> tenantNameAggsBuckets = tenantNameAggs.getBuckets();
        return tenantNameAggsBuckets.stream().map(tenantName -> new Pair<String, Long>
                (tenantName.getKeyAsString(), tenantName.getDocCount())
        ).collect(Collectors.toList());
    }
}
