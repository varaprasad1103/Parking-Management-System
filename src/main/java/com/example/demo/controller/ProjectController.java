package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Requests;
import com.example.demo.model.Spot;
import com.example.demo.model.User;
import com.example.demo.service.ProjectService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/projecthome")
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	
	@GetMapping("/home")
	public String home(HttpSession session)
	{
		return "home";
	}
	@GetMapping("/login")
	public String login(Model model)
	{
		model.addAttribute("user",new User());
		return "login";
	}
	@GetMapping("/logout")
	public String logout(HttpSession session)
	{
		session.invalidate();
		return "redirect:/api/projecthome/home";
	}
	@GetMapping("/signup")
	public String signup(Model model)
	{
		model.addAttribute("user",new User());
		return "signup";
	}
	@GetMapping("/about")
	public String about()
	{
		return "about";
	}
	
	@GetMapping("/parking")
	public String parking(Model model)
	{
		model.addAttribute("spot",projectService.findavailable());
		return "parking";
	}
	
	@GetMapping("/admin-page")
	public String admin()
	{
		return "admin-page";
	}
	

	@GetMapping("/login-fail")
	public String lfail()
	{
		return "login-fail";
	}
	@GetMapping("/signup-fail")
	public String sfail()
	{
		return "signup-fail";
	}
	@GetMapping("/signup-success")
	public String ssuccess()
	{
		return "signup-success";
	}
	
	@GetMapping("/profile")
	public String profile(Model model,HttpSession session)
	{
		User u=(User)session.getAttribute("user");
		model.addAttribute("loggeduser",u);
		return "profile";
	}
	@GetMapping("/my-parking")
	public String parkings(Model model,HttpSession session)
	{
		User u1=(User)session.getAttribute("user");
		model.addAttribute("reservations",projectService.findMyParking(u1));
		return "my-parking";
	}
	
	
	@GetMapping("/admin-dashboard")
	public String dashboard(Model model)
	{
		model.addAttribute("users",projectService.findUsers());
		model.addAttribute("requests",projectService.findRequests());
		return "dashboard";
	}
	
	@PostMapping("/adduser")
	public String adduser(@RequestParam Long id)
	{	if(projectService.addUser(id))
		{
			return "redirect:/api/projecthome/admin-dashboard";
		}
		return "admin-user-error";
	}
	
	@PostMapping("/deleteuser")
	public String deleteuser(@RequestParam Long id)
	{
		if(projectService.deleteUser(id))
		{
			return "redirect:/api/projecthome/admin-dashboard";
		}
		return "admin-user-error";
	}
	
	@PostMapping("/loginsave")
	public String loginsave(HttpSession session,@RequestParam String username, @RequestParam String password)
	{
		System.out.println("inside login");
		User u=projectService.validateUser(username,password);
		System.out.println(u);
		if(u==null)
			return "login-fail";
		else
		{
			session.setAttribute("user", u);
			if(u.getUsername().equals("admin"))
				return "redirect:/api/projecthome/admin-page";
			return "redirect:/api/projecthome/parking";	
		}
	}
	@PostMapping("/signsave")
	public String signsave(Requests user)
	{
		if(projectService.addUserSignupRequest(user))
		{
			return "signup-success";
		}
		return "signup-fail";
	}

	@PostMapping("/reservespot")
	public String reserve(HttpSession session,@RequestParam Long id)
	{
		User u=(User)session.getAttribute("user");
		
		if(projectService.spotReservation(u, id))
		{
			return "redirect:/api/projecthome/parking";
		}
		
		return "redirect:/api/projecthome/parking-fail";
	}
	@PostMapping("/releasespot")
	public String release(@RequestParam Long id)
	{
		if(projectService.releaseReservation(id))
		{
			return "redirect:/api/projecthome/my-parking";
		}
		return "redirect:/api/projecthome/parking-fail";
	}
	
	@GetMapping("/manage-parking")
	public String manage(Model model)
	{
		model.addAttribute("spot",new Spot());
		model.addAttribute("spots",projectService.findavailable());
		return "manage-parking";
	}
	
	@PostMapping("/deletespot")
	public String deletespot(@RequestParam Long id)
	{
		if(projectService.deleteSpot(id))
		{
			return "redirect:/api/projecthome/manage-parking";
		}
		return "parking-fail";
	}
	@PostMapping("/addspot")
	public String addspot(@RequestParam String location)
	{
		if(projectService.addSpot(location))
		{
			return "redirect:/api/projecthome/manage-parking";
		}
		return "parking-fail";
	}
	
	
}
