package com.versacomllc.training.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * Entity for Course data.
 * 
 * @author Shamim Ahmmed
 * 
 */
@Entity
@Table(name = "question_answer")
public class QuestionAnswer extends AbstractEntity {

	/** Course name*/
	@Column(name = "is_answer", length = 255)
	private Boolean isAnswer;
	
	@Column(name = "answer", length = 500)
	private String answer;

	@ManyToOne ( targetEntity=Question.class,optional=false)
	@JoinColumn(name = "question_id",referencedColumnName="id")	
	private Question question;	
	

	public Boolean getIsAnswer() {
		return isAnswer;
	}


	public void setIsAnswer(Boolean isAnswer) {
		this.isAnswer = isAnswer;
	}


	public Question getQuestion() {
		return question;
	}


	public void setQuestion(Question question) {
		this.question = question;
	}


	public String getAnswer() {
		return answer;
	}


	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
