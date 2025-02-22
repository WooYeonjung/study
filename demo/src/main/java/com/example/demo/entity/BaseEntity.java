package com.example.demo.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

// 날짜데이터 처리하는 형식으로 사용하기 위한 클래스
//** BaseEntity
//=> 자료 등록시간, 수정시간 등 자동으로 추가되고 변경되는 값들을 자동으로 처리하기위한 BaseEntity 클래스 
//=> 추상클래스로 작성      
//=> @MappedSuperclass: 테이블로 생성되지않음
//=> @EntityListeners : 엔티티객체의 변화를 감지하는 리스너설정 (AuditingEntityListener.class 가 담당)
//   AuditingEntityListener 를 활성화 시키기 위해서는 
//   DemoJpaApplication.java 에 @EnableJpaAuditing 설정추가해야함.

@MappedSuperclass
@EntityListeners(value= {AuditingEntityListener.class})
@Getter
abstract class BaseEntity {

	@CreatedDate
	@Column(name="regdate", updatable=false) // updatable의 default => true 수정이 일어나지않기를 원하면 false
	private LocalDateTime regdate;
	
	@LastModifiedDate
	@Column(name="moddate") //수정가능
	private LocalDateTime moddate;
	
	
	
}
