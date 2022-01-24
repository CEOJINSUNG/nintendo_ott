package com.multi.module.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.multi.module.domain.User;
import com.multi.module.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserMutation implements GraphQLMutationResolver {

    private final UserService userService;

    public User logoutUser(String id) {
        return null;
    }
}
