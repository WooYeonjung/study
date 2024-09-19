package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Jo;
import com.example.demo.service.JoService;
import com.example.demo.service.MemberService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Controller
@Log4j2
@RequestMapping(value="/jo")
public class JoController {

	JoService service;
	MemberService mservice;
	
	@GetMapping(value="/joList")
	public void joList(Model model) {
		model.addAttribute("banana",service.joinList());
	}
	
	@GetMapping(value="/joInsert")
	public void insertForm() {
		
	}
	
	@PostMapping(value="/insert")
	public String insert(Jo entity,RedirectAttributes rttr,Model model) {
		String uri = "redirect:/jo/joList";
		try {
			log.info("joInsert 성공 => "+service.save(entity) );
			rttr.addFlashAttribute("message", "조 등록 성공");
		}catch(Exception e) {
			log.error("joInsert 실패 => "+ e.toString());
			model.addAttribute("message","조 등록 실패, 다시 등록하세요");
			uri ="jo/joInsert";
		}
		return uri;
	}
	
	@GetMapping(value="/detail")
	public String joInfo(Model model,String jCode, Jo entity) {
		String uri = "jo/joDetail";
		model.addAttribute("apple",service.selectOne(entity.getJno()));
		if("U".equals(jCode)) 
			uri = "jo/joUpdate";
		if("D".equals(jCode)) 
			model.addAttribute("banana",mservice.findByJno(entity.getJno())); 
		
		/*
		 * else { //model.addAttribute("banana",mservice.selectOne(uri)); }
		 */
		return uri;
	}
	
	@PostMapping(value="/update")
	public String update(Jo entity, Model model,RedirectAttributes rttr) {
		model.addAttribute("apple",entity);
		String uri = "redirect:joList";
		
		try {
			log.info("jo Update 성공 => "+service.save(entity) );
			rttr.addFlashAttribute("message", entity.getJno()+"조 수정 성공");
		}catch(Exception e) {
			log.error("jo Update 실패 => "+ e.toString());
			rttr.addFlashAttribute("message", entity.getJno()+"조 수정 실패");
		}

		return uri;
	}
	
	@GetMapping(value="delete")
	public String delete(String jno,RedirectAttributes rttr) {
			String uri="redirect:joList";
			
		try {
			service.deleteById(Integer.parseInt(jno));
			log.info("jo Delete 성공");
			rttr.addFlashAttribute("message",jno+"조 삭제 성공");
		}catch(Exception e){
			log.error("jo Delete 실패"+e.toString());
			rttr.addFlashAttribute("message",jno+"조 삭제 실패");
		}
			
			

		return uri;
	}
}
