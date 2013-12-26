package com.versacomllc.training.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Entity for Course data.
 * 
 * @author Shamim Ahmmed
 * 
 */
@Entity
@Table(name = "course_enrollment")
public class CourseEnrollment extends AbstractEntity {

	/** Course name */
	@Column(name = "name", length = 255)
	private String name;

	/** Course description */
	@Column(name = "description", length = 255)
	private String description;

	@OneToOne
	@JoinColumn(name = "course_id")
	private Course course;

	@Column(name = "enrollment_date")
	private Date enrollmentDate;
	
	@Column(name = "finish_date")
	private Date finishDate;
	
	@OneToMany(mappedBy="courseEnrollment",targetEntity=UserCourseEnrollment.class,fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	private Set<UserCourseEnrollment> userCourseEnrollments;
	

	public Set<UserCourseEnrollment> getUserCourseEnrollments() {
		return userCourseEnrollments;
	}

	public void setUserCourseEnrollments(
			Set<UserCourseEnrollment> userCourseEnrollments) {
		this.userCourseEnrollments = userCourseEnrollments;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Date getEnrollmentDate() {
		return enrollmentDate;
	}

	public void setEnrollmentDate(Date enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}


}
