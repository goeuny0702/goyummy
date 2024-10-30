package com.example.shinwoosystem.models.dto;

import lombok.extern.slf4j.Slf4j;
import lombok.AllArgsConstructor;
import com.example.shinwoosystem.models.entity.Login;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

public class LoginForm {
    private Long id;
    private String username;
    private String password;   // 제목을 받을 필드(속성)
    private String email; // 내용을 받을 필드(속성)
    private String role;
    private String provider;    // 추가
    private String providerId;	//oauth2로그인을 사용하는 경우에는 제공자와 제공자아이디정보가 필요함. sns계정으로 로그인할땐 필요하다고. 
    //로그인할때 직접입력할필요는 없지만 로그인 처리가 된 후엔 디비에 저장을해야하기때문에 이 정보를 포함해야 함. 알겠냐??

    public Login toEntity() {
        return new Login(id, username, password, email, role, provider, providerId);
    }
}

