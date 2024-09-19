package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.JoDTO;
import com.example.demo.entity.Member;
import com.example.demo.service.JoService;
import com.example.demo.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor // 모든 컬럼 생성자 주입됨, 개별적인 @Autowired 생략가능
@Controller
@Log4j2
@RequestMapping(value = "/member")
public class MemberController {

	MemberService service; // 주입을 하려면 만들어나놔야함.
	PasswordEncoder passwordEncoder;
	JoService jservice;

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// ** 다양한 JPA 쿼리 방법 Test
	// => JoController, MemberController
	
	// **QueryDSL
	// =>  List<Member> findMemberJoinDSL()
	// => console 로 결과 확인
	@GetMapping("/joindsl")
	public String joinDSL() {
		List<JoDTO> list = service.joinDSL();
		for(JoDTO m :list) {
			System.out.println(m);
		}
		return "redirect:/home";
	}//joinDSL
	
	
	
	// ** Password 수정 하기
	@GetMapping("/pwUpdate")
	public void pwUpdate() {
	}

	@PostMapping("/pwUpdate")
	public String pwUpdate(HttpSession session, Member entity, Model model) {
		// 1) 요청 분선
		// => id : session 에서
		// => new password : 암호화
		String url = "member/loginForm";
		entity.setId((String) session.getAttribute("loginID"));
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));

		// 2) Service
		try {
			service.updatePassword(entity.getId(),entity.getPassword()); 
			// =>id,암호화 된 pw 인자 전송
			log.info("Password Update 대~~~성공! => " + entity.getId());
			session.invalidate();
			model.addAttribute("message","Password 수정 성공, 재로그인 하세요.");
		} catch (Exception e) {
			// 실패 : 재수정 유도
			log.error("Password Update Exception => " + e.toString());
			url = "member/pwUpdate";
			model.addAttribute("message","Password 수정 실패. 다시 시도하세요.");
		}
		return url;
	}// pwUpdate

	// ** ID 중복하기
	@GetMapping("/idDupCheck")
	public void idDupCheck(String id, Model model) {
		// ** newID 존재여부 확인 & 결과전달
		if (service.selectOne(id) != null) {
			// => 사용 불가능
			model.addAttribute("idUse", "F");

		} else {
			// => 사용가능
			model.addAttribute("idUse", "T");
		}
	}// idDupCheck

	@GetMapping("/memberList")
	public void mList(Model model) {
		model.addAttribute("slist", service.selectList());

	}

	// 로그인 폼
	@GetMapping("/loginForm")
	public void loginForm() {
		System.out.println("로그인 폼");
	}

	// 로그인
	@PostMapping("/login")
	public String login(HttpSession session, Model model, Member entity) {
//		String id = request.getParameter("id");
//		String password = request.getParameter("password");
		String password = entity.getPassword(); // password 확인위해 보관 필요함.

		entity = service.selectOne(entity.getId());
		String uri = "redirect:/home";
		if (entity != null && passwordEncoder.matches(password, entity.getPassword())) {
			session.setAttribute("loginID", entity.getId());
			session.setAttribute("loginName", entity.getName());
			session.setAttribute("loginInfo", entity.getInfo());

		} else {
			uri = "member/loginForm";
			model.addAttribute("message", "id 또는 password 오류");
		}
		return uri;

	}// login

	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/home";
	}

	// ** @RequestParam("...") 사용 시 주의사항
	// => 해당하는 parameter 가 없으면 400 오류
	// -> 오류 방지를 위해서 일반 detail 요청시에도 파라미터 주기. ?jCode=D 를 추가함
	@GetMapping("/memberDetail")
	public String myinfo(HttpSession session, Model model, String jCode) {
		String uri = "member/memberDetail";
		model.addAttribute("detail", service.selectOne((String) session.getAttribute("loginID")));

		if ("U".equals(jCode)) {
			uri = "member/updateForm";
			model.addAttribute("joList", jservice.selectAll());
		}
		return uri;
	}

	// ** MemberJoin 회원가입
	@GetMapping(value = "/joinForm")
	public void joinForm(Model model) {
		// => joList에서 조목록 가져와 joinFomr의 select 에 적용
		model.addAttribute("joList", jservice.selectAll());
	}// joinForm

	// => Join
	// - 성공 : loginForm
	// - 실패: joinForm (재가입유도)
	@PostMapping(value = "/mjoin")
	public String mjoin(Member entity, Model model, HttpServletRequest request) throws IOException {
		String uri = "member/loginForm"; // String 으로 이동할때는 views/ 이후 폴더를 적어야함.

		// 1) passwordEncoder 적용
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));

		// 2) Upload File 처리 ***********************************
		// 2.1) 물리적 실제 위치 확인
		// => 개발환경 or 배포환경 여부에 따라 물리적 저장 위치가 달라짐
		// => 확인
//		String realPath = request.getRealPath("/");  // deprecated
		String realPath = request.getServletContext().getRealPath("/");
		System.out.println("** realPath => " + realPath);
		// => C:\Users\Ellie\Desktop\coding_study\MTest\myWork\demo02\src\main\webapp\
		// => 실제 저장위히
		// "C:\\Users\\Ellie\\Desktop\\coding_study\\MTest\myWork\\demo03\\src\\main\\webapp\\resources\\uploadImages"

		realPath += "resources\\\\uploadImages\\";

		// 2.2) realPath 존재확인 및 생성
		File file = new File(realPath); // String 을 File 타입으로 바꿔준 그럼 file 은 경로로 인식
		if (!file.exists())
			file.mkdir(); // 저장 폴더가 존재하지 않으면 만들어줌

		// 2.3) basicman.png Copy하기 (IO Stream 실습)
		// => 기본Image(basicman.png)가 uploadImages 폴더에 없는 경우
		// images 폴더에서 가져오기(Copy)
		// => IO 발생: Checked Exception
		file = new File(realPath + "basicman.png");
		if (!file.exists()) {
			String basicImagePath = "C:\\Users\\Ellie\\Desktop\\coding_study\\MTest\\myWork\\demo\\src\\main\\webapp\\resources\\images\\basicman4.png";

			FileInputStream fin = new FileInputStream(new File(basicImagePath));// 원본데이터 읽어들여옴
			// => basicImagePath을 읽어서 파일입력 바이트스트림 생성

			FileOutputStream fout = new FileOutputStream(file);
			// => 목적지(realPath+"basicman.png") 파일출력 바이트스트림 생성

			FileCopyUtils.copy(fin, fout);
		}

		// 2.4) 저장경로 완성하기
		// => 물리적 저장위치 : file1
		// => table 저장값_파일명 : file2
		String file1 = "", file2 = "basicman.png";

		MultipartFile uploadfilef = entity.getUploadfilef();

		// => 업로드 파일 선택여부 확인
		if (uploadfilef != null && !uploadfilef.isEmpty()) { // 풀수 확인
			// => imageFile 선택 -> 저장
			file1 = realPath + uploadfilef.getOriginalFilename(); // 저장경로(realPath+파일명) 완성

			uploadfilef.transferTo(new File(file1)); // 해당하는 경로(file1)에 저장(붙여넣기)

			// => Table 저장값 완성
			file2 = uploadfilef.getOriginalFilename();
		}

		entity.setUploadfile(file2);

		// 3) Service 처리
		try {
			log.info("** Member Insert 성공! => " + service.save(entity));
			model.addAttribute("message", "회원가입 성공, 로그인 후 이용하세요");
		} catch (Exception e) {
			log.error("** Member Insert 성공! => " + e.toString());
			model.addAttribute("message", "회원가입 실패!");
			uri = "member/joinForm";
		}

		return uri;
	}

	// => Update
	// - 성공 : memberDetail
	// - 실패: updateForm (재수정유도)
	// 수정 내용을 전달하여 attribute 에 저장해놓음
	@PostMapping(value = "/mupdate")
	public String mupdate(Member entity, Model model, HttpSession session, HttpServletRequest request)
			throws IOException {

		// => 출력을 위해 전달된 내정보 보관
		model.addAttribute("detail", entity);
		String uri = "member/memberDetail";

		// ** Upload Image 처리
		// => newImage 선택여부확인
		// =>선택 -> oldImage 삭제, newImage 저장: uploadfilef
		// => 선택하지않음 -> oldImage 가 entity의 uploadfile 로 전달되어있으므로 그냥 사용
		MultipartFile uploadfilef = entity.getUploadfilef();
		if (uploadfilef != null && !uploadfilef.isEmpty()) {
			// 1) 물리적 위치에 저장
			String realPath = request.getServletContext().getRealPath("/");
			realPath += "resources\\uploadImages\\";

			// 2)oldImage 삭제
			// => oldImage 가 기본 Image(basicman.png) 가 아닌 경우에만 삭제
			// => oldImage File 명 : dto.getUploadfile()
			// => 삭제경로 : realPath + dto.getUploadfile()
			// => dto.getUploadfile()
//			if(dto.getUploadfile().equals("basicman.png"))
			if (!"basicman.png".equals(entity.getUploadfile())) {
				File delFile = new File(realPath + entity.getUploadfile()); // 파일타입으로 변경!
				if (delFile.isFile()) {
					// 이런 파일 있니? 있으면 지우자
					delFile.delete();
				}
			}

			// 3) newImage 물리적 저장
			realPath += uploadfilef.getOriginalFilename(); // 저장경로 완성
			uploadfilef.transferTo(new File(realPath));
			// realPath 이경로의 파일에 uploadfilef 내용을 그대로 전달

			// 4) Table 의 저장값 수정
			entity.setUploadfile(uploadfilef.getOriginalFilename());
		}

		// => service처리
		try {
			log.info("** Member Update 성공 => " + service.save(entity));
			session.setAttribute("loginName", entity.getName());
			model.addAttribute("message", "정보수정 성공!");
		} catch (Exception e) {
			log.error("** Member Update 실패! => " + e.toString());
			model.addAttribute("message", "정보수정 실패!");
			uri = "member/updateForm";
		}

		return uri;
	}

	@GetMapping(value = "/delete")
	public String delete2(RedirectAttributes rttr, HttpSession session) {
		// RedirectAttributes => redirect를 하더라도 원하는 메세지를 출력할 수 있음
		// 1) 요청분석
		String uri = "redirect:/home";
		String id = (String) (session.getAttribute("loginID"));

		// 2) Service & 결과

		try {
			service.deleteById(id);
			log.info("** Member Delete 성공 => " + id);
			rttr.addFlashAttribute("message", "탈퇴 성공"); // redirect 일때는 볼 수 없음.
			session.invalidate();
		} catch (Exception e) {
			log.error("** Member Delete 실패! => " + e.toString());
			rttr.addFlashAttribute("message", "탈퇴 실패!");
			uri = "member/updateForm";
		}
		return uri;
	}// delete

}
