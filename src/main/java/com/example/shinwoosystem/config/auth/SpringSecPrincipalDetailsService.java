package com.example.shinwoosystem.config.auth;

import com.example.shinwoosystem.dao.repository.UserRepository;
import com.example.shinwoosystem.models.entity.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//시큐리티 설정에서 loginProcessingUrl("/login");
//login 요청이 오면 자동으로 UserDetailsService타입으로 loC되어 있는 loadUserByUsername 함수가 실행.
//암호 비교는 자동으로 해줌.
@Service
public class SpringSecPrincipalDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    // 시큐리티 session = Authentication = UserDetails
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Login loginEntity = userRepository.findByUsername(username);

        //없는 아이디를 입력했을 경우
        if(loginEntity == null){
            throw new UsernameNotFoundException("아이디가 존재하지 않습니다." + username);
        }

        return new SpringSecPrincipalDetails(loginEntity);
    }
}
