package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Requests {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	String username,email,contact,password;
	public Requests() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Requests(long id, String name, String email, String contact, String password) {
		super();
		this.id = id;
		this.username = name;
		this.email = email;
		this.contact = contact;
		this.password = password;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + username + ", email=" + email + ", contact=" + contact + ", password="
				+ password + "]";
	}
	
}
