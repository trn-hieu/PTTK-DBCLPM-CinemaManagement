package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Cinema;
import com.example.demo.repository.CinemaRepository;

@Controller
@RequestMapping("cinema")
public class CinemaController {
	@Autowired
	private CinemaRepository cinemaRepo;
	
	@GetMapping("all")
	public String getAll(Model model) {
		List<Cinema> list = cinemaRepo.getAll();
		model.addAttribute("list", list);
		return "SelectCinemaView";
	}
	
//	@PostMapping("all")
//	public String redirect(@RequestParam(name ="cinema")String id) {
//		return "redirect:/showtime/create?cinema="+id;
//	}
	
}
