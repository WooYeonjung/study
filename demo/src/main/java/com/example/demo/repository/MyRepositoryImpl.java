package com.example.demo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

//** EntityManager 사용하기
//=> 영속 컨텍스트에 접근하여 엔티티에 대한 DB 작업을 제공

@Transactional
@Repository //spring 에서 사용하려면 생성자가 있어야함
@RequiredArgsConstructor 
public class MyRepositoryImpl implements MyRepository{
	
	
	private final EntityManager em;
	
	/*=>
	  생략 @RequiredArgsConstructor 적용
	public MyRepositoryImpl(EntityManager em) {  //Repository로 사용하기 위해 생성자를 초기화해줌.
		this.em = em;
	}
	*/
	@Override
	public List<Member> emMemberList() {
		return em.createQuery("select m from Member m order by m.jno asc", Member.class).getResultList();
	}
	
	@Override
	public Member emMemberDetail(String id) {
		return em.createQuery("select m from Member m where m.id=:id",Member.class).setParameter("id", id).getSingleResult();
	}
	
	
	// *** JPA CriteriaBuilder (객체지향 쿼리 빌더)
	@Override
	public List<Member> cbMemberList() {  


	    //1) CriteriaBuilder 객체 생성
	    // => EntityManager 혹은 EntityManagerFactory 에서 제공받음.
		CriteriaBuilder builder = em.getCriteriaBuilder();
	     
	    //2) CriteriaQuery 객체 생성 
	    //=> CriteriaBuilder 에서 제공받음.
	    //=> 반환타입을 알수 없다면 제네릭타입을 Object 로 준다.
		CriteriaQuery<Member> query = builder.createQuery(Member.class);
	           
	    //3) 조회의 시작점을 뜻하는 Root객체 생성 
	    //=> 이때 m 은 JPQL에서 별칭이라고 생각하면 된다.
	    //=> 반환타입을 알수 없다면 제네릭타입을 Object로 준다.
		Root<Member> m = query.from(Member.class);
		
	     //4) 검색조건 정의
	      Predicate jnoEqual = builder.equal(m.get("jno"), 1);

	     //5) 정렬조건 정의
	       Order ageDesc = builder.desc(m.get("age"));

	    // 6) 쿼리문 완성 
	         query.select(m)
	         	  .distinct(true)//중복제거
	         	  .where(jnoEqual)
	         	  .orderBy(ageDesc);
	    //위에서 반환타입이 명확하지 않다면 Query 객체로 쿼리를 만들고 결과를 받는다.
	    TypedQuery<Member> typeQuery = em.createQuery(query);
	     
	    List<Member> members = typeQuery.getResultList();
	    return members;

	} //cbMemberList
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
