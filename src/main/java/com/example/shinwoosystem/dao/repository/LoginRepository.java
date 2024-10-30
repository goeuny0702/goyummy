package com.example.shinwoosystem.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.shinwoosystem.models.entity.Login;
//로그인 레파지토리는 로그인관련 정보를 관리. 로그인시도기록이나 실패횟수, 마지막 로그인 시간등을 조정할 수 있다.
public interface LoginRepository extends JpaRepository<Login, Long>{
    Login findByUsername(String username);
}

