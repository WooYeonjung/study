package com.example.demo.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.domain.GuestBookDTO;
import com.example.demo.domain.PageRequestDTO;
import com.example.demo.domain.PageResultDTO;
import com.example.demo.entity.GuestBook;
import com.example.demo.entity.TestKey;
import com.example.demo.entity.TestKeyId;
import com.example.demo.service.GuestBookService;
import com.example.demo.service.TestKeyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequiredArgsConstructor //final 사용한 아이들에게만 생성자 주입
public class HomeController {
	private final GuestBookService service;
	private final TestKeyService tservice;
	
	@GetMapping({ "/",  "/home" })
	public String home(Locale locale, Model model) {

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formmatedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formmatedDate);
		return "home";
	}// home

	@GetMapping("/axtestform")
	public String axTestForm() {
		return "axTest/axTestForm";
	}
	
	
	//*** JPA 복합키 Test *********************************
	//1) Save
	// => 동일 Data 입력 시 Exception 발생하지 않음(update 로 처리하기때문)
	@GetMapping("/tsave")
	public String tsave() {
		TestKey entity = TestKey.builder()
							.id("apple")
							.no(2)
							.name("홍길동")
							.count(1)
							.build();
		
		try {
			tservice.save(entity);
			log.info("** TestKey Save => "+ entity);
		}catch(Exception e) {
			log.error("** Save Exception => " + e.toString());
		}
		
		
		return "redirect:home";
	}
	
	@GetMapping("/tupdate")
	public String tupdate() {
		// ver01
		TestKey entity = TestKey.builder()
							.id("apple")
							.no(1)
							.name("스티브")
							.count(1)
							.build();
		
		try {
			tservice.save(entity);
			log.info("** TestKey Update => "+ entity);
		}catch(Exception e) {
			log.error("** Save Exception => " + e.toString());
		}
		
		return "redirect:home";
	}//tdelete
	
	//=> Count Update
	// - 기존의 count += 전달된 count 값
	// - JpaRepository에서 상속받은 기본구문 외에 새로운 sql 구문 필요함
	// - TestkeyRepository 에 메서드 추가 필요함
	// - 필요한 컬럼 : id, no, count   
	// 파라미터를 개별적으로 처리할 것임.
	// -쿼리 스트링으로 전달 => /tupdatecount?id=apple&no=2&count=5
	@GetMapping("/tupdatecount")
	public String tcupdatecount(String id, int no, int count) {
		
		try {
			tservice.updateCount(id,no,count);
			log.info("** TestKey updateCount => "+ id+", "+no+", "+count);
		}catch(Exception e) {
			log.error("** updateCount Exception => " + e.toString());
		}
		
		return "redirect:home";
	}//tcupdatecount
	
	
	//=> Duplicate update count 증가 
	// /tdupupdate?id=apple&no=3&name=사과&count=5
	@GetMapping("/tdupupdate")
	public String tdupupdate(String id, int no,String name, int count) {
		try {
			tservice.dupUpdateCount(id,no,name,count);
			log.info("** TestKey dupUpdateCount => "+ id+", "+no+", "+name+", "+count); 
		}catch(Exception e) {
			log.error("** dupUpdateCount Exception => " + e.toString());
		}
		
		return "redirect:home";
	}
	
	//3) TestKeyRepository default 메서드 활용
	// -쿼리 스트링으로 전달 => /tcalc?id=banana&no=2&count=30
	@GetMapping("/tcalc")
	public String tcalc(String id, int no, int count) {
		
		try {
			/* tservice.calcCount(id,no,count); */
			/* log.info("** TestKey calcCount => "+ id+", "+no+", "+count); */
			log.info("** TestKey calcCount => result "+ tservice.calcCount(id,no,count));
		}catch(Exception e) {
			log.error("** calcCount Exception => " + e.toString());
		}
		
		
		return "redirect:home";
	}
	
	@GetMapping("/tlist")
	public String tlist() {
		List<TestKey> list = tservice.selectList();
		for(TestKey t  :list) {
			System.out.println(t);
		}
		
		return "redirect:home";
	}
	
	//=> SelectOne (복합키이기떄문에 사용함)
	//	쿼리스트링으로 Test : /tdetail?id=apple&no=1
	@GetMapping("/tdetail")
	public String tdetail(TestKeyId testid) {
		System.out.println("** tDetail => "+ tservice.selectOne(testid));
		
		return "redirect:home";
	} //tdetail
	
	
	// => Delete 
	//	쿼리스트링으로 Test : /tdelete?id=apple&no=1
	@GetMapping("/tdelete")
	public String tdelete(TestKeyId testid) {
		try {		
			
			if(tservice.selectOne(testid)!=null) {
				tservice.delete(testid);
				log.info("** tdelete 성공 => "+ testid);
			}else {
				throw new Exception("Data not Found");
			}
		}catch(Exception e) {
			log.error("Delete Exception=> "+e.toString());
		}
		
		return "redirect:home";
	}
	
	//*** JPA Start *********************************
	@GetMapping("/ginsert")
	public String ginsert() {
			GuestBook entity = GuestBook.builder()
								.title("Jpa Insert Test")
								.content("입력이 술술 잘되요 ~~")
								.writer("admin")
								.build();
			log.info("** guest insert => "+service.register(entity));
		return "redirect:home";
	}

	@GetMapping("/gupdate")
	public String gupdate() {
			GuestBook entity = GuestBook.builder()
								.gno(3L)
								.title("Jpa Update Test")
								.content("수정이 술술 잘되요 ~~")
								.writer("admin")
								.build();
			log.info("** guest insert => "+service.register(entity));
		return "redirect:home";
	}
	
	@GetMapping("/glist")
	public String glist() {
		// ver01 : Entity 이용 (GuestBook)
//		List<GuestBook> list =  service.selectList();
//		for(GuestBook g:list) {
//			System.out.println(g+", regdate = "+ g.getRegdate()+", modDate = "+g.getModdate());
//		}
		//ver02: GuestBookDTO
		List<GuestBookDTO> list2 =  service.selectList2();
		for(GuestBookDTO g:list2) {
			System.out.println(g+", regdate = "+ g.getRegdate()+", modDate = "+g.getModdate());
		}
		return "redirect:home";
	}
	
	
	@GetMapping("/gdelete")
	// => 쿼리스트링으로 Test : "/gdelete?gno=30
	public String gdelete(Long gno) {
		try {
			// => delete 하기전 자료 존재 확인 후 삭제
			// (~ServiceImpl 에서 할 수도 있음)
			
			if(service.selectOne(gno) != null) {				
				service.delete(gno); //하다가 오류가 있으면 exception
				log.info("** gdelete 성공 => "+ gno);
			}else {
				log.error("** gdelete 실패 => Data Not Found **");
				throw new Exception(" Data Not Found ");
			}
		}catch (Exception e){
			log.error("** gdelete Exception => "+ e.toString());
			// => 자료가 없는경우  : SpringBoot3 부터는 Exception 발생하지않음.
			//org.springframework.dao.EmptyResultDataAccessException 발생확인
		}
		return "redirect:home";
	}
	
	// ** JPA Pageing & Sort
    // => PageRequestDTO(페이징 조건들) -> PageResultDTO(최종결과)
    // => 사용객체들 : Page<Entity>, Pageable(i) -> PageRequest(구현체) 등.
	@GetMapping("/gpage")
	public String gpage(int pageNo) {
		//1)
		PageRequestDTO requestDTO = PageRequestDTO.builder()
									.page(pageNo)
									.size(5)
									.build();
		//2) Service
		// 요청을 보내면 작업한 결과를 return
		PageResultDTO<GuestBookDTO,GuestBook> resultDTO =  service.pageList(requestDTO);
		
		// 3) 결과
		System.out.println("** JPA Paging & Sort List => pageNo: "+ pageNo);
		for (GuestBookDTO g : resultDTO.getDtoList()) {
			System.out.println(g);
		}
		

		return "redirect:home";
	}
}
