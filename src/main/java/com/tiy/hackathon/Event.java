package com.tiy.hackathon;

import javax.persistence.*;

/**
 * Created by Brett on 9/29/16.
 */
@Entity
@Table(name = "events")
public class Event {

	@Id
	@GeneratedValue
	int id;

	@Column (nullable = false)
	String name;

	@Column (nullable = false)
	String location;

	@Column (nullable = false)
	String dateAndTime;

	@Column (nullable = false)
	String details;

//	@ManyToOne
//	User user;

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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}

	public Event(String name, String location, String dateAndTime, String details) {
		this.name = name;
		this.location = location;
		this.dateAndTime = dateAndTime;
		this.details = details;
	}

	public Event() {
	}
}
