package com.tiy.hackathon;

/**
 * Created by Brett on 10/1/16.
 */
public class UserResponseContainer {

	User responseUser;
	String errorMessage;

	public User getResponseUser() {
		return responseUser;
	}

	public void setResponseUser(User responseUser) {
		this.responseUser = responseUser;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public UserResponseContainer() {

	}
}
