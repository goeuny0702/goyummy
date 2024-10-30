package com.example.shinwoosystem.config.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        // 커스텀 파라미터 추가
        String errorMessage = URLEncoder.encode("아이디 혹은 비밀번호가 잘못되었습니다.", StandardCharsets.UTF_8.toString());
//        String errorMessage = "비밀번호가 잘못되었습니다.";
//        String errorMessage = "password wrong";

        // 로그인 페이지로 리다이렉트하면서 파라미터 추가
        String redirectUrl = "/login?error=true&message=" + errorMessage;
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
