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
@Table(name = "course_test")
public class CourseTest extends AbstractEntity {

	@OneToOne
	@JoinColumn(name = "user_course_enrollment_id")
	private UserCourseEnrollment userCourseEnrollment;
	
	@Column(name = "start_time")
	private Date startTime;
	
	@Column(name = "end_time")
	private Date endTime;
	
	@Column(name = "total_questions")
	private int totalQuestions;
	
	@Column(name = "total_answers")
	private int totalAnswers;
	
	@Column(name = "correct_answers")
	private int correctAnswers;
	
	@Column(name = "passed")
	private Boolean passed;
	
	@Column(name = "completed")
	private Boolean completed;
	
	@Column(name = "code")
	private String code;
	
	@OneToMany(mappedBy="courseTest",targetEntity=CourseTestDetails.class,fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	private Set<CourseTestDetails> courseTestDetails;
	

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

	public int getTotalQuestions() {
		return totalQuestions;
	}

	public void setTotalQuestions(int totalQuestions) {
		this.totalQuestions = totalQuestions;
	}

	public int getTotalAnswers() {
		return totalAnswers;
	}

	public void setTotalAnswers(int totalAnswers) {
		this.totalAnswers = totalAnswers;
	}

	public int getCorrectAnswers() {
		return correctAnswers;
	}

	public void setCorrectAnswers(int correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	public Boolean getPassed() {
		return passed;
	}

	public void setPassed(Boolean passed) {
		this.passed = passed;
	}

	public Set<CourseTestDetails> getCourseTestDetails() {
		return courseTestDetails;
	}

	public void setCourseTestDetails(Set<CourseTestDetails> courseTestDetails) {
		this.courseTestDetails = courseTestDetails;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	public UserCourseEnrollment getUserCourseEnrollment() {
		return userCourseEnrollment;
	}

	public void setUserCourseEnrollment(UserCourseEnrollment userCourseEnrollment) {
		this.userCourseEnrollment = userCourseEnrollment;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
}
