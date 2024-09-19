package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.JoDTO;
import com.example.demo.entity.Member;

public interface MemberService {
	
	//**QueryDSL
	List<JoDTO> joinDSL();
	
	//*Password 수정하기
	void updatePassword(String id, String password);
	
	//조별 멤버 출력
	// => JPARepository Method Naming 규약
	List<Member> findByJno(int jno);
	
	// ** selectLists
	List<Member> selectList();

	// ** selectOne
	Member selectOne(String id);

	// ** insert,update
	Member save(Member entity);

	// ** delete
	void deleteById(String id);
	

}