package com.example.shinwoosystem.service;

import com.example.shinwoosystem.dao.repository.LoginRepository;
import com.example.shinwoosystem.models.entity.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.Getter;
import lombok.Setter;

@Service
public class AuthService {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean authenticate(String username, String password) {
        // 사용자 정보를 DB에서 조회
        Login login = loginRepository.findByUsername(username);
        if (login == null) {
            return false; // 사용자 없으면 인증 실패
        }

        // 비밀번호 비교
        return bCryptPasswordEncoder.matches(password, login.getPassword());
    }
}
