package com.multi.module.domain;

import lombok.Getter;
import lombok.NonNull;
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
    private int id;

    private String name;

    private String email;

    private String profileImage;

    private String nintendoId;

    @OneToMany
    private List<UserParty> userParties = new ArrayList<>();

    //회원 등록
    public static User createUser(int id, String name, String email, String profileImage) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setProfileImage(profileImage);
        return user;
    }
}
