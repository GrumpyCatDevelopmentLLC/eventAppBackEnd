package com.tiy.hackathon;

import java.util.ArrayList;

/**
 * Created by Brett on 10/1/16.
 */
public class AttendingResponseContainer {
	ArrayList<AttendingEvents> myEvents = new ArrayList<>();
	String errorMessage;

	public AttendingResponseContainer() {
	}

	public ArrayList<AttendingEvents> getMyEvents() {
		return myEvents;
	}

	public void setMyEvents(ArrayList<AttendingEvents> myEvents) {
		this.myEvents = myEvents;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
