package com.versacomllc.training.message;

import com.versacomllc.training.domain.Employee;

public class UserName implements Comparable<UserName>{

	public UserName(Employee emp){
		this.id = String.valueOf(emp.getId());
		this.email = emp.getEmail();
		this.name = emp.getFirstName() + " "+ emp.getLastName();
		this.firstName = emp.getFirstName();
		this.lastName = emp.getLastName();
		this.phone = emp.getPhone();
		this.address = emp.getAddress();
	}
	
	private String id;

	private String email;

	private String name;
	
	private String firstName;
	private String lastName;
	private String phone;
	private String address;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
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
	public int compareTo(UserName another) {
		return this.name.compareTo(another.name);
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
