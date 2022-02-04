package com.multi.module.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "user_party")
@Getter
@Setter
public class UserParty extends BaseTime {
    @Id @GeneratedValue
    @Column(name = "user_party_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id")
    private Party party;

    @Enumerated(EnumType.STRING)
    private Role role; // LEADER, PARTICIPANT

    private int price;
    private String description;

    //연관관계 메서드
    public void addUser(User user) {
        this.user = user;
        user.getUserParties().add(this);
    }

    public void addParty(Party party) {
        this.party = party;
        party.getUserParties().add(this);
    }
}
