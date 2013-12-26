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
@Table(name = "slide")
public class Slide extends AbstractEntity {
	
	@Column(name = "title", length = 255)
	private String title;
	
	/** content url */
	@Column(name = "url", length = 255)
	private String url;
	
	@Column(name = "content", length = 500)
	private String content;

	@ManyToOne ( targetEntity=Course.class, optional=false)
	@JoinColumn(name = "course_id",referencedColumnName="id")	
	private Course course;	
	
	private int serial;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public int getSerial() {
		return serial;
	}

	public void setSerial(int serial) {
		this.serial = serial;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
