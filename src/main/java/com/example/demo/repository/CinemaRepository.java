package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Cinema;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long>{
	@Query(value = "select * from cinema", nativeQuery = true)
	List<Cinema> getAll();
 }
