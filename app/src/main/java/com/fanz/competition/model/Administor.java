package com.fanz.competition.model;

public class Administor {
	private int id;
	private String name;
	private String password;
	private String phone ;
	private String email;
	
	public Administor(){}
	
	public Administor(String name,String password){
		this.name = name;
		this.password = password;
	}
	
	public Administor(int id, String name ,String password , String phone , String email){
		this.id = id ;
		this.name = name;
		this.password  = password;
		this.phone = phone;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}
	
}
