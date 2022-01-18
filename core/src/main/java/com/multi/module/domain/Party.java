package com.multi.module.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "party")
@Getter
@Setter
@ToString
public class Party extends BaseTime {

    @Id @Column(name = "party_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "party")
    private List<UserParty> userParties = new ArrayList<>();

    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
