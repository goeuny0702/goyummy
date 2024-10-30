//package com.example.demo.controller;
//
//import com.example.demo.dao.repository.LoginRepository;
//import com.example.demo.models.entity.Login;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.annotation.Secured;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import lombok.Getter;
//import lombok.Setter;
//
//@Controller
//public class sign_upController {
//
//	@Autowired
//	private LoginRepository loginRepository;
//	
//@Autowired
//private BCryptPasswordEncoder bCryptPasswordEncoder; //bcrypt는 Blowfish Crypt의 준말. 해쉬함수임.
//
////회원가입 화면으로 이동.
////	@return
//	@GetMapping("/sign_up")
//	public String signUpForm(Model model) {
//		return "sign_up";
//	}
//	//회원가입 처리
//	
//	@PostMapping("/sign_up")
//	public String signUpForm(Login login) {
//		//회원가입 정보 처리
//		login.setRole("ROLE_USER");	//기본역할 설정
//		String rawPassword = login.getPassword();
//		String encPassword = bCryptPasswordEncoder.encode(rawPassword);	//비번 암호화
//		login.setPassword(encPassword);	// 암호화된 비번 저장.
//		
//		loginRepository.save(login);	//회워정보 디비에 저장
//		
//		return "redirect:/login";
//	}
//}
