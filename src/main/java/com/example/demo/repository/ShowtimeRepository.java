package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Showtime;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long>{
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO	showtime(date, end_time, start_time,movieid, roomid, price)"
			+ "VALUES (?1, ?2, ?3, ?4, ?5, ?6)",nativeQuery = true)
	int save(String date, String endtime, String startime, long movie, long room, long price);
	
	
	@Query(value = 
			 "SELECT count(*) FROM cinema.showtime "
			+ "where roomid= ?1 and "
			+ "CASE when (?4 > ?3) then"
			+ "(cinema.showtime.date = ?2 and start_time < end_time "
			+ "and((start_time < ?3 And end_time >= ?3)"
			+ "or(start_time >= ?3 and end_time <= ?4)"
			+ "or(start_time <= ?4 and end_time > ?4)))"
			+ "OR(date = DATE_SUB(?2, INTERVAL 1 DAY) and start_time > end_time "
			+ "and(end_time >= ?3)"
			+ ")"
			+ "OR (date = ?2 and start_time > end_time and start_time <= ?4)"
			+ "else (date = DATE_ADD(?2, INTERVAL 1 DAY) and start_time < ?3 )"
			+ "OR ((date = ?2 ) and ((start_time < ?3 and end_time >= ?3)"
			+ "OR (start_time >= ?3 and end_time > ?3)"
			+ "OR (start_time >= ?3 and end_time <= ?4)"
			+ "OR (start_time <= ?3 and end_time <= ?4 and start_time > end_time)"
			+ "OR (start_time >= ?3 and end_time >= ?4)"
			+ "OR (start_time <= ?3 and end_time >= ?4 and start_time > end_time)"
			+ "))"
			+ "end", nativeQuery = true)
	int checkDuplicate(long roomid, String date, String start, String end);
	
//	@Query(value = "SELECT * FROM showtime "
//			+ "WHERE date = cast(now() as  date)", nativeQuery = true)
//	List<Showtime> getTodayShowtime();
	
	
	@Query(value = "Select * from showtime where ((:date is null and date=cast(now() as  date) )or showtime.date = :date) and "
			+ "(:room is null or showtime.roomid = :room) and (:start is null or start_time = :start) "
			+ "and (:end is null or end_time = :end)",nativeQuery = true)
	List<Showtime> search( String date, String start, String end, String room);
	
}
