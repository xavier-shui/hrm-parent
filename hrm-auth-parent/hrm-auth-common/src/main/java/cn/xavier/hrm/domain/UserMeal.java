package cn.xavier.hrm.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhengwei-shui
 * @since 2021-12-16
 */
@TableName("t_user_meal")
public class UserMeal extends Model<UserMeal> {

    private static final long serialVersionUID = 1L;

    @TableField("meal_id")
    private Long mealId;
    @TableField("login_id")
    private Long loginId;
    /**
     * 该机构的该套餐到期时间
     */
    @TableField("expire_date")
    private Date expireDate;
    /**
     * 状态,是否过期 0 未支付，1已经购买，2过期
     */
    private Integer state;


    public Long getMealId() {
        return mealId;
    }

    public void setMealId(Long mealId) {
        this.mealId = mealId;
    }

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    protected Serializable pkVal() {
        return this.mealId;
    }

    @Override
    public String toString() {
        return "UserMeal{" +
        ", mealId=" + mealId +
        ", loginId=" + loginId +
        ", expireDate=" + expireDate +
        ", state=" + state +
        "}";
    }
}
