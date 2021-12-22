package cn.xavier.hrm.repository;

import cn.xavier.hrm.doc.EmployeeDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Zheng-Wei Shui
 * @date 12/22/2021
 */
@Repository
public interface EmployeeDocRepository extends ElasticsearchRepository<EmployeeDoc, Long> {
}
