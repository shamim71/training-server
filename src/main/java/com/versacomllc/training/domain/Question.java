package com.versacomllc.training.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;



/**
 * Entity for Course data.
 * 
 * @author Shamim Ahmmed
 * 
 */
@Entity
@Table(name = "question")
public class Question extends AbstractEntity {

	/** Course name*/
	@Column(name = "name", length = 255)
	private String name;
	
	/** Question image url */
	@Column(name = "url", length = 255)
	private String url;
	
	@ManyToOne ( targetEntity=Course.class, optional=false)
	@JoinColumn(name = "course_id",referencedColumnName="id")	
	private Course course;	
	
	@OneToMany(mappedBy="question",targetEntity=QuestionAnswer.class,fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	private Set<QuestionAnswer> questionAnswers;
	
	
	private int serial;
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Course getCourse() {
		return course;
	}


	public void setCourse(Course course) {
		this.course = course;
	}


	public Set<QuestionAnswer> getQuestionAnswers() {
		return questionAnswers;
	}


	public void setQuestionAnswers(Set<QuestionAnswer> questionAnswers) {
		this.questionAnswers = questionAnswers;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public int getSerial() {
		return serial;
	}


	public void setSerial(int serial) {
		this.serial = serial;
	}
	

	

}
