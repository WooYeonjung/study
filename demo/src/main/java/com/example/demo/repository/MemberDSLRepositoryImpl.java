package com.example.demo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.JoDTO;
import com.example.demo.entity.Member;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import static com.example.demo.entity.QMember.member;
import static com.example.demo.entity.QJo.jo;
//=> import static
//기본 import 구문은 '패키지 명시 없이 클래스를 사용'하게 해 주는데, 
//import static 구문은 한 단계 더 들어가 '클래스 명시 없이 static 변수 나 static 메서드를 사용하게 해줌.
@RequiredArgsConstructor
@Repository
public class MemberDSLRepositoryImpl implements MemberDSLRepository {
	
	private final JPAQueryFactory jpaQueryFactory;
	// => 생성을 위한 Bean 설정을 DemoConfig 에 추가해야함.
	
    // 1) Entity return
    // => Q클래스로 SQL 구문 작성하고 Entity return
    // => Parameter로 전달된 조원들만 출력하기 
    @Override
    public List<Member> findMemberJnoDSL(int jno) {

    	return jpaQueryFactory.selectFrom(member) //member.class 에서 import를 static 으로 했기때문에 class 생략
    					.where(member.jno.eq(jno).and(member.point.goe(10)))
    					.orderBy(member.age.desc())
    					.fetch(); //마무리!
    	
    	
    }
    
    // 2) Join & DTO return
    // => QueryDSL 에서 DTO 적용하기
    // => 메모장 QueryDSL사용법.txt 참고  
    //    4종류 방법중 1) Setter 접근 , 2) 필드 직접접근 적용
    
    // 2.1) Setter 접근 
    // => JoDTO의 setter 를 호출해서 ,  DTO 의 멤버변수에 injection 해주는 방식.
    // => Projections.bean(~~~)  로 접근
   
    
    // => fetchJoin()
    //    - 조인하는 대상 데이터를 즉시 로딩해서 가져온다
    //    - JPA의 지연로딩을 피하고 N+1문제를 해결할 수 있음.
    //    ( MemberRepository 참고, https://jie0025.tistory.com/518 )
    
    @Override
    public List<JoDTO> findMemberJoinDSL() {
    											//(return 타입, 가지고 올 것들)
    	return	jpaQueryFactory.select(Projections.bean(JoDTO.class, member.id,member.name,member.jno,member.age,jo.jname,jo.project,jo.slogan))
    				   .from(member)
    				   .leftJoin(jo)
    				   .on(member.jno.eq(jo.jno))
    				   .fetchJoin()
    				   .fetch();
    }
    
    
    // 2.2) 필드 직접 접근 
    // => 필드에 직접 접근해서 값을 injection 하는 방식.
    // =>Projections.fields(~~~) 로 접근
    //     그러므로 DTO 에 setter/getter 없어도 가능하며
    //     JoDTO의 멤버변수에 값이 injection 된다.
    @Override
    public List<JoDTO> findMemberJoinDSL2() {
    	
    	
    	return	jpaQueryFactory.select(Projections.fields(JoDTO.class, member.id,member.name,member.jno,member.age,jo.jname,jo.project,jo.slogan))
				   .from(member)
				   .leftJoin(jo)
				   .on(member.jno.eq(jo.jno))
				   .fetchJoin()
				   .fetch();
    }
  
}
