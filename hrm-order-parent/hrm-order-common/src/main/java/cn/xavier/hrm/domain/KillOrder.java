package cn.xavier.hrm.domain;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author terrylv
 * @since 2021-03-21
 */
@TableName("t_kill_order")
public class KillOrder extends Model<KillOrder> {

    //订单状态 ：
    //0下单成功待支付，
    //1支付成功待出货，
    //2出货成功订单完成
    //3用户手动取消订单(未支付)
    //4.支付失败/超时自动订单取消,
    //5.出货失败
    public static int STATE_ORDER_UN_PAY = 0;
    public static int STATE_ORDER_PAY_SUCCESS = 1;
    public static int STATE_ORDER_COMPLETE = 2;
    public static int STATE_ORDER_USER_CANCEL = 3;
    public static int STATE_ORDER_FAIL = 4;
    public static int STATE_ORDER_SHIP_FAIL = 5;

    //支付状态：
    //0待支付 ，
    //1支付完成
    //2支付失败
    //3.订单超时未支付
    public static int STATE_PAY_UN_PAY = 0;
    public static int STATE_PAY_SUCCESS = 1;
    public static int STATE_PAY_FAIL = 2;
    public static int STATE_PAY_OUT_TIME = 3;

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 订单编号
     */
    @TableField("order_no")
    private String orderNo;

    private String title;
    /**
     * 支付总的价格
     */
    @TableField("total_price")
    private BigDecimal totalPrice;
    /**
     * 秒杀数量
     */
    @TableField("kill_count")
    private Integer killCount;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     *  订单状态 ： 
0下单成功待支付，
1支付成功待出货，
2出货成功订单完成 
3用户手动取消订单(未支付)
4.支付失败/超时自动订单取消,
5.出货失败
     */
    @TableField("status_order")
    private Integer statusOrder;
    /**
     * 支付状态：  
0待支付 ，
1支付完成 
2支付失败
3.订单超时未支付
     */
    @TableField("status_pay")
    private Integer statusPay;
    /**
     * 秒杀场次ID
     */
    @TableField("sessions_id")
    private Long sessionsId;
    /**
     * 最后支付更新时间
     */
    @TableField("pay_update_time")
    private Date payUpdateTime;
    /**
     * 用户
     */
    @TableField("user_id")
    private Long userId;
    private Integer version;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getKillCount() {
        return killCount;
    }

    public void setKillCount(Integer killCount) {
        this.killCount = killCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(Integer statusOrder) {
        this.statusOrder = statusOrder;
    }

    public Integer getStatusPay() {
        return statusPay;
    }

    public void setStatusPay(Integer statusPay) {
        this.statusPay = statusPay;
    }

    public Long getSessionsId() {
        return sessionsId;
    }

    public void setSessionsId(Long sessionsId) {
        this.sessionsId = sessionsId;
    }

    public Date getPayUpdateTime() {
        return payUpdateTime;
    }

    public void setPayUpdateTime(Date payUpdateTime) {
        this.payUpdateTime = payUpdateTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "KillOrder{" +
        ", id=" + id +
        ", orderNo=" + orderNo +
        ", totalPrice=" + totalPrice +
        ", killCount=" + killCount +
        ", createTime=" + createTime +
        ", statusOrder=" + statusOrder +
        ", statusPay=" + statusPay +
        ", sessionsId=" + sessionsId +
        ", payUpdateTime=" + payUpdateTime +
        ", userId=" + userId +
        ", version=" + version +
        "}";
    }
}
