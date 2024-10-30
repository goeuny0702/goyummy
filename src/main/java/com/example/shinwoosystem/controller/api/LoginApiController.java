package com.example.shinwoosystem.controller.api;

import com.example.shinwoosystem.models.dto.LoginForm;
import com.example.shinwoosystem.models.entity.Login;
import com.example.shinwoosystem.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class LoginApiController {

    @Autowired  //스프링 부트가 미리생성해 놓은 서비스 객체 주입.(DI(Depender Injection))
    private LoginService loginService;

    @GetMapping("/api/article")
    public List<Login> index(){
        return loginService.index();
    }

    @GetMapping("/api/article/{id}")
    public Login show(@PathVariable Long id){
        return loginService.show(id);
    }

    @PostMapping("/api/articles")
    public ResponseEntity<Login> create(@RequestBody LoginForm formDto) {
        Login createdArticle =  loginService.create(formDto);

        return createdArticle != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(createdArticle)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Login> update(
            @PathVariable Long id,
            @RequestBody LoginForm dto
    ) {
        Login updatedLogin = loginService.update(id, dto);

        return updatedLogin != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(updatedLogin)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

        @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Login> delete(@PathVariable Long id){
        Login deleted = loginService.delete(id);
        return (deleted != null)
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    @PostMapping("/api/transaction-test")   //트랜젝션 테스트용 api(여러 게시글 생성 요청 접수)
    public ResponseEntity<List<Login>> transactionTest(
        @RequestBody List<LoginForm> dtos) {  //트랜젝션테스트 메서드 정의
        List<Login> createdList = loginService.createLogins(dtos); //서비스 호출

        return createdList != null  //생성 결과에 따라 응답처리
                ? ResponseEntity.status(HttpStatus.OK).body(createdList)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

}