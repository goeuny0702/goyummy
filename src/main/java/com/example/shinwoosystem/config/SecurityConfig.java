package com.example.shinwoosystem.config;

//1.코드받기(인증) 2. 액세스토큰(권한받기) 3. 사용자프로필 정보를 가져오고 4-1. 그정보를 토대로 회원가입을
//자동으로 진행시키기도 함. 4-2 기본정보 (이메일,전번,이름,아이디)만 필요하다면 자동가입. 이외의 것이 필요하면
//정보를 받고

import com.example.shinwoosystem.config.handler.CustomAuthenticationFailureHandler;// 인증 실패했을때 동작을 커스터마이징 가능.
import com.example.shinwoosystem.config.oauth.SpringSecOAuth2PrincipalDetailsService; // 로그인 기능을 구현할 때, 세부정보를 로드하고 매핑하는 클래스. 여기 인증을 통해 앱에 로그인하는 사용자를 관리할 수 있음.
import org.springframework.beans.factory.annotation.Autowired;	// spring의 의존성주입 기능을 제공하는 어노테이션. 이걸 필드나 생성자에 사용하면 스프링이 자동으로 bean을 주입.
import org.springframework.context.annotation.Bean; //메서드에 붙여서 bean을 생성하고 관리하는 기능.
import org.springframework.context.annotation.Configuration; //해당 클래스가 스프링의 설정 클래스를 의미. bean정의를 포함할 수 있는 클래스임을 나타내는 어노테이션.
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; //메서드 수준의 보안을 활성화하는 어노테이션. 특정 메서드에 접근제어를 적용할 수 있음. 권한에따라 호출제한가능. 매니저나 어드민이나 유저등을 제한할 수 있음.
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // http 요청에 대한 보안을 설정하는데 사용되는 객체.
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // 스프링시큐리티 웹보안기능이 활성화.
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer; // httpSecurity의 구성설정을 위한 추상클래스. 이것으로 보안구성을 커스터마이징할 수 있음. 특정기능을 활/비활성화할수 있음.
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // 비번을 암호화하는데 사용되는 스프링시큐리티의 클래스. BCrypt해시 알고리즘을 사용해서 비번을 안전히 저장.
import org.springframework.security.web.SecurityFilterChain; // 스프링 시큐리티에서 요청을 필터링하고 보안을 적용하는 체인. 체인이니, 요청이 들어오면 각 필터를 차례대로 실행하여 요청을 처리.
import org.springframework.security.web.authentication.AuthenticationFailureHandler; // 실패시 호출되는 핸들러. 어센틱페일러는 기본. 최상단의 커스텀은 이 인터페이스를 사용해서 특정동작 구현가능.

@Configuration      //Spring bean에서 사용할 수 있도록 세팅
@EnableWebSecurity  //Spring Security를 활성화 하도록 세팅
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

 @Autowired
 SpringSecOAuth2PrincipalDetailsService springSecOAuth2PrincipalDetailsService;
 @Bean
 BCryptPasswordEncoder encodePwd() {
     return new BCryptPasswordEncoder();
 }
 @Bean
 SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
     http
             .csrf(AbstractHttpConfigurer::disable)
//             .csrf((csrf)) -> csrf.disable()) 위의 코드와 같음.
             .authorizeHttpRequests((authorizeHttpRequests)
                     -> authorizeHttpRequests.
                     requestMatchers("/main").permitAll()	//누구나 메인페이지 접근 허용
                     .requestMatchers("/user/**").authenticated()//유저 역할이 필요한 페이지.
                     .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")  //매니저, 어드민권한이 있어야만 들어올 수 있다.
                     .requestMatchers("/admin/**").hasRole("ADMIN")  //어드민 권한이 있어야만 들어올 수 있다.
                     .anyRequest().permitAll()   //다른것들은 통과해도 좋다.
                     //구글 로그인이 완료된 후의 후처리가 필요함. 코드x 액세스토큰+사용자프로필정보 0 따라서 oauth가 매우 편리.
             ).formLogin(formLogin ->
                             formLogin.loginPage("/login") //인증이 되지 않는것들은 본 쿼리, 로그인 페이지로 이동하게 한다. admin, manager로 들어와도 로그인으로 들어와진다.
                                     .loginProcessingUrl("/loginprocess")
                                     .defaultSuccessUrl("/main")	//로그인 성공시 /main으로 이동. 아님?????????
//                                       .failureUrl("login-failure")
                                     .failureHandler(customAuthenticationFailureHandler())
             )
//             invalidateHttpSession은 로그아웃 시 sesson을 초기화
             .logout(logout -> logout.logoutSuccessUrl("/loginsucess").invalidateHttpSession(true))
             .oauth2Login(oauth2 ->
                     oauth2.loginPage("/login")
                             .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(springSecOAuth2PrincipalDetailsService))
                             .defaultSuccessUrl("/main"))  //oauth2 인증시 필요
     ;

     return http.build();
 }

 @Bean
 public AuthenticationFailureHandler customAuthenticationFailureHandler() {
     return new CustomAuthenticationFailureHandler();

 }
}
