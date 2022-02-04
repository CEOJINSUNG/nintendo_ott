package com.multi.module.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.multi.module.domain.*;
import com.multi.module.repository.PartyRepository;
import com.multi.module.repository.UserPartyRepository;
import com.multi.module.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Mutation implements GraphQLMutationResolver {

    private UserRepository userRepository;
    private PartyRepository partyRepository;
    private UserPartyRepository userPartyRepository;

    //초대 기능 없이 파티장이 파티 등록했을 때
    public Long makePartyNoInvite(
        @RequestParam String userId,
        @RequestParam String bank,
        @RequestParam String account,
        @RequestParam String nintendoId
    ) {
        //user 찾아 닌텐도 아이디 설정
        User user = userRepository.findById(Long.parseLong(userId)).orElse(null);
        user.setNintendoId(nintendoId);

        //party 생성 후 bank, account, status 세팅
        Party party = new Party();
        for (Bank bank1: Bank.values()) {
            if (bank1.name().equalsIgnoreCase(bank)){
                party.setBank(bank1);
                break;
            }
        }
        party.setAccount(account);
        party.setStatus(Status.Waiting);

        //userparty 생성 후 호스트 세팅
        UserParty userParty = new UserParty();
        userParty.addUser(user);
        userParty.addParty(party);
        userParty.setRole(Role.LEADER);

        userRepository.save(user);
        userPartyRepository.save(userParty);
        partyRepository.save(party);
        return userPartyRepository.findUserPartiesByUser(user).getId();
    }

    //초대 가능 인원 변경
    public JSONObject changeInviteNum(
        @RequestParam String userId,
        @RequestParam String partyId,
        @RequestParam String inviteNum
    ) {
        Party party = partyRepository.findById(Long.parseLong(partyId)).orElse(null);
        party.setInviteNum(Integer.parseInt(inviteNum));
        partyRepository.save(party);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("partyId", party.getId());
        jsonObject.put("inviteNum", party.getInviteNum());
        return jsonObject;
    }

}
