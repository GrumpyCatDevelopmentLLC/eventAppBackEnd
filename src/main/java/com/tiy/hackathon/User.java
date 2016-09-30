package com.tiy.hackathon;

import javax.persistence.*;

/**
 * Created by Brett on 9/29/16.
 */

@Entity
@Table (name = "users")
public class User {

	@Id
	@GeneratedValue
	int id;

	@Column (nullable = false, unique = true)
	String email;

	@Column(nullable = false, unique = true)
	String displayName;

	@Column (nullable = false)
	String password;

	@Column (nullable = false)
	boolean isOffensive;

	public User(String email, String displayName, String password) {
		this.email = email;
		this.displayName = displayName;
		this.password = password;
		this.isOffensive = false;
	}

	public User() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isOffensive() {
		return isOffensive;
	}

	public void setOffensive(boolean offensive) {
		isOffensive = offensive;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
