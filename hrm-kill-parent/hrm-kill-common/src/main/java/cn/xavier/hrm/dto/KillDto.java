package cn.xavier.hrm.dto;

import lombok.Data;

@Data
public class KillDto {
    private Long killCourseId;
    private String killCode;
    private int killCount = 1;
    private Long userId;
}
