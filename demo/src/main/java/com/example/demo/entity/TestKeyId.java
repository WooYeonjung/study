package com.example.demo.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//** @IdClass 로 지정되는 식별자 클래스의 조건
//- 식별자 클래스의 접근 지정자는 public
//- Serializable (i) 구현
//- 디폴트 생성자 필수(Lombok의 @NoArgsConstructor 사용)
//- equals, hashCode 구현 (Lombok의 @Data 사용시 자동생성)
//- 식별자클래스의 변수명과 엔티티의 변수명 동일

@Data //필수
@NoArgsConstructor //필수
@AllArgsConstructor
public class TestKeyId implements Serializable {
	private static final long serialVersionUID = 1L;
	

	private String id;

	private int no;

}
