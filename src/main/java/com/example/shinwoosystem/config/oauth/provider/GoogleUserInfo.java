package com.example.shinwoosystem.config.oauth.provider;

import java.util.Map;

import java.util.Map;

//구글 유저 회원 프로필 정보. 모델에 가까움
public class GoogleUserInfo implements OAuth2UserInfo {
    private Map<String, Object> attrs;

    public GoogleUserInfo(Map<String, Object> attrs){
        this.attrs = attrs;
    }

    @Override
    public String getProviderId() {
        return (String)attrs.get("sub");
            // ("sub")는 키값이라고 함.
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getEmail() {
        return (String)attrs.get("email");
    }

    @Override
    public String getName() {
        return (String)attrs.get("name");
    }
}
