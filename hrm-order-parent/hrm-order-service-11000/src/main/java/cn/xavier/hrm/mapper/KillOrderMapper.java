package cn.xavier.hrm.mapper;

import cn.xavier.hrm.domain.KillOrder;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author terrylv
 * @since 2021-03-21
 */
public interface KillOrderMapper extends BaseMapper<KillOrder> {


    //根据单号查询订单
    KillOrder selectByOrderSn(String orderNo);
}
