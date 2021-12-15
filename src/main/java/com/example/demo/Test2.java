package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
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
public class Test2 {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ShowtimeRepository showtimeRepo;
	
	
	/// Kiem tra trung lich khi lich moi chi keo dai trong 1 ngay
	
	@Test
	@Order(35)
	public void lich_moi_trong_1_ngay_TH1() throws Exception {
		// lich cu bat dau va ket thuc truoc lich moi
		// lich chieu da co
		showtimeRepo.save("2022-01-10", "08:00", "10:00", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="04:00";
		String end = "06:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/?success";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(1, list.size()); // kiem tra DB
	}
	
	@Test
	@Order(36)
	public void lich_moi_trong_1_ngay_TH2() throws Exception {
		// lich cu bat dau va ket thuc truoc lich moi, lich chieu cu keo dai sang ngay hom sau
		// lich chieu da co
		showtimeRepo.save("2022-01-10", "20:00", "01:00", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="04:00";
		String end = "06:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/?success";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(1, list.size()); // kiem tra DB
	}
	
	@Test
	@Order(37)
	public void lich_moi_trong_1_ngay_TH3() throws Exception {
		// lich cu bat dau cung luc voi lich moi va ket thuc truoc lich moi
		// lich chieu da co
		showtimeRepo.save("2022-01-10", "04:00", "05:00", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="04:00";
		String end = "06:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Test
	@Order(38)
	public void lich_moi_trong_1_ngay_TH4() throws Exception {
		// thoi gian ket thuc cua lich moi va cu trung nhau, lich moi bat dau chieu truoc lich cu
		// lich chieu da co
		showtimeRepo.save("2022-01-10", "05:00", "06:00", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="04:00";
		String end = "06:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Test
	@Order(39)
	public void lich_moi_trong_1_ngay_TH5() throws Exception {
		// lich cu bat dau chieu truoc, ket thuc luc lich moi bat dau chieu
		// lich chieu da co
		showtimeRepo.save("2022-01-10", "02:00", "04:00", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="04:00";
		String end = "06:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Test
	@Order(40)
	public void lich_moi_trong_1_ngay_TH6() throws Exception {
		// lich cu bat dau sau lich moi, ket thuc truoc lich moi
		// lich chieu da co
		showtimeRepo.save("2022-01-10", "04:30", "05:30", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="04:00";
		String end = "06:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Test
	@Order(41)
	public void lich_moi_trong_1_ngay_TH7() throws Exception {
		// lich cu bbat dau lich moi ket thuc
		// lich chieu da co
		showtimeRepo.save("2022-01-10", "06:00", "08:00", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="04:00";
		String end = "06:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Test
	@Order(42)
	public void lich_moi_trong_1_ngay_TH8() throws Exception {
		// lich cu bat dau va ket thuc truoc khi lich moi bat dau
		// lich chieu da co
		showtimeRepo.save("2022-01-10", "01:30", "03:00", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="04:00";
		String end = "06:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/?success";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(1, list.size()); // kiem tra DB
	}
	
	@Test
	@Order(43)
	public void lich_moi_trong_1_ngay_TH9() throws Exception {
		// lich cu bat dau truoc lich moi, ket thuc trong khi lich moi dang chieu
		// lich chieu da co
		showtimeRepo.save("2022-01-10", "03:00", "05:00", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="04:00";
		String end = "06:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Test
	@Order(44)
	public void lich_moi_trong_1_ngay_TH10() throws Exception {
		// lich moi bat dau chieu truoc, ket thuc luc lich cu dang chieu 
		// lich chieu da co
		showtimeRepo.save("2022-01-10", "05:00", "07:00", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="04:00";
		String end = "06:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Test
	@Order(45)
	public void lich_moi_trong_1_ngay_TH11() throws Exception {
		// lich moi chieu trong khoang thoi gian lich cu dang chieu
		// lich chieu da co
		showtimeRepo.save("2022-01-10", "03:00", "07:00", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="04:00";
		String end = "06:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB
	}
	
	@Test
	@Order(46)
	public void lich_moi_trong_1_ngay_TH12() throws Exception {
		// lich moi va lich cu trung thoi gian bat dau va ket thuc
		// lich chieu da co
		showtimeRepo.save("2022-01-10", "04:00", "06:00", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="04:00";
		String end = "06:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(1, list.size()); // kiem tra DB  -> truong hop nay neu luu thanh cong se co 2 ban ghi giong nhau
	}
	
	@Test
	@Order(47)
	public void lich_moi_trong_1_ngay_TH13() throws Exception {
		// lich cu bat dau tu ngay hôm truoc va ket thuc truoc khi lich moi bat dau
		// lich chieu da co
		showtimeRepo.save("2022-01-09", "23:00", "02:00", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="04:00";
		String end = "06:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/?success";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(1, list.size()); // kiem tra DB  
	}
	
	@Test
	@Order(48)
	public void lich_moi_trong_1_ngay_TH14() throws Exception {
		// lich cu bat dau tu ngay hôm truoc va ket thuc dung luc lich moi bat dau chieu
		// lich chieu da co
		showtimeRepo.save("2022-01-09", "23:00", "04:00", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="04:00";
		String end = "06:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB  
	}
	
	@Test
	@Order(49)
	public void lich_moi_trong_1_ngay_TH15() throws Exception {
		// lich cu bat dau tu ngay hôm truoc va ket thuc khi lich moi dang chieu
		// lich chieu da co
		showtimeRepo.save("2022-01-09", "23:00", "05:00", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="04:00";
		String end = "06:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB  
	}
	
	@Test
	@Order(50)
	public void lich_moi_trong_1_ngay_TH16() throws Exception {
		// lich cu bat dau tu ngay hôm truoc va ket thuc khi lich moi ket thuc
		// lich chieu da co
		showtimeRepo.save("2022-01-09", "23:00", "06:00", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="04:00";
		String end = "06:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB  
	}
	
	@Test
	@Order(51)
	public void lich_moi_trong_1_ngay_TH17() throws Exception {
		// lich cu bat dau tu ngay hôm truoc va ket thuc sau khi lich moi ket thuc
		// lich chieu da co
		showtimeRepo.save("2022-01-09", "23:00", "08:00", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="04:00";
		String end = "06:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB  
	}
	
	// Kiem tra trung lich khi lich chieu moi keo dai sang ngay hom sau
	
	@Test
	@Order(52)
	public void lich_moi_qua_ngay_hom_sau_TH01() throws Exception {
		// lich cu bat dau va ket thuc truoc khi lich moi bat dau, lich moi ket thuc vao ngay hom sau
		// lich chieu da co
		showtimeRepo.save("2022-01-10", "20:00", "21:30", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="22:00";
		String end = "03:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/?success";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(1, list.size()); // kiem tra DB  
	}
	
	@Test
	@Order(53)
	public void lich_moi_qua_ngay_hom_sau_TH02() throws Exception {
		// lich cu va lich moi trung thoi gian bat dau, lich cu ket thuc truoc 0h, lich moi ket thuc vao ngay hom sau
		// lich chieu da co
		showtimeRepo.save("2022-01-10", "22:00", "23:30", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="22:00";
		String end = "03:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB  
	}
	
	@Test
	@Order(54)
	public void lich_moi_qua_ngay_hom_sau_TH03() throws Exception {
		// lich cu bat dau vao ngay hom sau, dung luc lich moi ket thuc
		// lich chieu da co
		showtimeRepo.save("2022-01-11", "03:00", "04:30", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="22:00";
		String end = "03:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB  
	}
	
	@Test
	@Order(55)
	public void lich_moi_qua_ngay_hom_sau_TH04() throws Exception {
		// lich cu bat dau truoc lich moi, ket thuc luc lich moi bat dau
		// lich chieu da co
		showtimeRepo.save("2022-01-10", "20:00", "22:00", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="22:00";
		String end = "03:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB  
	}
	
	@Test
	@Order(56)
	public void lich_moi_qua_ngay_hom_sau_TH05() throws Exception {
		// lich moi bat dau dau truoc, ket thuc sau lich cu, lich cu chieu qua ngay hom sau
		// lich chieu da co
		showtimeRepo.save("2022-01-10", "22:30", "02:00", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="22:00";
		String end = "03:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB  
	}
	
	@Test
	@Order(57)
	public void lich_moi_qua_ngay_hom_sau_TH06() throws Exception {
		// lich cu bat dau vao ngay hom sau, truoc luc lich moi ket thuc và ket thuc cung luc voi lich moi
		// lich chieu da co
		showtimeRepo.save("2022-01-11", "01:30", "03:00", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="22:00";
		String end = "03:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB  
	}
	
	@Test
	@Order(58)
	public void lich_moi_qua_ngay_hom_sau_TH07() throws Exception {
		// lich cu bat dau vao ngay hom sau, sau khi lich moi ket thuc
		// lich chieu da co
		showtimeRepo.save("2022-01-11", "04:30", "06:00", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="22:00";
		String end = "03:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB  
	}
	
	@Test
	@Order(59)
	public void lich_moi_qua_ngay_hom_sau_TH08() throws Exception {
		// lich cu bat dau truoc lich moi, ket thuc sau khi lich moi bat dau va truoc 0h 
		// lich chieu da co
		showtimeRepo.save("2022-01-10", "21:00", "23:00", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="22:00";
		String end = "03:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB  
	}
	
	@Test
	@Order(60)
	public void lich_moi_qua_ngay_hom_sau_TH09() throws Exception {
		// lich cu bat dau sau lich moi, ket thuc sau khi lich moi bat dau va truoc 0h 
		// lich chieu da co
		showtimeRepo.save("2022-01-10", "22:30", "23:30", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="22:00";
		String end = "03:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB  
	}
	
	@Test
	@Order(61)
	public void lich_moi_qua_ngay_hom_sau_TH10() throws Exception {
		// lich cu bat dau vao ngay hom sau, bat dau va ket thuc truoc khi lich moi ket thuc
		// lich chieu da co
		showtimeRepo.save("2022-01-11", "00:30", "02:30", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="22:00";
		String end = "03:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB  
	}
	
	@Test
	@Order(62)
	public void lich_moi_qua_ngay_hom_sau_TH11() throws Exception {
		// lich cu bat dau vao ngay hom sau, bat dau truoc khi lich moi ket thuc va ket thuc sau lich moi
		// lich chieu da co
		showtimeRepo.save("2022-01-11", "02:30", "05:30", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="22:00";
		String end = "03:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB  
	}
	
	@Test
	@Order(63)
	public void lich_moi_qua_ngay_hom_sau_TH12() throws Exception {
		// lich cu bat dau vao 0h ngay hom sauva ket thuc truoc khi lich moi ket thuc
		// lich chieu da co
		showtimeRepo.save("2022-01-11", "00:00", "02:30", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="22:00";
		String end = "03:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB  
	}
	
	@Test
	@Order(64)
	public void lich_moi_qua_ngay_hom_sau_TH13() throws Exception {
		// lich cu bat dau sau lich moi va truoc 0h, ket thuc vao 0h
		// lich chieu da co
		showtimeRepo.save("2022-01-10", "22:30", "00:00", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="22:00";
		String end = "03:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB  
	}
	
	@Test
	@Order(65)
	public void lich_moi_qua_ngay_hom_sau_TH14() throws Exception {
		// lich cu bat dau truoc lich moi va ket thuc luc 0h
		// lich chieu da co
		showtimeRepo.save("2022-01-10", "20:30", "00:00", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="22:00";
		String end = "03:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB  
	}
	
	@Test
	@Order(66)
	public void lich_moi_qua_ngay_hom_sau_TH15() throws Exception {
		// lich cu bat dau truoc lich moi va ket thuc vao ngay hom sau, truoc khi lich moi ket thuc
		// lich chieu da co
		showtimeRepo.save("2022-01-10", "20:30", "01:00", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="22:00";
		String end = "03:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB  
	}
	
	@Test
	@Order(67)
	public void lich_moi_qua_ngay_hom_sau_TH16() throws Exception {
		// lich cu va lich moi bat dau cung luc, lich cu ket thuc luc 0h
		// lich chieu da co
		showtimeRepo.save("2022-01-10", "22:00", "00:00", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="22:00";
		String end = "03:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB  
	}
	
	@Test
	@Order(68)
	public void lich_moi_qua_ngay_hom_sau_TH17() throws Exception {
		// lich cu va lich moi ket thuc cung luc, lich cu bat dau luc 0h ngay hom sau
		// lich chieu da co
		showtimeRepo.save("2022-01-11", "00:00", "03:00", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="22:00";
		String end = "03:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB  
	}
	
	@Test
	@Order(67)
	public void lich_moi_qua_ngay_hom_sau_TH18() throws Exception {
		// lich cu ket thuc sau lich moi, lich cu bat dau luc 0h ngay hom sau
		// lich chieu da co
		showtimeRepo.save("2022-01-11", "00:00", "04:00", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="22:00";
		String end = "03:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB  
	}
	
	@Test
	@Order(68)
	public void lich_moi_qua_ngay_hom_sau_TH19() throws Exception {
		// lich cu bat dau sau lich moi va truoc 0h, lich cu ket thuc sau lich moi
		// lich chieu da co
		showtimeRepo.save("2022-01-10", "23:00", "04:00", 1, 20, 80000);
		
		// lich chieu moi
		String date = "2022-01-10";
		String room_id="20";
		String movie_id ="1";
		String start ="22:00";
		String end = "03:00";
		String price = "80,000";
		
		String clientURL = "/showtime/create/20";
		String expect_redirectURL = "redirect:/showtime/create/20?failed";
		mockMvc.perform(post("/showtime/add")
				.param("movie", movie_id).param("date", date).param("starttime", start)
				.param("endtime", end).param("room", room_id).param("price", price).header("Referer", clientURL))
			.andExpect(view().name(expect_redirectURL));
		
		List<Showtime> list = showtimeRepo.search(date, start, end, room_id);
		assertEquals(0, list.size()); // kiem tra DB  
	}
}
