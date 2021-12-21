package cn.xavier.hrm.domain;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhengwei-shui
 * @since 2021-12-20
 */
@TableName("t_course_market")
public class CourseMarket extends Model<CourseMarket> {

    private static final long serialVersionUID = 1L;

    /**
     * 课程id
     */
    private Long id;
    /**
     * 收费规则：，收费1免费，2收费
     */
    private Integer charge;
    /**
     * 营销截止时间
     */
    private Date expires;
    /**
     * 咨询qq
     */
    private String qq;
    /**
     * 价格
     */
    private Float price;
    /**
     * 原价
     */
    @TableField("price_old")
    private Float priceOld;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCharge() {
        return charge;
    }

    public void setCharge(Integer charge) {
        this.charge = charge;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getPriceOld() {
        return priceOld;
    }

    public void setPriceOld(Float priceOld) {
        this.priceOld = priceOld;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CourseMarket{" +
        ", id=" + id +
        ", charge=" + charge +
        ", expires=" + expires +
        ", qq=" + qq +
        ", price=" + price +
        ", priceOld=" + priceOld +
        "}";
    }
}
