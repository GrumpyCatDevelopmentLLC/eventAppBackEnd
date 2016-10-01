package com.tiy.hackathon;

import java.util.ArrayList;

/**
 * Created by Brett on 10/1/16.
 */
public class EventResponseContainer {
	ArrayList <Event> responseEventContainer = new ArrayList<>();
	String errorMessage;

	public ArrayList getResponseEventContainer() {
		return responseEventContainer;
	}

	public void setResponseEventContainer(ArrayList responseEventContainer) {
		this.responseEventContainer = responseEventContainer;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public EventResponseContainer () {

	}
}
