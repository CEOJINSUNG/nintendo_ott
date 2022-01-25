package com.multi.module.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.multi.module.domain.User;
import com.multi.module.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserResolver implements GraphQLResolver<User> {

    private final UserRepository userRepository;

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(null);
    }

    public Long getUser_id(User user) {
        return userRepository.findUserByKakaoId(user.getKakaoId()).getId();
    }

    public String getName(User user) {
        return userRepository.findUserByKakaoId(user.getKakaoId()).getName();
    }

    public String getProfile_image(User user) {
        return userRepository.findUserByKakaoId(user.getKakaoId()).getProfileImage();
    }

    public String getToken(User user) {
        return userRepository.findUserByKakaoId(user.getKakaoId()).getToken();
    }

    public String getNintendo_id(User user) {
        return userRepository.findUserByKakaoId(user.getKakaoId()).getNintendoId();
    }
}
