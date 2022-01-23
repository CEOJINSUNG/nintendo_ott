package com.multi.module.controller;

import com.multi.module.domain.User;
import com.multi.module.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/oauth2/authorization/kakao")
    public User loginByKakao(@RequestParam("code") String code) {
        return userService.oauth2AuthorizationKakao(code);
    }
}
