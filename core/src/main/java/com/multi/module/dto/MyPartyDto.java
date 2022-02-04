package com.multi.module.dto;

import com.multi.module.domain.Status;
import com.multi.module.domain.User;
import com.multi.module.domain.UserParty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class MyPartyDto {
    String partyId;
    User host;
    ArrayList<User> guests;
    Status status;
    Integer inviteNum;
}
