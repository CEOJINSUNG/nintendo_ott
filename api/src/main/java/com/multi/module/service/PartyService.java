package com.multi.module.service;

import com.multi.module.domain.*;
import com.multi.module.repository.PartyRepository;
import com.multi.module.repository.UserPartyRepository;
import com.multi.module.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PartyService {

    private final UserRepository userRepository;
    private final PartyRepository partyRepository;
    private final UserPartyRepository userPartyRepository;

    public Long makeParty(
        String userId,
        String bank,
        String account,
        String nintendoId,
        Integer inviteNum,
        Status status
    ) {
        //1. user 찾아 닌텐도 아이디 설정
        User user = userRepository.findUserById(Long.parseLong(userId));
        user.setNintendoId(nintendoId);

        //2. party 생성 후 bank, account, invite_num, status 세팅
        Party party = new Party();
        for (Bank bank1: Bank.values()) {
            if (bank1.name().equalsIgnoreCase(bank)){
                party.setBank(bank1);
                break;
            }
        }
        party.setAccount(account);
        party.setStartDate(LocalDateTime.now());
        party.setInviteNum(inviteNum);
        party.setStatus(status);

        //3. user_party 생성 후 호스트 세팅
        UserParty userParty = new UserParty();
        userParty.addUser(user);
        userParty.addParty(party);
        userParty.setRole(Role.LEADER);

        userRepository.save(user);
        userPartyRepository.save(userParty);
        partyRepository.save(party);

        return userPartyRepository.findUserPartiesByUser(user).getParty().getId();
    }
}
