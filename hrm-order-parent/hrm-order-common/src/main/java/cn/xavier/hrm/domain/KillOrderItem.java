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
@TableName("t_kill_order_item")
public class KillOrderItem extends Model<KillOrderItem> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 对应秒杀订单
     */
    @TableField("kill_order_id")
    private Long killOrderId;
    /**
     * 秒杀课程的价格
     */
    private BigDecimal price;
    /**
     * 秒杀到课程的数量
     */
    private Integer count;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 秒杀场次编号,同一个课程多次发布，编号不一样
     */
    @TableField("session_number")
    private String sessionNumber;
    /**
     * 秒杀课程ID，不是课程ID
     */
    @TableField("kill_course_id")
    private Long killCourseId;
    /**
     * 秒杀课程名字
     */
    @TableField("kill_course_name")
    private String killCourseName;
    private Integer version;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKillOrderId() {
        return killOrderId;
    }

    public void setKillOrderId(Long killOrderId) {
        this.killOrderId = killOrderId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSessionNumber() {
        return sessionNumber;
    }

    public void setSessionNumber(String sessionNumber) {
        this.sessionNumber = sessionNumber;
    }

    public Long getKillCourseId() {
        return killCourseId;
    }

    public void setKillCourseId(Long killCourseId) {
        this.killCourseId = killCourseId;
    }

    public String getKillCourseName() {
        return killCourseName;
    }

    public void setKillCourseName(String killCourseName) {
        this.killCourseName = killCourseName;
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
        return "KillOrderItem{" +
        ", id=" + id +
        ", killOrderId=" + killOrderId +
        ", price=" + price +
        ", count=" + count +
        ", createTime=" + createTime +
        ", sessionNumber=" + sessionNumber +
        ", killCourseId=" + killCourseId +
        ", killCourseName=" + killCourseName +
        ", version=" + version +
        "}";
    }
}
