package com.kwan.book.springboot.config.auth.dto;

import com.kwan.book.springboot.web.domain.user.Role;
import com.kwan.book.springboot.web.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.jar.Attributes;

@Getter
public class OAuthAttributes {
    private Map<String, Object> authAttributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> authAttributes, String nameAttributeKey, String name, String email, String picture) {
        this.authAttributes = authAttributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> authAttributes) {
        if("naver".equals(registrationId)){
            return ofNaver("id", authAttributes);
        }

        return ofGoogle(userNameAttributeName, authAttributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> authAttributes) {
        return OAuthAttributes.builder().name((String) authAttributes.get("name")).email((String) authAttributes.get("email"))
                .picture((String) authAttributes.get("picture")).authAttributes(authAttributes).nameAttributeKey(userNameAttributeName).build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> authAttributes) {
        Map<String, Object> response = (Map<String, Object>)authAttributes.get("response");

        return OAuthAttributes.builder().name((String) response.get("name")).email((String) response.get("email"))
                .picture((String) response.get("profileImage")).authAttributes(response).nameAttributeKey(userNameAttributeName).build();
    }


    public User toEntity() {
        return User.builder().name(name).email(email).picture(picture)
                .role(Role.GUEST)
                .build();

    }

}
