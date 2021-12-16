package com.example.demo.model;

import java.util.List;

public class RevenuaByMovie extends Movie{
	private int totalTicket;
	private long totalRevenua;
	private List<ShowtimeStat> detail;
	
	public RevenuaByMovie() {
		
	}

	public RevenuaByMovie(int totalTicket, long totalRevenua, List<ShowtimeStat> detail) {
		super();
		this.totalTicket = totalTicket;
		this.totalRevenua = totalRevenua;
		this.detail = detail;
	}

	
	
	
}
