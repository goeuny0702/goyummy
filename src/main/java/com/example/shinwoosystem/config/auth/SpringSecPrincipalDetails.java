package com.example.shinwoosystem.config.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.example.shinwoosystem.models.entity.Login;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class SpringSecPrincipalDetails implements UserDetails, OAuth2User {
	// 스프링 시큐리티와 오오스2를 사용하는 사용자인증 및 권한 관리를 위한 사용자정의 userdetails와 oauth2user 구현.
//implements UserDetails를 치고 alt enter를 치고 create method를 선택하면 하단의 일곱가지의 메소드가 자동으로 생성.
    private Login login;  //해당 프로그램에서 정의한 User Entity. 우리 디비에 저장되있는 유저.
    private Map<String, Object> loginAttrs;

    public SpringSecPrincipalDetails(Login login) {
        this.login = login;
    }

    public SpringSecPrincipalDetails(Login login, Map<String, Object> loginAttrs) {
        this.login = login;
        this.loginAttrs = loginAttrs;
    }



    @Override
    public Map<String, Object> getAttributes() {
        return loginAttrs;
    }

    //해당 User의 권한을 리턴하는 곳. admin인지 manager인지 user인지 구분해주는 곳.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return login.getRole();
            }
        });
        // ex) admin or manager or user
        // admin or user
        // manager or user
        return collection;
    }

    @Override
    public String getPassword() {
        return login.getPassword();
    }

    @Override
    public String getUsername() {
        return login.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override   // 잠김 여부
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override   //인증이 만료가 되었는지 아닌지
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override   //활성화여부 = 휴먼계정
    //1년 이상 미접속이라면?
    // 현재시간 - 가입시간 <1년이라면 잠금 으로.
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
