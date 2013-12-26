package com.versacomllc.training.message;

import com.versacomllc.training.common.CommonConstants;
import com.versacomllc.training.domain.Course;
import com.versacomllc.training.domain.CourseEnrollment;
import com.versacomllc.training.domain.CourseTest;
import com.versacomllc.training.domain.Employee;



public class UserEnrollmentTestMessage implements Comparable<UserEnrollmentTestMessage>{

	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String courseCode;
	
	private String courseName;
	
	private String enrollmentName;
	
	private String enrollmentDue;
	
	private String finishDate;
	
	private int totalQuestion;
	
	private int correctlyAnswered;
	
	private String status;
	
	private Long testId;
	
	
	public UserEnrollmentTestMessage() {
	}
	
	public UserEnrollmentTestMessage(CourseTest test) {
		
		Employee emp = test.getUserCourseEnrollment().getEmployee();
		this.firstName = emp.getFirstName();
		this.lastName = emp.getLastName();
		this.email = emp.getEmail();
		
		CourseEnrollment courseEnrollment = test.getUserCourseEnrollment().getCourseEnrollment();
		this.enrollmentName = courseEnrollment.getName();
		this.enrollmentDue = CommonConstants.US_DATEFORMAT.format(courseEnrollment.getFinishDate());
		
		Course course = courseEnrollment.getCourse();
		this.courseCode = course.getCourseCode();
		this.courseName = course.getName();
		
		if(test.getEndTime() != null){
			this.finishDate = CommonConstants.US_DATEFORMAT.format(test.getEndTime());
		}
		
		this.totalQuestion = test.getTotalQuestions();
		this.correctlyAnswered = test.getCorrectAnswers();
		this.status = test.getUserCourseEnrollment().getStatus().name();
		this.testId = test.getId();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getEnrollmentName() {
		return enrollmentName;
	}

	public void setEnrollmentName(String enrollmentName) {
		this.enrollmentName = enrollmentName;
	}

	public String getEnrollmentDue() {
		return enrollmentDue;
	}

	public void setEnrollmentDue(String enrollmentDue) {
		this.enrollmentDue = enrollmentDue;
	}

	public String getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}

	public int getTotalQuestion() {
		return totalQuestion;
	}

	public void setTotalQuestion(int totalQuestion) {
		this.totalQuestion = totalQuestion;
	}

	public int getCorrectlyAnswered() {
		return correctlyAnswered;
	}

	public void setCorrectlyAnswered(int correctlyAnswered) {
		this.correctlyAnswered = correctlyAnswered;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int compareTo(UserEnrollmentTestMessage another) {
		return this.enrollmentDue.compareTo(another.enrollmentDue);
	}

	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + correctlyAnswered;
		result = prime * result
				+ ((courseCode == null) ? 0 : courseCode.hashCode());
		result = prime * result
				+ ((courseName == null) ? 0 : courseName.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((enrollmentDue == null) ? 0 : enrollmentDue.hashCode());
		result = prime * result
				+ ((enrollmentName == null) ? 0 : enrollmentName.hashCode());
		result = prime * result
				+ ((finishDate == null) ? 0 : finishDate.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((testId == null) ? 0 : testId.hashCode());
		result = prime * result + totalQuestion;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserEnrollmentTestMessage other = (UserEnrollmentTestMessage) obj;
		if (correctlyAnswered != other.correctlyAnswered)
			return false;
		if (courseCode == null) {
			if (other.courseCode != null)
				return false;
		} else if (!courseCode.equals(other.courseCode))
			return false;
		if (courseName == null) {
			if (other.courseName != null)
				return false;
		} else if (!courseName.equals(other.courseName))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (enrollmentDue == null) {
			if (other.enrollmentDue != null)
				return false;
		} else if (!enrollmentDue.equals(other.enrollmentDue))
			return false;
		if (enrollmentName == null) {
			if (other.enrollmentName != null)
				return false;
		} else if (!enrollmentName.equals(other.enrollmentName))
			return false;
		if (finishDate == null) {
			if (other.finishDate != null)
				return false;
		} else if (!finishDate.equals(other.finishDate))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (testId == null) {
			if (other.testId != null)
				return false;
		} else if (!testId.equals(other.testId))
			return false;
		if (totalQuestion != other.totalQuestion)
			return false;
		return true;
	}
	
	
}
