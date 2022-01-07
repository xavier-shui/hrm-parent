package cn.xavier.hrm.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class PayResultToMQ {
    public static final String WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
    public static final String TRADE_CLOSED = "TRADE_CLOSED";
    public static final String TRADE_SUCCESS = "TRADE_SUCCESS";
    public static final String TRADE_FINISHED = "TRADE_FINISHED";

    protected String out_trade_no;
    //交易状态：WAIT_BUYER_PAY（交易创建，等待买家付款）、TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、TRADE_SUCCESS（交易支付成功）、TRADE_FINISHED（交易结束，不可退款）
    protected String trade_status;
    protected String total_amount;
    protected String code;
    protected String msg;

    @JSONField(serialize = false)
    public boolean isTradeSuccess(){
        return this.trade_status.equals(TRADE_SUCCESS) || this.trade_status.equals(TRADE_FINISHED);
    }
    @JSONField(serialize = false)
    public boolean isTradeWit(){
        return this.trade_status.equals(WAIT_BUYER_PAY);
    }
    @JSONField(serialize = false)
    public boolean isTradeExpire(){
        return this.trade_status.equals(TRADE_CLOSED);
    }
}
