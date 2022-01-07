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
@TableName("t_pay_flow")
public class PayFlow extends Model<PayFlow> {

    private static final long serialVersionUID = 1L;
    //0.支付中，1.支付成功，3.支付失败
    public static final Integer STATUS_APPLY = 0;
    public static final Integer STATUS_SUCCESS = 1;
    public static final Integer STATUS_FAIL = 2;
    //支付方式：阿里支付
    public static final Integer TYPE_BUY_ALI = 1;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 流水创建
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 发生金额
     */
    private BigDecimal amount;
    /**
     * 支付方式:0余额直接，1支付宝，2微信,3京东支付，4蚂蚁花呗
     */
    @TableField("pay_type")
    private Integer payType;
    /**
     * 业务ID，可以关联订单ID
     */
    @TableField("relation_id")
    private Long relationId;
    /**
     * 支付编号，可以用作订单编号
     */
    @TableField("pay_no")
    private String payNo;
    /**
     * 0.支付中，1.支付成功，3.支付失败
     */
    @TableField("pay_status")
    private Integer payStatus;
    /**
     * 支付描述
     */
    private String intro;
    /**
     * 支付结果描述，错误原因
     */
    @TableField("result_desc")
    private String resultDesc;
    private Integer version;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
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
        return "PayFlow{" +
        ", id=" + id +
        ", createTime=" + createTime +
        ", amount=" + amount +
        ", payType=" + payType +
        ", relationId=" + relationId +
        ", payNo=" + payNo +
        ", payStatus=" + payStatus +
        ", intro=" + intro +
        ", resultDesc=" + resultDesc +
        ", version=" + version +
        "}";
    }
}
