package com.example.shinwoosystem.config.oauth;


import com.example.shinwoosystem.config.auth.SpringSecPrincipalDetails;
import com.example.shinwoosystem.config.oauth.provider.GoogleUserInfo;
import com.example.shinwoosystem.config.oauth.provider.KakaoUserInfo;
import com.example.shinwoosystem.config.oauth.provider.NaverUserInfo;
import com.example.shinwoosystem.config.oauth.provider.OAuth2UserInfo;
import com.example.shinwoosystem.dao.repository.UserRepository;
import com.example.shinwoosystem.dao.repository.LoginRepository;
import com.example.shinwoosystem.models.entity.Login;
//import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

//OAuth는 사용자의 ID 인증 데이터를 해당 애플리케이션과 공유하지 않고 여러 애플리케이션에 걸쳐
// 사용자 권한 부여를 확대하기 위한 프로토콜입니다.

@Service
public class SpringSecOAuth2PrincipalDetailsService extends DefaultOAuth2UserService{	//OAuth2는 OAuth의 후속버전. 더 많은 기능을 포함. 사용자의 세부정보를 로드하고
//매핑하는데 중요한 역할을 수행하는 클래스. 이를 통해 오오스2인증을 통해 애플리케이션에 로그인하는 사용자를 관리하고, 적절한 권한을 부여할 수 있음.


    @Autowired //유저레파티토리의 의존성을 주입.
    private UserRepository userRepository;	//일반 유저 정보를 처리하는 레파지토리임으로 login이 아닌 user가 맞음.

    //구글로부터 받은 userRequest 데이터에 대한 후처리하는 함수
    @Override //로드유저 메서드를 오버라이드.
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);	//OAuth2User는 일반 유저정보를 나타냄. login이 아니고.

        //이하 세 줄은 디버깅용 로그.
        System.out.println("getClientRegistration: " + userRequest.getClientRegistration());
        System.out.println("getAccessToken: " + userRequest.getAccessToken().getTokenValue());
        System.out.println("getAttributes: " + super.loadUser(userRequest).getAttributes());

        // 회원가입을 진행. 사용자 속성이 null인지 체크.
        OAuth2UserInfo oAuth2UserInfo = null;	//사용자 정보를 저장할 변수.

        String providerName = userRequest.getClientRegistration().getRegistrationId();
        
        if (providerName.equals("google")){
            //구글 로그인응답 정보 저장
            System.out.println("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());

        } else if (providerName.equals("naver")){
        	
            //네이버 로그인 성공 응답 정보 저장
            System.out.println("네이버 로그인 요청");
            //구글 로그인할땐 googleUserInfo.java의 정보를 저장, 네이버라면 네이버유저인포 저장 한다는 뜻.
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        } else if (providerName.equals("kakao")) {
            System.out.println("카카오 로그인 요청");
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        } else {
            System.out.println("우리는 구글과 네이버, 카카오 sns로그인만 지원해요~");
            return null;
        }

        String provider = oAuth2UserInfo.getProvider();  //구글인지 네이버인지 카카오인지 알 수 있게 해주는 제공자의 이름.
        String providerId = oAuth2UserInfo.getProviderId(); //제공자 아이디
        String username = provider + "_" + providerId; //사용자이름 생성.
        String password = "-";	//사용자 비번
        String email = oAuth2UserInfo.getEmail(); //이메일 가져오기
        String name = oAuth2UserInfo.getName(); 	//이름도
        String role = "ROLE_USER";	//기본 사용자 역할 설정. user를 제공함.

        
        //디비에서 사용자를 찾기.
        Login userEntity = userRepository.findByUsername(username);	//유저네임으로 로그인 엔티티 찾기.
        if (Objects.isNull(userEntity)){	//사용자가 존재하지 않으면
            userEntity = new Login();	//새로운 로그인 객체 생성
            userEntity.setUsername(username);	//사용자 이름도 설정
            userEntity.setPassword(password);	//패스워드도 설정
            userEntity.setEmail(email);			//이메일도 설정
            userEntity.setProvider(provider);	//제공자도
            userEntity.setProviderId(providerId);	//제공자 아이디도 ㅋㅋ
            userEntity.setRole(role);			//역할도

            userRepository.save(userEntity);		//새로운 사용자 정보를 이제 디비에 저장.
        }
        //SpringSecPrincipalDetails를 반환하여 인증된 사용자 정보와 속성을 반환한다.
        return new SpringSecPrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
}
