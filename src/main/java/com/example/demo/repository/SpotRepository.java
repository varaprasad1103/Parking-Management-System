package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Spot;

public interface SpotRepository extends JpaRepository<Spot, Long> {
	List<Spot> findByAvailableTrue();
	List<Spot> findByLocation(String location);
}