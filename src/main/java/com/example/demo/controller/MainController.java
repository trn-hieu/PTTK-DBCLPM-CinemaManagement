package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Controller
public class MainController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/")
	public String getMainPage() {
		return "MainView";
	}
	
	@GetMapping("/login")
	public String getLoginPage() {
		return "LoginView";
	}
	
	@PostMapping("/login")
	public String checkLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
		User user = userRepository.findByUsername(username);
		if(user == null || !password.equals(user.getPassword()))
			return "redirect:/login?failed";
		
		return "redirect:/";
	}
}
