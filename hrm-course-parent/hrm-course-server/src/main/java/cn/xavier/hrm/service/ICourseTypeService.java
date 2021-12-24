package cn.xavier.hrm.service;

import cn.xavier.hrm.domain.CourseType;
import cn.xavier.hrm.util.AjaxResult;
import cn.xavier.hrm.vo.CrumbsVo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 课程目录 服务类
 * </p>
 *
 * @author zhengwei-shui
 * @since 2021-12-20
 */
public interface ICourseTypeService extends IService<CourseType> {
    /**
     * 无限极树
     *
     * @return the tree data
     */
    AjaxResult getTreeData();

    AjaxResult getCrumbs(Long courseTypeId);
}
