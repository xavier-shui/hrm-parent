package cn.xavier.hrm.service;

import cn.xavier.hrm.domain.SystemDictionary;
import cn.xavier.hrm.util.AjaxResult;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhengwei-shui
 * @since 2021-12-22
 */
public interface ISystemDictionaryService extends IService<SystemDictionary> {

    AjaxResult getCourseLevel(String type);
}
