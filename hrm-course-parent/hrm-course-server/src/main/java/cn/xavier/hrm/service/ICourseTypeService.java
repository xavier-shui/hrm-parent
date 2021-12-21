package cn.xavier.hrm.service;

import cn.xavier.hrm.domain.CourseType;
import cn.xavier.hrm.util.AjaxResult;
import com.baomidou.mybatisplus.service.IService;

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
}
