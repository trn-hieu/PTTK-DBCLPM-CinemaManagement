package com.example.demo.model;

import java.util.Date;
import java.util.List;

public class TicketStat {
	private Date date;
	private int totalTicket;
	private long totalRevenua;
	private List<ShowtimeStat> detail;
	
	public TicketStat(Date date, int totalTicket, long totalRevenua, List<ShowtimeStat> detail) {
		super();
		this.date = date;
		this.totalTicket = totalTicket;
		this.totalRevenua = totalRevenua;
		this.detail = detail;
	}

	public TicketStat() {
		super();
	}
	
	
	
}
