package com.versacomllc.training.message;

import java.util.List;


public class EnrollmentTestMessage {

	private Long enrollmentId;
	
	private Long userId;
	
	private Long courseId;
	
	private String startDate;
	
	private String endDate;
	
	private int totalQuestion;
	
	private int totalAnswer;
	
	private int totalCorrectAnswer;
	
	private Boolean passed;
	
	private List<TestDetails> details;
	
	public EnrollmentTestMessage() {
	}
	
	
	public static class TestDetails{
		
		private Long questionId;
		
		private Long userAnswerId;
		
		private Boolean correct;
		
		private Boolean endOfTest;

		public Long getQuestionId() {
			return questionId;
		}

		public void setQuestionId(Long questionId) {
			this.questionId = questionId;
		}

		public Long getUserAnswerId() {
			return userAnswerId;
		}

		public void setUserAnswerId(Long userAnswerId) {
			this.userAnswerId = userAnswerId;
		}

		public Boolean getCorrect() {
			return correct;
		}

		public void setCorrect(Boolean correct) {
			this.correct = correct;
		}

		public Boolean getEndOfTest() {
			return endOfTest;
		}

		public void setEndOfTest(Boolean endOfTest) {
			this.endOfTest = endOfTest;
		}
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public Long getCourseId() {
		return courseId;
	}


	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public int getTotalQuestion() {
		return totalQuestion;
	}


	public void setTotalQuestion(int totalQuestion) {
		this.totalQuestion = totalQuestion;
	}


	public int getTotalAnswer() {
		return totalAnswer;
	}


	public void setTotalAnswer(int totalAnswer) {
		this.totalAnswer = totalAnswer;
	}


	public int getTotalCorrectAnswer() {
		return totalCorrectAnswer;
	}


	public void setTotalCorrectAnswer(int totalCorrectAnswer) {
		this.totalCorrectAnswer = totalCorrectAnswer;
	}


	public Boolean getPassed() {
		return passed;
	}


	public void setPassed(Boolean passed) {
		this.passed = passed;
	}


	public Long getEnrollmentId() {
		return enrollmentId;
	}


	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}


	public List<TestDetails> getDetails() {
		return details;
	}


	public void setDetails(List<TestDetails> details) {
		this.details = details;
	}
	
	
	
}
