package cn.xavier.hrm.service.impl;

import cn.xavier.hrm.domain.SystemDictionary;
import cn.xavier.hrm.mapper.SystemDictionaryMapper;
import cn.xavier.hrm.service.ISystemDictionaryService;
import cn.xavier.hrm.util.AjaxResult;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhengwei-shui
 * @since 2021-12-22
 */
@Service
public class SystemDictionaryServiceImpl extends ServiceImpl<SystemDictionaryMapper, SystemDictionary> implements ISystemDictionaryService {

    @Autowired
    private SystemDictionaryMapper systemDictionaryMapper;

    @Override
    public AjaxResult getCourseLevel(String type) {
        List<SystemDictionary> types = systemDictionaryMapper.selectList(
                new EntityWrapper<SystemDictionary>().eq("type", type));
        return AjaxResult.me().setResultObj(types);
    }
}
