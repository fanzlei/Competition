package com.fanz.competition.model;

import java.sql.Date;

public class Project {

	private int id;
	private String name;
	private String profile;
	private String sponsor;
	private Date start_date;
	private Date end_date;
	private int project_type;
	
	public Project(){}
	
	public Project(String name,String profile,String sponsor,Date start_date,Date end_date){
		this.name = name;
		this.profile = profile;
		this.sponsor = sponsor;
		this.start_date = start_date;
		this.end_date = end_date;
	}
	
	public Project(int id,String name,String profile,String sponsor,Date start_date,Date end_date){
		this.id = id;
		this.name = name;
		this.profile = profile;
		this.sponsor = sponsor;
		this.start_date = start_date;
		this.end_date = end_date;
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

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}

	public int getProject_type() {
		return project_type;
	}

	public void setProject_type(int project_type) {
		this.project_type = project_type;
	}
	
	
}
