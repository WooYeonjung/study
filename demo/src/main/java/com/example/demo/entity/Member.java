package com.example.demo.entity;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="member")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

	// ** 맴버변수
	@Id
	private String id;
	
	@Column(updatable=false)
	//=> 별도로 수정하기 위함.
	private String password;
	private String name;
	private int age;
	private int jno;
	private String info;
	private double point;
	private String birthday;
	private String rid;// 추천인 recommened id
	private String uploadfile; //테이블 보관용(파일명)
	
	@Transient
	// => SQL 구문처리시 제외시켜줌
	// => jakarta.persistence.Transient
	private MultipartFile uploadfilef; 

}
