package cn.xavier.hrm.domain;

import com.baomidou.mybatisplus.enums.IdType;
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
 * @author zhengwei-shui
 * @since 2021-12-20
 */
@TableName("t_course")
public class Course extends Model<Course> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 课程名称
     */
    private String name;
    /**
     * 适用人群
     */
    @TableField("for_user")
    private String forUser;
    /**
     * 课程分类
     */
    @TableField("course_type_id")
    private Long courseTypeId;
    @TableField("grade_name")
    private String gradeName;
    /**
     * 课程等级
     */
    @TableField("grade_id")
    private Long gradeId;
    /**
     * 课程状态，下线：0 ， 上线：1
     */
    private Integer status;
    /**
     * 教育机构
     */
    @TableField("tenant_id")
    private Long tenantId;
    @TableField("tenant_name")
    private String tenantName;
    /**
     * 添加课程的后台用户的ID
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 添加课程的后台用户
     */
    @TableField("user_name")
    private String userName;
    /**
     * 课程的开课时间
     */
    @TableField("start_time")
    private Date startTime;
    /**
     * 课程的节课时间
     */
    @TableField("end_time")
    private Date endTime;
    /**
     * 封面
     */
    private String pic;
    @TableField("sale_count")
    private Integer saleCount;
    @TableField("view_count")
    private Integer viewCount;
    /**
     * 评论数
     */
    @TableField("comment_count")
    private Integer commentCount;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getForUser() {
        return forUser;
    }

    public void setForUser(String forUser) {
        this.forUser = forUser;
    }

    public Long getCourseTypeId() {
        return courseTypeId;
    }

    public void setCourseTypeId(Long courseTypeId) {
        this.courseTypeId = courseTypeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Course{" +
        ", id=" + id +
        ", name=" + name +
        ", forUser=" + forUser +
        ", courseTypeId=" + courseTypeId +
        ", gradeName=" + gradeName +
        ", gradeId=" + gradeId +
        ", status=" + status +
        ", tenantId=" + tenantId +
        ", tenantName=" + tenantName +
        ", userId=" + userId +
        ", userName=" + userName +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", pic=" + pic +
        ", saleCount=" + saleCount +
        ", viewCount=" + viewCount +
        ", commentCount=" + commentCount +
        "}";
    }
}
