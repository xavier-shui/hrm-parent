package cn.xavier.hrm.doc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author Zheng-Wei Shui
 * @date 12/22/2021
 */
@Document(indexName = "xavier", type = "employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDoc {
    @Id
    private Long id;
    @Field(type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Integer)
    private Integer age;
}
