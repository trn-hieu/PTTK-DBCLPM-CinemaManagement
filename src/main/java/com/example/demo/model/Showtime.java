package com.example.demo.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "showtime")
public class Showtime implements Comparable<Showtime>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private Date date;
	
	private String startTime;
	private String endTime;
	
	@OneToOne
	@JoinColumn(name = "movieid")
	private Movie movie;
	
	@OneToOne
	@JoinColumn(name = "roomid")
	private Room room;
	
	private long price;
	
	@Transient
	private String status;
	
	
	public Showtime() {
		super();
	}
	public Showtime(String date, String startTime, String endTime, Movie movie, Room room, long price) throws ParseException {
		super();
		this.date = new SimpleDateFormat("dd/MM/yyy").parse(date);
		this.startTime = startTime;
		this.endTime =endTime;
		this.movie = movie;
		this.room = room;
		this.price= price;
	}
	public static boolean checkDate(String date, String starttime) throws Exception {
		Date currentTime = new SimpleDateFormat("yyy-mm-dd hh:mm").getCalendar().getTime();
		Date dateInput = new SimpleDateFormat("yyy-mm-dd hh:mm").parse(date+" "+starttime);
		if(dateInput.before(currentTime)) return true;
		else return false;
	}
	
	
	public static List<Showtime> setStatusList(List<Showtime> list){
		Calendar c = Calendar.getInstance();
		String currentTime = String.format("%02d", c.getTime().getHours()) +":"+String.format("%02d", c.getTime().getMinutes());
		List<Showtime> result =new ArrayList<>();
		for(int i=0;i<list.size();i++) {
			String star =list.get(i).getStartTime();
			String end = list.get(i).getEndTime();
			if(star.compareTo(currentTime) <=0 && (end.compareTo(currentTime) >= 0  || end.compareTo(star) < 0 )) {
				Showtime temp = list.get(i);
				temp.setStatus("Đang chiếu");
				result.add(temp);
			}else if(star.compareTo(currentTime) > 0) {
				Showtime temp = list.get(i);
				temp.setStatus("Sắp chiếu");
				result.add(temp);
			}else {
				Showtime temp = list.get(i);
				temp.setStatus("Đã chiếu");
				result.add(temp);
			}
		}
		return result;
	}
	
	@Override
	public int compareTo(Showtime arg0) {
		// TODO Auto-generated method stub
		return (arg0.getDate()+" "+arg0.startTime).compareTo(date+" "+startTime);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
