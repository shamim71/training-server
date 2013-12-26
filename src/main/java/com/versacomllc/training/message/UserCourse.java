package com.versacomllc.training.message;

import com.versacomllc.training.domain.Employee;

public class UserCourse implements Comparable<UserCourse>{

	public UserCourse() {
		super();
	}

	public UserCourse(Employee emp){
		this.id = emp.getId();
		this.email = emp.getEmail();
		this.name = emp.getFirstName() + " "+ emp.getLastName();
		this.selected = false;
	}
	
	private Long id;

	private String email;

	private String name;
	
	private boolean selected;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(UserCourse another) {
		return this.name.compareTo(another.name);
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
