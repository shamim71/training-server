package com.versacomllc.training.message.request;

import java.util.List;


/**
 * Request filter for getting Enrollment test report.
 * 
 * @author Shamim Ahmmed
 * 
 */
public class EnrollmentTestFilter{


	private String email;
	
	private String firstName;
	
	private String lastName;
	
	private List<Long> courseIds;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Long> getCourseIds() {
		return courseIds;
	}

	public void setCourseIds(List<Long> courseIds) {
		this.courseIds = courseIds;
	}

}
