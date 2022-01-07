package cn.xavier.hrm.dto;

import lombok.Data;

@Data
public class AlipayNotifyDto extends PayResultToMQ{

    private String charset;
    private String gmt_create;
    private String gmt_payment;
    private String notify_time;
    private String subject;
    private String sign;
    private String buyer_id;
    private String invoice_amount;
    private String version;
    private String notify_id;
    private String fund_bill_list;
    private String notify_type;
    private String trade_no;
    private String auth_app_id;
    private String receipt_amount;
    private String point_amount;
    private String app_id;
    private String buyer_pay_amount;
    private String sign_type;
    private String seller_id;


}