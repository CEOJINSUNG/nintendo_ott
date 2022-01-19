package com.multi.module.service;

import com.multi.module.domain.User;
import com.multi.module.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public Long getUser(User user){
        return userRepository.findById(user.getId()).get().getId();
    }

}
