package com.versacomllc.training.message;

import static com.versacomllc.training.common.CommonConstants.US_DATEFORMAT;

import org.hibernate.validator.constraints.NotEmpty;

import com.versacomllc.training.domain.Course;
import com.versacomllc.training.domain.UserCourseEnrollment;

public class CourseEnrollmentMessage {

	@NotEmpty(message = "Course id is missing")
	private Long courseId;

	private String courseCode;
	
	private String courseName;
	
	private String description;
	
	private String status;
	
	private Long enrollmentId;
	
	private String enrollmentName;
	
	private String enrollmentEnd;

	public CourseEnrollmentMessage(){}
	public CourseEnrollmentMessage(UserCourseEnrollment en){
		Course course = en.getCourseEnrollment().getCourse();
		this.courseId = course.getId();
		this.courseCode = course.getCourseCode();
		this.courseName = course.getName();
		this.description = course.getDescription();
		this.status = en.getStatus().name();
		this.enrollmentId = en.getId();
		this.enrollmentName = en.getCourseEnrollment().getName();
		this.enrollmentEnd = US_DATEFORMAT.format(en.getCourseEnrollment().getFinishDate());
	}
	

	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public Long getEnrollmentId() {
		return enrollmentId;
	}
	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}
	public String getEnrollmentName() {
		return enrollmentName;
	}
	public void setEnrollmentName(String enrollmentName) {
		this.enrollmentName = enrollmentName;
	}
	public String getEnrollmentEnd() {
		return enrollmentEnd;
	}
	public void setEnrollmentEnd(String enrollmentEnd) {
		this.enrollmentEnd = enrollmentEnd;
	}
	
}
