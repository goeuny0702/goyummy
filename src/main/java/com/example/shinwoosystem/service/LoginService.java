package com.example.shinwoosystem.service;

import com.example.shinwoosystem.models.dto.LoginForm;
import com.example.shinwoosystem.models.entity.Login;
import com.example.shinwoosystem.dao.repository.LoginRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

@Service    //서비스 객체 생성
@Slf4j
public class LoginService {
    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // 로그인 인증 메서드 추가 (새롭게 추가되는 부분)
    public boolean authenticate(String username, String password) {
        // 사용자 이름으로 DB에서 Login 엔티티 검색
        Login login = loginRepository.findByUsername(username);

        if (login == null) {
            log.warn("사용자를 찾을 수 없습니다: " + username);
            return false;
        }

        // 입력한 비밀번호와 저장된 암호화된 비밀번호 비교
        boolean isPasswordMatch = bCryptPasswordEncoder.matches(password, login.getPassword());

        if (!isPasswordMatch) {
            log.warn("비밀번호가 일치하지 않습니다.");
            return false;
        }

        // 인증 성공
        return true;
    }  
    
    
    public List<Login> index(){
        return loginRepository.findAll();
    }

    public Login show(Long id) {
        return loginRepository.findById(id).orElse(null);
    }

    public Login create(LoginForm formDto){
        Login login = formDto.toEntity();
//        List<Login> createLogins(List<LoginForm> dtos); // 추가된 메서드
        if (login != null && login.getId() != null){
            return null;
        }

        return loginRepository.save(login);
    }

    public Login update(Long id, LoginForm dto){
                //1. dto(ArticleForm)를 엔티티(Article)로 변환
        Login loginEntity = dto.toEntity();
        log.info("id: {}, article: {}", id, loginEntity.toString());

        //2. 타깃 조회하기
        Login target = loginRepository.findById(id).orElse(null);

        //3. 타깃의 값을 체크해서 잘못된 요청 처리하기
        if (target == null || id != loginEntity.getId()){
            return null;
        }

        //4. 타깃의 값을 실제 변경하기
        target.patch(loginEntity);
        Login updated = loginRepository.save((target));

        return updated;
    }

    public Login delete(Long id){
        Login target = loginRepository.findById(id).orElse(null);
        if (target == null){
            return null;
        }
        loginRepository.delete(target);
        return target;
    }

    public List<Login> createArticles(List<LoginForm> dtos) {
        // 1.Dtos를 엔티티 묶음으로 변환하기
        List<Login> articleList
            = dtos.stream().map(dto -> dto.toEntity()).collect(Collectors.toList());
        //스트림이란 리스트와 같은 자료구조에 저장된 요소를 하나씩 순회하면서 처리하는 코드패턴.

//        List<Article> articleList2 = new ArrayList<>();
//        for(ArticleForm dto : dtos) {
//            articleList2.add(dto.toEntity());
//        } 윗줄의 쿼리와 동일한 의미. 그러나 첫번째것이 더 간결하므로 윗것을 애용.

        // 2. 엔티티 묶음을 db에 저장하기
        articleList.stream()    //아티클리스트를 스트림화
                .forEach(article -> loginRepository.save(article));

        // 3. 강제 예외 발생시키기
        loginRepository.findById(-1L)
                .orElseThrow(() -> new IllegalArgumentException("결제실패"));

        // 4. 결과값을 반환하기
        return articleList;
    }

	public List<Login> createLogins(List<LoginForm> dtos) {
// 로그인폼 리스트를 사용하여 여러개의 Login엔티티를 생성하고 저장하는 기능을 구현해야함. 그리고 각각의 로그인폼을 로그인엔티티로 변환, 저장, 엔티티리스트를 반환해야함.
	    return dtos.stream()
	            .map(dto -> {
	                // dto를 Login 엔티티로 변환
	                Login login = dto.toEntity();
	                // login을 데이터베이스에 저장하고 반환
	                return loginRepository.save(login);
	            })
	            .collect(Collectors.toList());
	}
}
