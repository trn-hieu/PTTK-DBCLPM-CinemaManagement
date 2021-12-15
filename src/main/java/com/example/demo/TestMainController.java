package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TestMainController {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void testLoginSuccess() throws Exception {
		String username ="admin";
		String password ="admin";
		
		mockMvc.perform(post("/login").param("username", username)
				.param("password", password)).andExpect(view().name("redirect:/"));
	}
	
	@Test
	public void usernameNotExist() throws Exception {
		String username ="abcd";
		String password ="admin";
		
		mockMvc.perform(post("/login").param("username", username)
				.param("password", password)).andExpect(view().name("redirect:/login?failed"));
	}
	
	@Test
	public void wrongPassword() throws Exception {
		String username ="admin";
		String password ="123456";
		
		mockMvc.perform(post("/login").param("username", username)
				.param("password", password)).andExpect(view().name("redirect:/login?failed"));
	}
	
	@Test
	public void usernameEmpty() throws Exception {
		String username ="";
		String password ="admin";
		
		mockMvc.perform(post("/login").param("username", username)
				.param("password", password)).andExpect(view().name("redirect:/login?failed"));
	}
	
	@Test
	public void passwordEmpty() throws Exception {
		String username ="admin";
		String password ="";
		
		mockMvc.perform(post("/login").param("username", username)
				.param("password", password)).andExpect(view().name("redirect:/login?failed"));
	}
	
	@Test
	public void usernameIsSQLcommand() throws Exception {
		String username ="admin OR 1=1";
		String password ="123456";
		
		mockMvc.perform(post("/login").param("username", username)
				.param("password", password)).andExpect(view().name("redirect:/login?failed"));
	}
	
}
