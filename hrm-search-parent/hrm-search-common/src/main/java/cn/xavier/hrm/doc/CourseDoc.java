package cn.xavier.hrm.doc;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "hrm" , type = "course")
@Data
public class CourseDoc {
    //PUT  /hrmtest/coursetest/1
    @Id //文档对象的ID就是该字段的值
    private Long id;
    //课程名称
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String name;
    //价格
    @Field(type = FieldType.Float)
    private Float price;
    //原价
    @Field(type = FieldType.Float)
    private Float priceOld;
    //适用人群
    @Field(type = FieldType.Keyword,index = false)
    private String forUser;
    //课程类型
    @Field(type = FieldType.Long)
    private Long courseTypeId;
    // 支持聚合
    @Field(type = FieldType.Text,analyzer = "ik_smart",searchAnalyzer = "ik_smart", fielddata = true)
    private String courseTypeName;
    //等级名
    @Field(type = FieldType.Keyword)
    private String gradeName;
    //机构
    @Field(type = FieldType.Keyword)
    private String tenantName;
    @Field(type = FieldType.Long)
    private Long tenantId;
    //图片
    @Field(type = FieldType.Keyword,index = false)
    private String pic;
    //销量
    @Field(type = FieldType.Integer)
    private Integer saleCount = 0;
    //浏览量
    @Field(type = FieldType.Integer)
    private Integer viewCount = 0;
    //评论数
    @Field(type = FieldType.Integer)
    private Integer commentCount = 0;
    //是否收费
    @Field(type = FieldType.Integer)
    private Integer charge;
    //上线时间
    @Field(type = FieldType.Date)
    private Date onlineTime;
}