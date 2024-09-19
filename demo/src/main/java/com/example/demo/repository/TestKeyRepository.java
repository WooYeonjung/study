package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.TestKey;
import com.example.demo.entity.TestKeyId;

import jakarta.transaction.Transactional;

//** JPA 복합키실습 (@IdClass 방법) 과 count 값 수정하기  
//=> DML 사용시 @Modifying, @Transactional 적용 필수.
//1) JPQL : OK
//2) Native_SQL : OK
//3) default 메서드로 구현
// => 복잡한 계산식에 활용
// => 계산식을 메서드로 구현하고, 쿼리메서드를 호출하여 적용
//     -> 서비스는 default 메서드를 호출
/*JPQL : jpa query ->jpa 쿼리는 entity가 기준!
Native_SQL : mysql query*/
public interface TestKeyRepository extends JpaRepository<TestKey, TestKeyId>{
	
	@Transactional //jakarta
	@Modifying
	// =>JPQL 적용 : Entity명 사용 ->Entity가 기준이기때문(대소문자 구분함.)& alias 적용해주어야함.
	// parameter 값 적용 => :param명
	//@Query("Update TestKey t Set t.count = t.count+:count Where t.id=:id and t.no=:no ")
	
	//=> Native_SQL :MySql Sql 구분 그대로 적용(table명 사용->대소문자 상관없음)
	@Query(nativeQuery =true, 
		value = "Update TestKey Set count = count+:count Where id=:id and no=:no ")
	void updateCount(@Param("id") String id,@Param("no") int no,@Param("count") int count);
	
	@Transactional //jakarta
	@Modifying
	//=> JPQL : insert 는 지원하지않음 (update, delete 는 가능)
    //@Query("insert into TestKey VALUES (:id, :no, :count, :name)"
    //        + " ON DUPLICATE KEY UPDATE count = count+:count")
    //=> Native_SQL
	@Query(nativeQuery =true
	,value= "INSERT INTO TestKey (id, no, name, count) VALUES (:id, :no, :name, :count) ON DUPLICATE KEY UPDATE count = count+:count")
	void dupUpdateCount(@Param("id") String id,@Param("no") int no,@Param("name") String name,@Param("count") int count);
	
	//3) default 메서드 활용 //인터페이스에서 구현부를 만들수 있는 메서드이기때문에.
	default int calcCount(@Param("id") String id,@Param("no") int no,@Param("count") int count) {
		//3.1) 계산 로직 구현해서 db(Table)에 전달할 값을 계산 
		int result = count*no+100;
		System.out.println("** clacCount result => "+ result);
		
		//3.2) result (계산결과) 를 Table로 전달 & 저장
		// => Update Sql 구문이 필요
		updateSql(id,no,result);
		return result;
	} //calcCount
	
	@Modifying
	@Transactional
	// =>JPQL : update, delete 가능
	@Query("Update TestKey t Set t.count=:result Where t.id=:id and t.no=:no")
	void updateSql(@Param("id") String id,@Param("no") int no,@Param("result") int result);
}
