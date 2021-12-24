package cn.xavier.hrm.query;

import lombok.Data;

/**
 * @author Zheng-Wei Shui
 * @date 12/23/2021
 */
@Data
public class CourseDocQuery {
    private String keyword;
    private Long courseTypeId;
    private Float priceMin;
    private Float priceMax;
    private String courseTypeName;
    private String tenantName;
    private String sortField;
    private String sortType;
    private Integer page;
    private Integer rows;
}
