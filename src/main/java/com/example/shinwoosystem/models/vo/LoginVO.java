package com.example.shinwoosystem.models.vo;

import com.example.shinwoosystem.models.entity.Login;

import lombok.extern.slf4j.Slf4j;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginVO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;
    
    public Login toEntity() {
        return new Login();
    }
}