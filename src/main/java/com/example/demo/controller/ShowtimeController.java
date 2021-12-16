package com.example.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Movie;
import com.example.demo.model.Room;
import com.example.demo.model.Showtime;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.ShowtimeRepository;

@Controller
@RequestMapping("showtime")
public class ShowtimeController {
	@Autowired
	private MovieRepository movieRepo;
	@Autowired
	private RoomRepository roomRepo;
	@Autowired
	private ShowtimeRepository showtimeRepo;
	
	@GetMapping("create/{id}")
	public String getForm(@PathVariable(name="id")String id, Model model) throws ParseException {
		List<Movie> movies = movieRepo.getAll();
		List<Room> rooms = roomRepo.getByCinemaId(Long.parseLong(id));
		
		model.addAttribute("movies", movies);
		model.addAttribute("rooms", rooms);
		model.addAttribute("cinemaid", id);

 		return "AddShowtimeView";
	}
	
	@PostMapping("add")
	public String saveShowtime(@RequestParam("movie") long movie, @RequestParam("date") String date
			, @RequestParam("starttime") String starttime, @RequestParam("endtime") String endtime
			, @RequestParam("room") long room
			, @RequestParam("price") String price, HttpServletRequest request) throws Exception {
		System.out.println(movie +" "+room+" "+date+" "+starttime+" "+endtime+" "+price+request.getHeader("Referer"));
		
		int checkDB = showtimeRepo.checkDuplicate(room, date, starttime, endtime);
		if(checkDB > 0)
			return "redirect:"+request.getHeader("Referer")+"?failed";
		
		long ticketPrice = Long.parseLong(price.replaceAll(",", ""));
		int saveResult =showtimeRepo.save(date,starttime, endtime,movie,room, ticketPrice);
		System.out.println(saveResult);
		return"redirect:/?success";
	}
	
//	@GetMapping("all")
//	public String getResult(Model model, @RequestParam(name = "date",required = false)String date,
//			@RequestParam(name = "start",required = false)String start, @RequestParam(name = "end",required = false)String end,
//			@RequestParam(name = "room",required = false)String room) {
//
//		if(date != null || start!=null || end!=null ) {
//			if(date.equals("")) date=null;
//			if(start.equals("")) start=null;
//			if(end.equals("")) end=null;
//			//if(room.equals(" ")) room=null;
//		}
//		List<Showtime> list = showtimeRepo.search(date, start, end, room);
//		model.addAttribute("listAll", list);
//		return "ShowtimeList";
//	}
}
