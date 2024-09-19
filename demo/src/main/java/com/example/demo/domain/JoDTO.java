package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JoDTO extends MemberDTO {

	// ** 멤버 변수 정의
	private int jno;
	private String jname;
	private String captain;
	private String project;
	private String slogan;
	
	
	// => 조장이름 촐력을 위한 joinList() 구문의 return 값을 위해 추가함.
	//	  select 구문과 컬럼순서 & Type이 같아야함.
	public JoDTO( int jno, String jname, String captain,String name,String project, String slogan) {
		super.setName(name); //조상에 있는 것이기때문에 setName한것임.
		this.jno=jno;
		this.jname = jname;
		this.captain = captain;
		this.project = project;
		this.slogan = slogan;
	}

	@Override
	public String toString() {
		return "JoDTO [jno=" + jno + ", jname=" + jname + ", captain=" + captain + ", id=" + getId()+ ", name=" + getName() + ", age=" + getAge()+", project=" + project + ", slogan="
				+ slogan + "]";
	}

}// class
