package com.example.demo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "room")
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	
	private String feature;
	
	@OneToMany(mappedBy = "room")
	private List<Seat> seats;
	
	@OneToMany(mappedBy = "room")
	private List<Showtime> showtimes;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cinemaid")
	private Cinema cinema;

	public Room() {
		super();
	}

	public Room(String name, String feature, List<Seat> seats) {
		super();
		this.name = name;
		this.feature = feature;
		this.seats = seats;
	}
	

	public Room(long id, String name, String feature, List<Seat> seats, Cinema cinema) {
		super();
		this.id = id;
		this.name = name;
		this.feature = feature;
		this.seats = seats;
		this.cinema = cinema;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public Cinema getCinema() {
		return cinema;
	}

	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

	public List<Showtime> getShowtimes() {
		return showtimes;
	}

	public void setShowtimes(List<Showtime> showtimes) {
		this.showtimes = showtimes;
	}
	
	
}

