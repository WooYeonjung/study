package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.JoDTO;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberDSLRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.MyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberRepository repository;
	private final MyRepository emrepository;
	private final MemberDSLRepository dsrepository;
	
	//**QueryDSL
	public List<JoDTO> joinDSL(){
		
		//return dsrepository.findMemberJoinDSL();  //ver01. Setter 접근
		return dsrepository.findMemberJoinDSL2();	//ver02. 필드 직접 접근
	};
	
	// ** Password 수정하기
	@Override
	public void updatePassword(String id, String password) {
		repository.updatePassword(id, password);
	}

	// 조별 멤버 출력
	// => ver01: JPARepository Method Naming 규약
	// => ver02: QueryDSL
	@Override
	public List<Member> findByJno(int jno) {
//		return repository.findByJno(jno);
		return dsrepository.findMemberJnoDSL(jno);
	}

	// ** selectList
	@Override
	public List<Member> selectList() {
		//ver01
		//return repository.findAll();
		
		//ver02 : EntityManager Test
		//return emrepository.emMemberList();
		//ver03 : Criteria Test
		return emrepository.cbMemberList();
	}

	// ** selectOne
	@Override
	public Member selectOne(String id) {
		// ver01
/*
		Optional<Member> result = repository.findById(id);
		if (result.isPresent())
			return result.get();
		else
			return null;
*/
		//ver02
		return emrepository.emMemberDetail(id);
		
	}

	// ** insert, Member
	@Override
	public Member save(Member entity) {
		return repository.save(entity);
	}

	// ** delete
	@Override
	public void deleteById(String id) {
		repository.deleteById(id);
	}

}// class
