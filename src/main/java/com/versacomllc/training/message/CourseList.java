package com.versacomllc.training.message;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "courses")
public class CourseList {

	private List<CourseMessage> list = new ArrayList<>();

	public List<CourseMessage> getList() {
		return list;
	}

	public void setList(List<CourseMessage> list) {
		this.list = list;
	}

}
