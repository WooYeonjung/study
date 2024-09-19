package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "guestbook")
// => 엔티티클래스명과 동일하면 생략가능

@ToString
@Getter
//=>  Entity 와 DTO 를 분리해서 사용하기위해 @Setter 생략하고
//생성자주입만 허용함
//그러나 필요시에는 @Setter 적용가능

@Builder  //@AllArgsConstructor 있어야함
@AllArgsConstructor 
@NoArgsConstructor
public class GuestBook extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long gno; //auto_Increment
	
	@Column(length=100,nullable = false) //notNull
	private String title;
	
	@Column(length=1000,nullable = false)
	private String content;
	
	@Column(length=30,nullable = false)
	private String writer;
	
	public void chageTitle(String title) {
		this.title = title;
	}
	
	public void chageContent(String content) {
		this.content = content;
	}
	
	
}//class
