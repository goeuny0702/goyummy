package com.example.shinwoosystem.models.entity;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
//자카르타에 맞는 현재버전의 jpa를 임포트해야한다. persistence, javax가 아닌. 그러면 15번 라인의 @Entity의 오류가 사라질 것이다.
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@ToString
public class Login {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username; 
	private String password; 
	private String email;
	private String role;
	
	private String provider;	//구글인지 네이버인지 카카오인지 구별을 하기 위한 제공자 아이디= 필드
	private String providerId; //각각의 provider사의 id. google -> sub, naver ->? kakao ->?
		// TODO Auto-generated constructor stub
	public void patch(Login login) {
	    if (!Objects.isNull(login)) {
	        if (login.username != null) {
	            this.username = login.username;
	        }
	        if (login.password != null) {
	            this.password = login.password;
	        }
	        if (login.email != null) {
	            this.email = login.email;
	        }
	        if (login.role != null) {
	            this.role = login.role;
	        }
	    }
	}
}

