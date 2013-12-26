package com.versacomllc.training.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.versacomllc.training.common.CommonConstants.TraningStatus;

/**
 * Entity for Course data.
 * 
 * @author Shamim Ahmmed
 * 
 */
@Entity
@Table(name = "user_course_enrollment")
public class UserCourseEnrollment extends AbstractEntity {

	@OneToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@Enumerated(EnumType.STRING)
	private TraningStatus status;
	
	
	@ManyToOne ( targetEntity=CourseEnrollment.class,optional=false)
	@JoinColumn(name = "course_enrollment_id",referencedColumnName="id")	
	private CourseEnrollment courseEnrollment;	
	
	public TraningStatus getStatus() {
		return status;
	}

	public void setStatus(TraningStatus status) {
		this.status = status;
	}


	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public CourseEnrollment getCourseEnrollment() {
		return courseEnrollment;
	}

	public void setCourseEnrollment(CourseEnrollment courseEnrollment) {
		this.courseEnrollment = courseEnrollment;
	}

	

}
