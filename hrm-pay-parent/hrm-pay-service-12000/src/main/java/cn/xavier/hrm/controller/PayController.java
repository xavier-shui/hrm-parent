package cn.xavier.hrm.controller;

import cn.xavier.hrm.domain.PayFlow;
import cn.xavier.hrm.dto.AlipayNotifyDto;
import cn.xavier.hrm.service.IPayFlowService;
import cn.xavier.hrm.util.AjaxResult;
import cn.xavier.hrm.util.ValidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@Slf4j
public class PayController {

    @Autowired
    private IPayFlowService payFlowService ;

    @RequestMapping("/pay/{orderNo}")
    public void pay(@PathVariable("orderNo")String orderNo, HttpServletResponse response){
        String html = payFlowService.pay(orderNo);
        log.info("支付申请结果：{}",html);
        try {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(html);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/alipay/notify")
    public String notify(AlipayNotifyDto dto){
        log.info("异步回调：{}",dto);
        return payFlowService.payNotify(dto);
    }

    @RequestMapping("/alipay/return")
    public AjaxResult synchronous(AlipayNotifyDto dto){
        log.info("同步回调：{}",dto);
        String out_trade_no = dto.getOut_trade_no();
        ValidUtils.assertNotNull(out_trade_no,"订单无效");
        PayFlow payFlow = payFlowService.selectByNo(out_trade_no);
        ValidUtils.assertNotNull(payFlow,"订单无效");
        return AjaxResult.me().setResultObj(payFlow);
    }
}
