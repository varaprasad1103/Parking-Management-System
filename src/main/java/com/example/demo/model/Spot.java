package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Spot {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String location,available;
	public Spot() {
		super();
	}
	@Override
	public String toString() {
		return "spot [id=" + id + ", location=" + location + ", available=" + available + "]";
	}
	public Spot( String location, String available) {
		super();
		this.location = location;
		this.available = available;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getAvailable() {
		return available;
	}
	public void setAvailable(String available) {
		this.available = available;
	}
	
}
