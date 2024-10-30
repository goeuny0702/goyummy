package com.example.shinwoosystem.config.oauth.provider;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo {
    private Map<String, Object> attrs;

    public NaverUserInfo(Map<String, Object> attrs){
        this.attrs = attrs;
    }

    @Override
    public String getProviderId() {
        return (String)attrs.get("id");
        // ("sub")는 키값이라고 함.
    }

    @Override
    public String getProvider() {
        return "naver";
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
