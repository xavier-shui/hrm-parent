package cn.xavier.hrm.service;

import cn.xavier.hrm.domain.KillCourse;
import cn.xavier.hrm.dto.KillDto;
import cn.xavier.hrm.util.AjaxResult;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author terrylv
 * @since 2021-03-21
 */
public interface IKillCourseService extends IService<KillCourse> {

    //添加秒杀课程
    AjaxResult add(KillCourse killCourse);

    //查询已经发布的秒杀课程
    AjaxResult publishs();

    //查询某一个已经发布的秒杀课程
    AjaxResult publishsOne(Long id);

    //执行秒杀
    AjaxResult kill(KillDto killDto);
}
