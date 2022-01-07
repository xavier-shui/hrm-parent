package cn.xavier.hrm.service;

import cn.xavier.hrm.domain.PayFlow;
import cn.xavier.hrm.dto.AlipayNotifyDto;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author terrylv
 * @since 2021-03-21
 */
public interface IPayFlowService extends IService<PayFlow> {

    //支付
    String pay(String orderNo);

    //根据流水号查询流水
    PayFlow selectByNo(String payNo);

    //支付异步回调
    String payNotify(AlipayNotifyDto dto);
}
