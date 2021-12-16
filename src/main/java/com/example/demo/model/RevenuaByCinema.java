package com.example.demo.model;

import java.util.List;

public class RevenuaByCinema extends Cinema{
	private int totalTicket;
	private long totalRevenua;
	private List<ShowtimeStat> detail;	
	
	public RevenuaByCinema() {;
	}
	
	public RevenuaByCinema(String name, String address, String intro, List<Room> rooms, int totalTicket,
			long totalRevenua, List<ShowtimeStat> detail) {
		super(name, address, intro, rooms);
		this.totalTicket = totalTicket;
		this.totalRevenua = totalRevenua;
		this.detail = detail;
	}


	
	
	
	
	
}
