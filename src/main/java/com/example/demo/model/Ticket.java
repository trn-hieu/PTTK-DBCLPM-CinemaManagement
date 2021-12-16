package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "ticket")
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "discount")
	private long discount;
	
	@Transient
	private long total;
	
	@ManyToOne
	@JoinColumn(name = "seatid")
	private Seat seat;
	
	@ManyToOne
	@JoinColumn(name = "showtimeid")
	private Showtime showtime;
	
	@ManyToOne
	@JoinColumn(name = "billid")
	private Bill bill;

	public Ticket() {
		super();
	}

	public Ticket(long discount, long total, Seat seat, Showtime showtime) {
		super();
		this.discount = discount;
		this.total = total;
		this.seat = seat;
		this.showtime = showtime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDiscount() {
		return discount;
	}

	public void setDiscount(long discount) {
		this.discount = discount;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public Showtime getShowtime() {
		return showtime;
	}

	public void setShowtime(Showtime showtime) {
		this.showtime = showtime;
	}
	
	
}
