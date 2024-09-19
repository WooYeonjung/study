package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="jo")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Jo {
	@Id
	private int jno;
	private String jname;
	private String captain;
	private String project;
	private String slogan;
	
	@Transient
	// => 조장이름 출력용이지만, @Transient를 적용하면 적용되지 않음
    //      그러나 join없는 List 출력시 jsp의 el Tag를 위해 필요함
	private String name;
} //class
