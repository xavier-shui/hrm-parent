package cn.xavier.hrm.vo;

import cn.xavier.hrm.domain.CourseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zheng-Wei Shui
 * @date 12/23/2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrumbsVo {
    private CourseType own;
    private List<CourseType> siblings = new ArrayList<>();
}
