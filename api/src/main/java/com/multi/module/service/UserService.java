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
        String userInfoFromKakao = oauth2Kakao.callGetUserByAccessToken(authorization.getAccess_token());
        JSONParser parser = new JSONParser();
        Object obj = new Object();
        try {
            obj = parser.parse(userInfoFromKakao);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) obj;
        int id = (int) jsonObject.get("id");
        JSONObject properties = (JSONObject) jsonObject.get("properties");
        String nickname = (String) properties.get("nickname");
        String email = (String) properties.get("email");
        String profileImage = (String) properties.get("profileImage");

        User user = User.createUser(id, nickname, email, profileImage);

        validateDuplicateUser(user);
        userRepository.save(user);
        return user;
    }

    private void validateDuplicateUser(User user) {
        Optional<User> findUser = userRepository.findById(user.getId());
        if (findUser.isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다."); }
    }

}