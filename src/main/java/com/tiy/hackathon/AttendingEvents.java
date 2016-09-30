package com.tiy.hackathon;

import javax.persistence.*;

/**
 * Created by Brett on 9/30/16.
 */

@Entity
@Table(name = "attending")
public class AttendingEvents {

	@Id
	@GeneratedValue
	int id;

	@ManyToOne
	Event event;

	@ManyToOne
	User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public AttendingEvents(Event event, User user) {
		this.event = event;
		this.user = user;
	}

	public AttendingEvents () {

	}
}
