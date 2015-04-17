package com.fanz.competition.model;

public class Reward {

	private int id;
	private String project_name;
	private String student_name;
	private String rank;
	private int project_type;
	private String certificate;
	
	public Reward(){}
	
	public Reward(String project_name,String student_name,String rank,int project_type){
		this.project_name = project_name;
		this.student_name = student_name;
		this.rank = rank;
		this.project_type = project_type;
	}
	
	public Reward(int id,String project_name,String student_name,String rank,int project_type){
		this.id = id;
		this.project_name = project_name;
		this.student_name = student_name;
		this.rank = rank;
		this.project_type = project_type;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getStudent_name() {
		return student_name;
	}

	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public int getProject_type() {
		return project_type;
	}

	public void setProject_type(int project_type) {
		this.project_type = project_type;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return project_name+student_name+rank;
	}
	
	
}
