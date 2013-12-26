package com.versacomllc.training.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;



/**
 * Entity for Course data.
 * 
 * @author Shamim Ahmmed
 * 
 */
@Entity
@Table(name = "course_test_details")
public class CourseTestDetails extends AbstractEntity {


	@OneToOne
	@JoinColumn(name = "question_id",nullable=false)
	private Question question;
	
	@OneToOne
	@JoinColumn(name = "user_answer_id",nullable=false)
	private QuestionAnswer userAnswer;

	@ManyToOne ( targetEntity=CourseTest.class,optional=false)
	@JoinColumn(name = "course_test_id",referencedColumnName="id")	
	private CourseTest courseTest;	
	
	private Boolean correct;
	
	
	public CourseTest getCourseTest() {
		return courseTest;
	}

	public void setCourseTest(CourseTest courseTest) {
		this.courseTest = courseTest;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public QuestionAnswer getUserAnswer() {
		return userAnswer;
	}

	public void setUserAnswer(QuestionAnswer userAnswer) {
		this.userAnswer = userAnswer;
	}

	public Boolean getCorrect() {
		return correct;
	}

	public void setCorrect(Boolean correct) {
		this.correct = correct;
	}

}
