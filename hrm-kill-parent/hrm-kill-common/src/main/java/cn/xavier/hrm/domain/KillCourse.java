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
@TableName("t_kill_course")
public class KillCourse extends Model<KillCourse> {

    public static final int STATE_UN_PUBLISH = 0;
    public static final int STATE_KILLING = 1;
    public static final int STATE_KILLING_COMPLETE = 2;

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 秒杀场次编号,同一个课程多次发布，编号不一样
     */
    @TableField("session_number")
    private String sessionNumber;
    /**
     * 课程名字
     */
    @TableField("course_name")
    private String courseName;
    /**
     * 对应的课程ID
     */
    @TableField("course_id")
    private Long courseId;
    @TableField("kill_price")
    private BigDecimal killPrice;
    @TableField("kill_count")
    private Integer killCount;
    /**
     * 每个人可以秒杀的数量,默认1
     */
    @TableField("kill_limit")
    private Integer killLimit;
    /**
     * 秒杀课程排序
     */
    @TableField("kill_sort")
    private Integer killSort;
    /**
     * 秒杀状态:0待发布，1秒杀中，2秒杀结束
     */
    @TableField("kill_status")
    private Integer killStatus;
    @TableField("course_pic")
    private String coursePic;
    /**
     * 秒杀开始时间
     */
    @TableField("start_time")
    private Date startTime;
    /**
     * 秒杀结束时间
     */
    @TableField("end_time")
    private Date endTime;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    private Integer version;

    @TableField(exist = false)
    private String killCode;

    public String getKillCode() {
        return killCode;
    }

    public void setKillCode(String killCode) {
        this.killCode = killCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionNumber() {
        return sessionNumber;
    }

    public void setSessionNumber(String sessionNumber) {
        this.sessionNumber = sessionNumber;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public BigDecimal getKillPrice() {
        return killPrice;
    }

    public void setKillPrice(BigDecimal killPrice) {
        this.killPrice = killPrice;
    }

    public Integer getKillCount() {
        return killCount;
    }

    public void setKillCount(Integer killCount) {
        this.killCount = killCount;
    }

    public Integer getKillLimit() {
        return killLimit;
    }

    public void setKillLimit(Integer killLimit) {
        this.killLimit = killLimit;
    }

    public Integer getKillSort() {
        return killSort;
    }

    public void setKillSort(Integer killSort) {
        this.killSort = killSort;
    }

    public Integer getKillStatus() {
        return killStatus;
    }

    public void setKillStatus(Integer killStatus) {
        this.killStatus = killStatus;
    }

    public String getCoursePic() {
        return coursePic;
    }

    public void setCoursePic(String coursePic) {
        this.coursePic = coursePic;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
        return "KillCourse{" +
        ", id=" + id +
        ", sessionNumber=" + sessionNumber +
        ", courseName=" + courseName +
        ", courseId=" + courseId +
        ", killPrice=" + killPrice +
        ", killCount=" + killCount +
        ", killLimit=" + killLimit +
        ", killSort=" + killSort +
        ", killStatus=" + killStatus +
        ", coursePic=" + coursePic +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", createTime=" + createTime +
        ", version=" + version +
        "}";
    }
}
