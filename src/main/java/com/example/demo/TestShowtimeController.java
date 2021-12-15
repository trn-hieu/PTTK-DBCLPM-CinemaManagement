package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;


//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Showtime;
import com.example.demo.repository.ShowtimeRepository;

@SpringBootTest()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@Transactional
public class TestShowtimeController {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ShowtimeRepository showtimeRepo;
	
	// getForm() method
	@Order(1)
	@Test
	public void testGetForm_CinemaNotExist() throws Exception {
		String cinema_id = "10000";
		//rap chieu khong ton tai -> tra ve loi 404
		mockMvc.perform(get("/showtime/create/"+cinema_id)).andExpect(status().isNotFound());
		//van tra ve giao dien them lich chieu, ko bao loi
	}
	
	// saveShowtime() method
	@Order(2)
	@Test
	public void testSaveShowtime() throws Exception {
		// Truong hop lich chieu hop le(thoi gian hop le, khong trung lich)
		String date = "2022-02-20";
		String room_id="20";
		String movie_id ="1";
		String start ="09:00";
		String end = "10:30";
		String price = "80,000";
		
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price))
			.andExpect(view().name("redirect:/"));
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(1, list.size()); // kiem tra DB
	}
	
	@Order(3)
	@Test
	public void  movieNotExist()throws Exception {
		String date = "2022-02-20";
		String room_id="1";
		String movie_id ="999";  // movie id =999 khong ton tai
		String start ="09:00";
		String end = "10:30";
		String price = "80,000";
		
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price))
			.andExpect(view().name("redirect:/showtime/create/3?failed"));
		// loi do khoa ngoai khong ton tai -> Sql bao loi
	}
	
	@Order(4)
	@Test
	public void roomNotExist() throws Exception {
		String date = "2022-02-20";
		String room_id="999"; // room id =999 khong ton tai
		String movie_id ="1";
		String start ="09:00";
		String end = "10:30";
		String price = "80,000";
		
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price))
			.andExpect(view().name("redirect:/showtime/create/3?failed"));
		// loi do khoa ngoai khong ton tai -> Sql bao loi
	}
	
	@Order(5)
	@Test
	public void dateInputBeforPresent_start_end_present() throws Exception {
		//String date = String.valueOf(LocalDate.now());
		// ngay chieu < ngay hien tai
		// gio bat dau < gio ket thuc < gio hien tai
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -30);
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		c.add(Calendar.MINUTE, 20);
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		//System.out.println(start+"  "+end );
		
		String date= "2021-11-26";
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/showtime/create/3";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL+"?failed"));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Order(6)
	@Test
	public void dateInputBeforPresent_present_start_end() throws Exception {
		// ngay chieu < ngay hien tai
		// gio ket thuc > gio bat dau > gio hien tai
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, 2);
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		c.add(Calendar.MINUTE, 30);
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		//System.out.println(start+"  "+end );
		
		String date= "2021-11-26";
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/showtime/create/"+room_id;
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL+"?failed"));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Order(7)
	@Test
	public void dateInputBeforPresent_startendpresent() throws Exception {
		// ngay chieu < ngay hien tai
		// gio bat dau = gio ket thuc = gio hien tai
		Calendar c = Calendar.getInstance();
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		//System.out.println(start+"  "+end );
		
		String date= "2021-11-26";
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/showtime/create/"+room_id;
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL+"?failed"));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Order(8)
	@Test
	public void dateInputBeforPresent_startend_present() throws Exception {
		// ngay chieu < ngay hien tai
		// gio bat dau = gio ket thuc < gio hien tai
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -10); 
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		//System.out.println(start+"  "+end );
		
		String date= "2021-11-26";
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/showtime/create/"+room_id;
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL+"?failed"));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Order(9)
	@Test
	public void dateInputBeforPresent_present_startend() throws Exception {
		// ngay chieu < ngay hien tai
		// gio bat dau = gio ket thuc > gio hien tai
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, 20); 
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		//System.out.println(start+"  "+end );
		
		String date= "2021-11-26";
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/showtime/create/"+room_id;
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL+"?failed"));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Order(10)
	@Test
	public void dateInputBeforPresent_start_present_end() throws Exception {
		// ngay chieu < ngay hien tai
		// gio bat dau < gio hien tai < gio ket thuc
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -10); 
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		c.add(Calendar.MINUTE, 20); 
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		//System.out.println(start+"  "+end );
		
		String date= "2021-11-26";
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/showtime/create/"+room_id;
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL+"?failed"));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Order(11)
	@Test
	public void dateInputBeforPresent_startpresent_end() throws Exception {
		// ngay chieu < ngay hien tai
		// gio bat dau = gio hien tai < gio ket thuc
		Calendar c = Calendar.getInstance();
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		c.add(Calendar.MINUTE, 20); 
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		//System.out.println(start+"  "+end );
		
		String date= "2021-11-26";
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/showtime/create/"+room_id;
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL+"?failed"));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Order(12)
	@Test
	public void dateInputBeforPresent_start_presentend() throws Exception {
		// ngay chieu < ngay hien tai
		// gio bat dau < gio hien tai = gio ket thuc
		Calendar c = Calendar.getInstance();
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		c.add(Calendar.MINUTE, 20); 
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		//System.out.println(start+"  "+end );
		
		String date= "2021-11-26";
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/showtime/create/"+room_id;
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL+"?failed"));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Order(13)
	@Test
	public void dateInputBeforPresent_end_start_present() throws Exception {
		// ngay chieu < ngay hien tai
		// gio ket thuc < gio bat dau < gio hien tai
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -10); 
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		c.add(Calendar.MINUTE, -40); 
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		//System.out.println(start+"  "+end );
		
		String date= "2021-11-26";
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/showtime/create/"+room_id;
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL+"?failed"));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Order(14)
	@Test
	public void dateInputBeforPresent_present_end_start() throws Exception {
		// ngay chieu < ngay hien tai
		// gio hien tai < gio ket thuc < gio bat dau
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, 10); 
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		c.add(Calendar.MINUTE, 40); 
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		
		//System.out.println(start+"  "+end );
		
		String date= "2021-11-26";
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/showtime/create/"+room_id;
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL+"?failed"));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Order(15)
	@Test
	public void dateInputEqualPresent_start_end_present() throws Exception {
		// ngay chieu = ngay hien tai
		// gio bat dau < gio ket thuc < gio hien tai 
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -30);
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		c.add(Calendar.MINUTE, 20);
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		//System.out.println(start+"  "+end );
		
		String date= String.valueOf(LocalDate.now());
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/showtime/create/3";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL+"?failed"));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Order(16)
	@Test
	public void dateInputEqualPresent_present_start_end() throws Exception {
		// ngay chieu = ngay hien tai
		// gio hien tai < gio bat dau < gio ket thuc 
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, 2);
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		c.add(Calendar.MINUTE, 30);
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		//System.out.println(start+"  "+end );
		
		String date= String.valueOf(LocalDate.now());
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(1, list.size()); // kiem tra DB
	}
	
	@Order(17)
	@Test
	public void dateInputEqualPresent_startendpresent() throws Exception {
		// ngay chieu = ngay hien tai
		// gio bat dau = gio ket thuc = gio hien tai
		Calendar c = Calendar.getInstance();
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		//System.out.println(start+"  "+end );
		
		String date= String.valueOf(LocalDate.now());
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/showtime/create/"+room_id;
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL+"?failed"));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Order(18)
	@Test
	public void dateInputEqualPresent_startend_present() throws Exception {
		// ngay chieu = ngay hien tai
		// gio bat dau = gio ket thuc < gio hien tai
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -10); 
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		//System.out.println(start+"  "+end );
		
		String date= String.valueOf(LocalDate.now());
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/showtime/create/"+room_id;
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL+"?failed"));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Order(19)
	@Test
	public void dateInputEqualPresent_present_startend() throws Exception {
		// ngay chieu = ngay hien tai
		// gio bat dau = gio ket thuc > gio hien tai
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, 20); 
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		//System.out.println(start+"  "+end );
		
		String date= String.valueOf(LocalDate.now());
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/showtime/create/"+room_id;
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL+"?failed"));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Order(20)
	@Test
	public void dateInputEqualPresent_start_present_end() throws Exception {
		// ngay chieu = ngay hien tai
		// gio bat dau < gio hien tai < gio ket thuc
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -10); 
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		c.add(Calendar.MINUTE, 20); 
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		//System.out.println(start+"  "+end );
		
		String date= String.valueOf(LocalDate.now());
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/showtime/create/"+room_id;
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL+"?failed"));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Order(21)
	@Test
	public void dateInputEqualPresent_startpresent_end() throws Exception {
		// ngay chieu = ngay hien tai
		// gio bat dau = gio hien tai < gio ket thuc
		Calendar c = Calendar.getInstance();
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		c.add(Calendar.MINUTE, 20); 
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		//System.out.println(start+"  "+end );
		
		String date= String.valueOf(LocalDate.now());
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/showtime/create/"+room_id;
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL+"?failed"));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Order(22)
	@Test
	public void dateInputEqualPresent_start_presentend() throws Exception {
		// ngay chieu = ngay hien tai
		// gio bat dau < gio hien tai = gio ket thuc
		Calendar c = Calendar.getInstance();
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		c.add(Calendar.MINUTE, 20); 
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		//System.out.println(start+"  "+end );
		
		String date= String.valueOf(LocalDate.now());
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/showtime/create/"+room_id;
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL+"?failed"));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Order(23)
	@Test
	public void dateInputEqualPresent_end_start_present() throws Exception {
		// ngay chieu = ngay hien tai
		// gio ket thuc < gio bat dau < gio hien tai
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -10); 
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		c.add(Calendar.MINUTE, -40); 
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		//System.out.println(start+"  "+end );
		
		String date= String.valueOf(LocalDate.now());
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/showtime/create/"+room_id;
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL+"?failed"));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Order(24)
	@Test
	public void dateInputEqualPresent_present_end_start() throws Exception {
		// ngay chieu = ngay hien tai
		// gio hien tai < gio ket thuc < gio bat dau
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, 10); 
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		c.add(Calendar.MINUTE, 40); 
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		
		//System.out.println(start+"  "+end );
		
		String date= String.valueOf(LocalDate.now());
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(1, list.size()); // kiem tra DB
	}
	
	@Order(25)
	@Test
	public void dateInputAfterPresent_start_end_present() throws Exception {
		// ngay chieu > ngay hien tai
		// gio bat dau < gio ket thuc < gio hien tai 
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -30);
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		c.add(Calendar.MINUTE, 20);
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		//System.out.println(start+"  "+end );
		
		String date= String.valueOf(LocalDate.now());
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/showtime/create/3";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL+"?failed"));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Order(26)
	@Test
	public void dateInputAfterPresent_present_start_end() throws Exception {
		// ngay chieu > ngay hien tai
		// gio hien tai < gio bat dau < gio ket thuc 
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, 2);
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		c.add(Calendar.MINUTE, 30);
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		//System.out.println(start+"  "+end );
		
		String date= String.valueOf(LocalDate.now());
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(1, list.size()); // kiem tra DB
	}
	
	@Order(27)
	@Test
	public void dateInputAfterPresent_startendpresent() throws Exception {
		// ngay chieu > ngay hien tai
		// gio bat dau = gio ket thuc = gio hien tai
		Calendar c = Calendar.getInstance();
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		//System.out.println(start+"  "+end );
		
		String date= String.valueOf(LocalDate.now());
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/showtime/create/"+room_id;
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL+"?failed"));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Order(28)
	@Test
	public void dateInputAfterPresent_startend_present() throws Exception {
		// ngay chieu > ngay hien tai
		// gio bat dau = gio ket thuc < gio hien tai
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -10); 
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		//System.out.println(start+"  "+end );
		
		String date= String.valueOf(LocalDate.now());
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/showtime/create/"+room_id;
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL+"?failed"));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Order(29)
	@Test
	public void dateInputAfterPresent_present_startend() throws Exception {
		// ngay chieu > ngay hien tai
		// gio bat dau = gio ket thuc > gio hien tai
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, 20); 
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		//System.out.println(start+"  "+end );
		
		String date= String.valueOf(LocalDate.now());
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/showtime/create/"+room_id;
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL+"?failed"));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Order(30)
	@Test
	public void dateInputAfterPresent_start_present_end() throws Exception {
		// ngay chieu > ngay hien tai
		// gio bat dau < gio hien tai < gio ket thuc
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -10); 
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		c.add(Calendar.MINUTE, 20); 
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		//System.out.println(start+"  "+end );
		
		String date= String.valueOf(LocalDate.now());
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/showtime/create/"+room_id;
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL+"?failed"));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Order(31)
	@Test
	public void dateInputAfterPresent_startpresent_end() throws Exception {
		// ngay chieu > ngay hien tai
		// gio bat dau = gio hien tai < gio ket thuc
		Calendar c = Calendar.getInstance();
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		c.add(Calendar.MINUTE, 20); 
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		//System.out.println(start+"  "+end );
		
		String date= String.valueOf(LocalDate.now());
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/showtime/create/"+room_id;
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL+"?failed"));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Order(32)
	@Test
	public void dateInputAfterPresent_start_presentend() throws Exception {
		// ngay chieu > ngay hien tai
		// gio bat dau < gio hien tai = gio ket thuc
		Calendar c = Calendar.getInstance();
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		c.add(Calendar.MINUTE, 20); 
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		//System.out.println(start+"  "+end );
		
		String date= String.valueOf(LocalDate.now());
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/showtime/create/"+room_id;
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL+"?failed"));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Order(33)
	@Test
	public void dateInputAfterPresent_end_start_present() throws Exception {
		// ngay chieu > ngay hien tai
		// gio ket thuc < gio bat dau < gio hien tai
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -10); 
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		c.add(Calendar.MINUTE, -40); 
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		//System.out.println(start+"  "+end );
		
		String date= String.valueOf(LocalDate.now());
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/showtime/create/"+room_id;
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL+"?failed"));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Order(34)
	@Test
	public void dateInputAfterPresent_present_end_start() throws Exception {
		// ngay chieu > ngay hien tai
		// gio hien tai < gio ket thuc < gio bat dau
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, 10); 
		String end = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		c.add(Calendar.MINUTE, 40); 
		String start =String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		
		//System.out.println(start+"  "+end );
		
		String date= String.valueOf(LocalDate.now());
		String room_id="20"; 
		String movie_id ="1";
		String price = "80,000";
		
		String expect_redirectURL = "/";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", expect_redirectURL))
			.andExpect(view().name("redirect:"+expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(1, list.size()); // kiem tra DB
	}
}
