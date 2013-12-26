package com.versacomllc.training.message;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.versacomllc.training.common.CommonConstants;
import com.versacomllc.training.domain.CourseEnrollment;
import com.versacomllc.training.domain.Employee;
import com.versacomllc.training.domain.UserCourseEnrollment;

public class CourseEnrollmentMsg {

	private Long id;
	
	@NotEmpty(message = "Enrollment name is missing")
	private String name;
	
	private String description;
	
	@NotEmpty(message = "Enrollment start date is missing")
	private String start;
	
	@NotEmpty(message = "Enrollment end date is missing")
	private String end;
	
	@NotNull(message = "Course is missing")
	private Long courseId;

	private String courseCode;
	
	private String courseName;
	
	private Set<UserCourseEnrollmentMsg> users;
	
	
	public CourseEnrollmentMsg(){}
	public CourseEnrollmentMsg(CourseEnrollment en){
		this.courseId = en.getCourse().getId();
		this.courseCode = en.getCourse().getCourseCode();
		this.courseName = en.getCourse().getName();
		
		this.description = en.getDescription();
		this.name = en.getName();
		this.id = en.getId();
		
		this.start = CommonConstants.US_DATEFORMAT.format(en.getEnrollmentDate());
		this.end = CommonConstants.US_DATEFORMAT.format(en.getFinishDate());
		
		//Load enrolled users;
		this.users = new TreeSet<UserCourseEnrollmentMsg>();
		Set<UserCourseEnrollment> enrollments =  en.getUserCourseEnrollments();
		for(UserCourseEnrollment enr: enrollments){
			UserCourseEnrollmentMsg msg = new UserCourseEnrollmentMsg();
			msg.setStatus(enr.getStatus().name());
			Employee emp = enr.getEmployee();
			msg.setUserEmail(emp.getEmail());
			msg.setUserId(emp.getId());
			msg.setUserName(emp.getFirstName() + " "+ emp.getLastName());
			this.users.add(msg);
		}
	}
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
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
	public Set<UserCourseEnrollmentMsg> getUsers() {
		return users;
	}
	public void setUsers(Set<UserCourseEnrollmentMsg> users) {
		this.users = users;
	}

	
	public static class UserCourseEnrollmentMsg implements Comparable<UserCourseEnrollmentMsg>{
		
		private Long userId;
		
		private String userName;
		
		private String userEmail;
		
		private String status;

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

		public String getUserEmail() {
			return userEmail;
		}

		public void setUserEmail(String userEmail) {
			this.userEmail = userEmail;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		@Override
		public int compareTo(UserCourseEnrollmentMsg another) {
			// TODO Auto-generated method stub
			return this.userName.compareTo(another.userName);
		}
		
	}
}
