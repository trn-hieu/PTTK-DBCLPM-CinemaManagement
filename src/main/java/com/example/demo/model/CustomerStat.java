package com.example.demo.model;

public class CustomerStat extends Customer{
	private int totalTicket, totalService, totalRevenua;

	public CustomerStat() {
	}

	public CustomerStat(int totalTicket, int totalService, int totalRevenua) {
		super();
		this.totalTicket = totalTicket;
		this.totalService = totalService;
		this.totalRevenua = totalRevenua;
	}
	
	
}
