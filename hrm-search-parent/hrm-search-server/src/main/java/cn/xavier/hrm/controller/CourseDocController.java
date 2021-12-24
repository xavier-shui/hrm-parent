package cn.xavier.hrm.controller;

import cn.xavier.hrm.config.HighlightResultMapper;
import cn.xavier.hrm.doc.CourseDoc;
import cn.xavier.hrm.query.CourseDocQuery;
import cn.xavier.hrm.repository.CourseDocRepository;
import cn.xavier.hrm.util.AjaxResult;
import cn.xavier.hrm.util.PageList;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public AjaxResult onlineCourse(@RequestBody List<CourseDoc> courseDocs) {
        repository.saveAll(courseDocs);
        return AjaxResult.me();
    }

    @PostMapping("/offlineCourse")
    public AjaxResult offlineCourse(@RequestBody List<CourseDoc> courseDocs) {
        repository.deleteAll(courseDocs);
        return AjaxResult.me();
    }

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
        builder.withQuery(boolQueryBuilder);

        // 分页
        builder.withPageable(PageRequest.of(courseDocQuery.getPage() - 1, courseDocQuery.getRows()));

        // 排序 Enum.valueOf(SortOrder, courseDocQuery.getSortType().toLowerCase())
        // 如果前端没传，默认用价格排序
        String sortField = courseDocQuery.getSortField();
        sortField = StringUtils.isEmpty(sortField) ? "price" : sortField;
        builder.withSort(new FieldSortBuilder(sortField).order(SortOrder.fromString(courseDocQuery.getSortType())));

        // repository不支持高亮
        // Page<CourseDoc> searchResult = repository.search(builder.build());
        AggregatedPage<CourseDoc> searchResult = elasticsearchTemplate.queryForPage(builder.build(), CourseDoc.class, highlightResultMapper);
        return AjaxResult.me().setResultObj(new PageList<>(searchResult.getTotalElements(), searchResult.getContent()));
    }
}

