package com.multi.module.service;

import com.multi.module.domain.User;
import com.multi.module.domain.AuthorizationKakao;
import com.multi.module.repository.UserRepository;
import com.multi.module.service.auth.Oauth2Kakao;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final Oauth2Kakao oauth2Kakao;

    public User oauth2AuthorizationKakao(String code) {
        AuthorizationKakao authorization = oauth2Kakao.callTokenApi(code);
        System.out.println(authorization.getAccess_token());
        String userInfoFromKakao = oauth2Kakao.callGetUserByAccessToken(authorization.getAccess_token());
        System.out.println(userInfoFromKakao);
        JSONParser parser = new JSONParser();
        Object obj = new Object();
        try {
            obj = parser.parse(userInfoFromKakao);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) obj;
        Long kakaoId = (Long) jsonObject.get("id");
        JSONObject properties = (JSONObject) jsonObject.get("properties");
        String nickname = (String) properties.get("nickname");
        String profileImage = (String) properties.get("profile_image");
        System.out.println(nickname);
        System.out.println(profileImage);

        User user = User.createUser(kakaoId, nickname, profileImage, authorization.getAccess_token());
        System.out.println(user.toString());
        return validateDuplicateUser(user);
    }

    private User validateDuplicateUser(User user) {
        User findUser = userRepository.findUserByKakaoId(user.getKakaoId());
        User a = null;
        if (findUser != a) {
            findUser.setToken(user.getToken());
            userRepository.save(findUser);
            return findUser;
        } else {
            userRepository.save(user);
            return user;
        }
    }
}