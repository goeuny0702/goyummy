package com.example.shinwoosystem.controller;

import com.example.shinwoosystem.service.AuthService;  // AuthService 임포트 추가
import com.example.shinwoosystem.dao.repository.LoginRepository;
import com.example.shinwoosystem.models.entity.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



import lombok.Getter;
import lombok.Setter;
@Controller
public class LoginController {

    @Autowired
    private AuthService authService;	
	
    @Autowired
    private LoginRepository loginRepository;	//회원정보에 대한 crud작업을 수행하는 레파지토리

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder; //비번을 안전하게 암호화하기 위한 도구

//    @GetMapping({"", "/"})
//    public String index() {
//        return "index";
//    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public String manager() {
        return "manager";
    }

    /**
     * 로그인 화면으로 이동
     * @return
     */
    
    @GetMapping("/main")	//url(인터넷 주소에 치는 유알엘.) 로그인화면을 보여주는 메서드
    public String main(
            @RequestParam(required = false) String message,	//로그인 과정에서 발생한 메시지를 받을 수 있도록 설정
            Model model	
    ) {
        model.addAttribute("message", message);	//로그인 화면에서 메시지를 표시하기 위해 모델에 message를 추가
        return "main";		
        // 메인페이지가 완료되면, 로그인창에서 로그인을 누르면 신우 메인페이지 템플릿을 반환.+ 매니저 롤 권한이있게 변환
        // return "redirect:/신우메인홈페이지유알엘." 또한 셋롤을 통해 유저가아닌 매니저 권한을 주자.
    }
    
    @GetMapping("/login")	//url(인터넷 주소에 치는 유알엘.) 로그인화면을 보여주는 메서드
    public String login(
            @RequestParam(required = false) String message,	//로그인 과정에서 발생한 메시지를 받을 수 있도록 설정
            Model model	
    ) {
        model.addAttribute("message", message);	//로그인 화면에서 메시지를 표시하기 위해 모델에 message를 추가
        return "login";		
        // 메인페이지가 완료되면, 로그인창에서 로그인을 누르면 신우 메인페이지 템플릿을 반환.+ 매니저 롤 권한이있게 변환
        // return "redirect:/신우메인홈페이지유알엘." 또한 셋롤을 통해 유저가아닌 매니저 권한을 주자.
    }

        
    // 상기 주석대로 로그인 인증 후 유저네임과 비번이 같으면 메인페이지로 리다이렉트하는 코드.
    @PostMapping("/loginprocess")
    public String loginprocess(
        @RequestParam String username, 
        @RequestParam String password, 
        Model model) {

       
		// 로그인 인증 로직 (예시)
        boolean isAuthenticated = authService.authenticate(username, password);
        
        if (isAuthenticated) {
            // 인증 성공 시 메인 페이지로 리다이렉트
            return "redirect:/main";	//혹은 특정 게시판 페이지
        } else {
            // 인증 실패 시 다시 로그인 페이지로
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }


    /**
     * 회원가입 화면으로 이동
     * @return
     */
    @GetMapping("/sign_up")	//상기 로그인과 동일. 회원가입 화면을 보여주는 메서드
    public String sign_up() {
        return "sign_up";
    }

    /**
     * 회원가입 처리
     * @return
     */
    @PostMapping("/sign_up")     // return의 string의 값은 json형태로 받겠다. post 요청.
    public String sign_up(Login login, BindingResult bindingResult, Model model) {	//역할은 회원가입 정보를 처리하고 저장하는 메서드.
        // 유저네임이 null인지 검사
    	System.out.print("안녕 "+login);
    	
        if (login.getUsername() == null || login.getUsername().isEmpty()) {
            model.addAttribute("error", "유저네임을 입력해주세요.");
            return "sign_up"; // 유저네임이 null일 경우 회원가입 페이지로 돌아감
        }

        // 유저네임 중복 검사
        if (loginRepository.findByUsername(login.getUsername()) != null) {
            model.addAttribute("error", "이미 사용 중인 유저네임입니다.");
            return "sign_up"; // 중복일 경우 회원가입 페이지로 돌아감
        }
        // 역할 설정
        login.setRole("ROLE_USER");	//사용자에게 기본역할을 설정,
        String rawPassword = login.getPassword();	//입력한 비번을 암호화
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);	//암호화된 비번으로 login 객체를 업뎃,
        login.setPassword(encPassword);

        loginRepository.save(login); // 회원정보를 디비에 저장

        return "redirect:/login";	//가입이 완료된 후 로그인 페이지로 리디렉션.
    }
    
    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    @ResponseBody
    public String info() {
        return "개인정보";
    }

    //@PreAuthorize : 메서드가 실행되기 전에 인증을 거친다. @PostAuthorize : 메서드가 실행되고 나서 응답을 보내기 전에 인증을 거친다. 반대는 postAu~
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")   //복수를 걸고 싶을땐 has Role. 문법임.
    @GetMapping("/data")
    @ResponseBody
    public String data() {
        return "데이터정보";
    }

}
