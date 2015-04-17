package com.fanz.competition.model;

import java.sql.Date;

public class Apply {

	private int id;
	private String student_name;
	private String project_name;
	private Date apply_time;
	
	public Apply(){}
	
	public Apply(String student_name,String project_name){
		this.student_name = student_name;
		this.project_name = project_name;
	}
	
	public Apply(int id,String student_name,String project_name,Date apply_time){
		this.id = id;
		this.student_name = student_name;
		this.project_name = project_name;
		this.apply_time = apply_time;
	}

	public int getId() {
		return id;
	}

	

	public String getStudent_name() {
		return student_name;
	}

	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public Date getApply_time() {
		return apply_time;
	}

	public void setApply_time(Date apply_time) {
		this.apply_time = apply_time;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return project_name+'\\'+student_name;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
