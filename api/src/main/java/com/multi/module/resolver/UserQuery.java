package com.multi.module.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.multi.module.domain.User;
import com.multi.module.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class UserQuery implements GraphQLQueryResolver {

    private final UserService userService;

    public User loginUser(String code) {
        return userService.oauth2AuthorizationKakao(code);
    }

}
