package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Member;

import jakarta.transaction.Transactional;

public interface MemberRepository extends 
						JpaRepository<Member,String>{
	
	// 1) JpaRepository Method Naming규약
	List<Member> findByJno(int jno);
	
	// 2) @Query 적용
	//=> updatePassword
	@Modifying
	@Transactional
	@Query("Update Member set password=:password where id=:id")
	void updatePassword(@Param("id") String id, @Param("password") String password);
	
	//=> Native Query 적용
	@Modifying
	@Transactional
	@Query(nativeQuery = true,value= "Update member set password=:password where id=:id")
	void updatePassword2(@Param("id") String id, @Param("password") String password);
	
	
}//interface
