package com.example.shinwoosystem.dao.repository;

import com.example.shinwoosystem.models.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

// 사용자의 기본정보를 저장하는 역할. 이름이메일비번등을. findByUsername, findByEmail 같은 메서드를 통해 사용자 조회가능.
//로그인 레파지토리는 로그인관련 정보ㅡㄹ 관리. 로그인시도기록이나 실패횟수, 마지막 로그인 시간등을 조정할 수 있다.
public interface UserRepository extends JpaRepository<Login, Long> {
    //findBy까진 규칙 username은 문법
    // select * from user where username= 1? . 물음표엔 함수, 즉 유저네임이 들어오겠다.
    Login findByUsername(String username);   //jpa 쿼리 메소드
    // select* from user where email = ?
//    public User findByEmail();
}
