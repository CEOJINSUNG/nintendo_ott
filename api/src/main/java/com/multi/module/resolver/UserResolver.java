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

    public User getUser(int id) {
        return userRepository.findById(id).orElseThrow(null);
    }

    public String getName(User user) {
        return userRepository.findById(user.getId()).get().getName();
    }

    public String getProfile_image(User user) {
        return userRepository.findById(user.getId()).get().getProfileImage();
    }

    public String getNintendo_id(User user) {
        return userRepository.findById(user.getId()).get().getNintendoId();
    }
}
