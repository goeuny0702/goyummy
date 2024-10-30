package com.example.shinwoosystem.config.oauth.provider;

public interface OAuth2UserInfo {
    //로그인할떄 필요한 공통화되는 기본 정보들. 이것들을 본 인터페이스에서 총괄, 구현한다.
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
}
