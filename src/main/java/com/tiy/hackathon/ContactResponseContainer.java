package com.tiy.hackathon;

import java.util.ArrayList;

/**
 * Created by Brett on 10/1/16.
 */
public class ContactResponseContainer {
//	Contacts responseContact;
	String errorMessage;
	ArrayList<Contacts> contactsALContainer = new ArrayList<Contacts> ();

	public ArrayList getResponseEventContainer() {
		return contactsALContainer;
	}

	public void setResponseEventContainer(ArrayList contactsALContainer) {
		this.contactsALContainer = contactsALContainer;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public ContactResponseContainer () {

	}
}
