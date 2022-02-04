package com.multi.module.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "user")
@Getter
@Setter
@ToString
public class User extends BaseTime {
    @Id @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String profileImage;

    private String nintendoId;

    private Long kakaoId;

    @OneToMany
    private List<UserParty> userParties = new ArrayList<>();

    //회원 등록
    public static User createUser(Long kakaoId, String name, String profileImage) {
        User user = new User();
        user.setKakaoId(kakaoId);
        user.setName(name);
        user.setProfileImage(profileImage);
        return user;
    }
}
