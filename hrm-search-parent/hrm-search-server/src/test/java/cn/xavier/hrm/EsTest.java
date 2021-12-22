package cn.xavier.hrm;

import cn.xavier.hrm.doc.CourseDoc;
import cn.xavier.hrm.doc.EmployeeDoc;
import cn.xavier.hrm.repository.EmployeeDocRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Zheng-Wei Shui
 * @date 12/22/2021
 */
@SpringBootTest(classes = SearchApp.class)
@RunWith(SpringRunner.class)
public class EsTest {

    @Autowired
    private EmployeeDocRepository docRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void testCreateIndex() throws Exception {
/*        // 根据@Document创建索引
        elasticsearchTemplate.createIndex(EmployeeDoc.class);
        // 根据@Field创建映射
        elasticsearchTemplate.putMapping(EmployeeDoc.class);*/
        elasticsearchTemplate.createIndex(CourseDoc.class);
        // 根据@Field创建映射
        elasticsearchTemplate.putMapping(CourseDoc.class);
    }

    @Test
    public void testAdd() throws Exception {
        docRepository.save(new EmployeeDoc(1L, "lucy", 20));
    }
}
