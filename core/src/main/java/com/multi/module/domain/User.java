package com.multi.module.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = "user")
@Getter
@Setter
@ToString
public class User extends BaseTime {
    @Id @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;
    @NonNull
    private String email;
    private String profileImage;
    private String nintendoId;

    @Enumerated(EnumType.STRING)
    private Bank bank; // 은행 종류 출력
    private String account;

}
