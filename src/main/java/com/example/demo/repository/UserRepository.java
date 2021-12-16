package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
	
	
	@Query(value= "SELECT CASE WHEN (COUNT(*) > 0) THEN 'true' ELSE 'false' END FROM user  WHERE username= :username and password = :password", nativeQuery = true)
	boolean checkLogin(@Param("username")String username, @Param("password") String password);
}
