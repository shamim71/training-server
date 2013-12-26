package com.versacomllc.training.message;

import java.util.Set;


public class CourseMessage {

	private Long id;

	private String code;
	
	private String name;
	
	private String description;
	
	private int passMark;
	
	private int maxQuestions;
	
	private Set<SlideMessage> slides;
	
	public static class SlideMessage implements Comparable<SlideMessage>{
		
		private String title;

		private String url;

		private String content;

		private Long id;	
		
		private int serial;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

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

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public int getSerial() {
			return serial;
		}

		public void setSerial(int serial) {
			this.serial = serial;
		}

		@Override
		public int compareTo(SlideMessage o) {
			if(this.serial > o.serial){
				return 1;
			}
			else if(this.serial < o.serial){
				return -1;
			}
			return 0;
		}
		
		
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<SlideMessage> getSlides() {
		return slides;
	}

	public void setSlides(Set<SlideMessage> slides) {
		this.slides = slides;
	}

	public int getPassMark() {
		return passMark;
	}

	public void setPassMark(int passMark) {
		this.passMark = passMark;
	}

	public int getMaxQuestions() {
		return maxQuestions;
	}

	public void setMaxQuestions(int maxQuestions) {
		this.maxQuestions = maxQuestions;
	}
	
}
