package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.demo.model.Requests;
import com.example.demo.model.Reservation;
import com.example.demo.model.Spot;
import com.example.demo.model.User;
import com.example.demo.repository.RequestRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.SpotRepository;
import com.example.demo.repository.UserRepository;

@Service
public class ProjectService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RequestRepository requestRepository;
	@Autowired
	private SpotRepository spotRepository;
	@Autowired
	private ReservationRepository reservationRepository;
	
	
	public List<Spot> findavailable()
	{
		return spotRepository.findByAvailableTrue();
	}
	
	public List<User> findUsers()
	{
		return userRepository.findAll();
	}
	public List<Requests> findRequests()
	{
		return requestRepository.findAll();
	}
	
	public List<Reservation> findMyParking(User u)
	{
		return reservationRepository.findByUsername(u.getUsername());
	}
	
	public boolean addUser(long id)
	{
		Requests r=requestRepository.findById(id).orElseThrow(()->new RuntimeException("cannot add user"));
		User user1=new User(r.getUsername(),r.getEmail(),r.getContact(),r.getPassword());
		try
		{
			userRepository.save(user1);
		}
		catch (DataIntegrityViolationException e)
		{
			return false;
		}
		requestRepository.deleteById(id);
		return true;
	}
	public boolean deleteUser(long id)
	{
		try {
			userRepository.deleteById(id);
		}
		catch (Exception e)
		{
			return false;
		}
		return true;
	}
	
	public User validateUser(String username,String password)
	{
		List<User> users = userRepository.findByUsername(username);
		User u=users.get(0);
		if(users.size()>=1) 
		{
			if(u.getPassword().equals(password))
			{
					return u;
			}
		}
		return null;
	}
	
	public boolean addUserSignupRequest(Requests user)
	{
		try
		{
			requestRepository.save(user);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public boolean spotReservation(User u , long id)
	{
		try
		{
			User u1=userRepository.findById(u.getId()).orElseThrow(()->new RuntimeException("user not found"));
			Spot s1=spotRepository.findById(id).orElseThrow(()->new RuntimeException("spot not found"));
			s1.setAvailable("0");
			spotRepository.save(s1);
			Reservation r1=new Reservation(u1.getUsername(),u1.getContact(),s1.getLocation());
			reservationRepository.save(r1);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean releaseReservation(long id)
	{
		try
		{
			Reservation r1=reservationRepository.findById(id).orElseThrow(()->new RuntimeException("reservation not found"));
			List<Spot> spots=spotRepository.findByLocation(r1.getLocation());
			Spot s1=spots.get(0);
			s1.setAvailable("1");
			spotRepository.save(s1);
			reservationRepository.deleteById(id);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean deleteSpot(long id)
	{
		try {
			spotRepository.deleteById(id);
		}
		catch (Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean addSpot(String location)
	{
		try
		{
			Spot s=new Spot(location,"1");
			spotRepository.save(s);
		}
		catch (DataIntegrityViolationException e)
		{
			return false;
		}
		return true;
	}
	
}

