package com.gradingsystem.gradingsystem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gradingsystem.gradingsystem.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("select count(U) from User U where U.username=:uname")
	public long findByUsername(@Param("uname") String uname);
	
	@Query("select U from User U where U.username=:uname")
	public User findByUsernameToLogin(@Param("uname") String uname);
} 