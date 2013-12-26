package com.versacomllc.training.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity for Course data.
 * 
 * @author Shamim Ahmmed
 * 
 */
@Entity
@Table(name = "course")
public class Course extends AbstractEntity {

	/**  */
	@Column(name = "course_code", length = 100, unique=true)
	private String courseCode;

	/** Course name*/
	@Column(name = "name", length = 255)
	private String name;
	
	/** Course description*/
	@Column(name = "description", length = 255)
	private String description;

	@OneToMany(mappedBy="course",targetEntity=Slide.class,fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	private Set<Slide> slides;
	
	@OneToMany(mappedBy="course",targetEntity=Question.class,fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	private Set<Question> questions;
	
	@Column(name = "pass_mark")
	private int passMark;
	
	@Column(name = "exam_max_questions")
	private int examMaxQuestions;
	
	
	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
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


	public Set<Slide> getSlides() {
		return slides;
	}

	public void setSlides(Set<Slide> slides) {
		this.slides = slides;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public int getPassMark() {
		return passMark;
	}

	public void setPassMark(int passMark) {
		this.passMark = passMark;
	}

	public int getExamMaxQuestions() {
		return examMaxQuestions;
	}

	public void setExamMaxQuestions(int examMaxQuestions) {
		this.examMaxQuestions = examMaxQuestions;
	}
	
}
