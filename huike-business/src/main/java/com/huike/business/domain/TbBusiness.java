package com.huike.business.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.huike.clues.domain.TbClue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.huike.common.annotation.Excel;
import com.huike.common.core.domain.BaseEntity;
import org.springframework.stereotype.Component;

@Component
/**
 * 线索对象 tb_business
 * @date 2021-04-28
 */
@ApiModel("商机对象")
public class TbBusiness extends TbClue
{
    private static final long serialVersionUID = 1L;

    /** 省 */
    @ApiModelProperty("省")
    @Excel(name = "省")
    private String provinces;

    /** 区 */
    @ApiModelProperty("区")
    @Excel(name = "区")
    private String city;

    @ApiModelProperty("省_区")
    private String region;

    /** 课程 */
    @ApiModelProperty("课程")
    @Excel(name = "课程")
    private Long courseId;

    /** 职业 */
    @ApiModelProperty("职业")
    @Excel(name = "职业")
    private String occupation;

    /** 学历 */
    @ApiModelProperty("学历")
    @Excel(name = "学历")
    private String education;

    /** 在职情况 */
    @ApiModelProperty("在职情况")
    @Excel(name = "在职情况")
    private String job;

    /** 薪资 */
    @ApiModelProperty("薪资")
    @Excel(name = "薪资")
    private String salary;

    /** 专业 */
    @ApiModelProperty("专业")
    @Excel(name = "专业")
    private String major;

    /** 希望薪资 */
    @ApiModelProperty("希望薪资")
    @Excel(name = "希望薪资")
    private String expectedSalary;

    /** 学习原因 */
    @ApiModelProperty("学习原因")
    @Excel(name = "学习原因")
    private String reasons;

    /** 职业计划 */
    @ApiModelProperty("职业计划")
    @Excel(name = "职业计划")
    private String plan;

    /** 计划时间 */
    @ApiModelProperty("计划时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "计划时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planTime;

    /** 其他意向 */
    @ApiModelProperty("其他意向")
    @Excel(name = "其他意向")
    private String otherIntention;

    /** 下次跟进时间 */
    @ApiModelProperty("下次跟进时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "下次跟进时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date nextTime;

    /** 状态(已分配1  进行中2  回收3) */
    @ApiModelProperty("状态(已分配1  进行中2  回收3)")
    @Excel(name = "状态(已分配1  进行中2  回收3 完成4)")
    private String status;

    //转换使用
    @ApiModelProperty("转换使用的线索id")
    private Long clueId;
    
    //开始时间
    @ApiModelProperty("开始时间")
    private String beginCreateTime;
    
    //结束时间
    @ApiModelProperty("结束时间")
    private String endCreateTime;

    private Date lastUpdateTime;

    public String getBeginCreateTime() {
		return beginCreateTime;
	}

	public void setBeginCreateTime(String beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}

	public String getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(String endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public Long getClueId() {
        return clueId;
    }

	public void setClueId(Long clueId) {
        this.clueId = clueId;
    }

    public String getRegion() {
        return provinces+"_"+city;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setProvinces(String provinces) 
    {
        this.provinces = provinces;
    }

    public String getProvinces() 
    {
        return provinces;
    }
    public void setCity(String city) 
    {
        this.city = city;
    }

    public String getCity() 
    {
        return city;
    }

    public void setCourseId(Long courseId)
    {
        this.courseId = courseId;
    }

    public Long getCourseId()
    {
        return courseId;
    }
    public void setOccupation(String occupation) 
    {
        this.occupation = occupation;
    }

    public String getOccupation() 
    {
        return occupation;
    }
    public void setEducation(String education) 
    {
        this.education = education;
    }

    public String getEducation() 
    {
        return education;
    }
    public void setJob(String job) 
    {
        this.job = job;
    }

    public String getJob() 
    {
        return job;
    }
    public void setSalary(String salary) 
    {
        this.salary = salary;
    }

    public String getSalary() 
    {
        return salary;
    }
    public void setMajor(String major) 
    {
        this.major = major;
    }

    public String getMajor() 
    {
        return major;
    }
    public void setExpectedSalary(String expectedSalary) 
    {
        this.expectedSalary = expectedSalary;
    }

    public String getExpectedSalary() 
    {
        return expectedSalary;
    }
    public void setReasons(String reasons) 
    {
        this.reasons = reasons;
    }

    public String getReasons() 
    {
        return reasons;
    }
    public void setPlan(String plan) 
    {
        this.plan = plan;
    }

    public String getPlan() 
    {
        return plan;
    }
    public void setPlanTime(Date planTime) 
    {
        this.planTime = planTime;
    }

    public Date getPlanTime() 
    {
        return planTime;
    }
    public void setOtherIntention(String otherIntention) 
    {
        this.otherIntention = otherIntention;
    }

    public String getOtherIntention() 
    {
        return otherIntention;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("phone", getPhone())
            .append("channel", getChannel())
            .append("activityId", getActivityId())
            .append("provinces", getProvinces())
            .append("city", getCity())
            .append("sex", getSex())
            .append("age", getAge())
            .append("weixin", getWeixin())
            .append("qq", getQq())
            .append("level", getLevel())
            .append("subject", getSubject())
            .append("courseId", getCourseId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("occupation", getOccupation())
            .append("education", getEducation())
            .append("job", getJob())
            .append("salary", getSalary())
            .append("major", getMajor())
            .append("expectedSalary", getExpectedSalary())
            .append("reasons", getReasons())
            .append("plan", getPlan())
            .append("planTime", getPlanTime())
            .append("otherIntention", getOtherIntention())
            .append("nextTime", getNextTime())
            .append("status", getStatus())
            .append("endCreateTime", getEndCreateTime())
            .append("beginCreateTime", getBeginCreateTime())
            .toString();
    }
}
