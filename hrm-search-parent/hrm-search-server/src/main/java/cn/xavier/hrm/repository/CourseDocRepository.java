package cn.xavier.hrm.repository;

import cn.xavier.hrm.doc.CourseDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Zheng-Wei Shui
 * @date 12/22/2021
 */
@Repository // 若定义在hrm-search-common里面会被其他微服务引用到，创建bean会出错，缺elasticSearchTemplate，没配ES
public interface CourseDocRepository extends ElasticsearchRepository<CourseDoc, Long> {
}
