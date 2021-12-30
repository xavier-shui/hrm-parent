package test;

import cn.xavier.hrm.CourseApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Types;

/**
 * @author Zheng-Wei Shui
 * @date 12/29/2021
 */
@SpringBootTest(classes = {CourseApp.class})
@RunWith(SpringRunner.class)
public class JdbcTemplateTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testJdbcTemplate() throws Exception {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("insert into t_system_dictionary (type, value) values (?, ?)",
                new Object[] { "Lucy", "lucy" }, new int[] {
                        Types.VARCHAR, Types.VARCHAR });
        // jdbcTemplate.getDataSource().getConnection().commit();
    }
}
