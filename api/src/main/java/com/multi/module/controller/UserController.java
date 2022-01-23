package com.multi.module.controller;

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
    public void oauth2AuthorizationKakao(@RequestParam("code") String code) {
        userService.oauth2AuthorizationKakao(code);
    }
}
