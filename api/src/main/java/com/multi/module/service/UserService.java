package com.multi.module.service;

import com.multi.module.domain.User;
import com.multi.module.domain.AuthorizationKakao;
import com.multi.module.repository.UserRepository;
import com.multi.module.service.auth.Oauth2Kakao;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final Oauth2Kakao oauth2Kakao;

    public ResponseEntity<User> oauth2AuthorizationKakao(String code) {
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

        User user = User.createUser(kakaoId, nickname, profileImage);
        return validateDuplicateUser(user, authorization.getAccess_token());
    }

    private ResponseEntity<User> validateDuplicateUser(User user, String token) {
        User findUser = userRepository.findUserByKakaoId(user.getKakaoId());
        User a = null;
        HttpHeaders headers = new HttpHeaders();
        headers.set("token", token);
        if (findUser != a) {
            userRepository.save(findUser);
            return new ResponseEntity<User>(findUser, headers, HttpStatus.OK);
        } else {
            userRepository.save(user);
            return new ResponseEntity<User>(findUser, headers, HttpStatus.OK);
        }
    }
}