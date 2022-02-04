package com.multi.module.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.multi.module.domain.*;
import com.multi.module.repository.PartyRepository;
import com.multi.module.repository.UserPartyRepository;
import com.multi.module.repository.UserRepository;
import com.multi.module.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class Mutation implements GraphQLMutationResolver {

    private final PartyService partyService;
    private final UserRepository userRepository;
    private final PartyRepository partyRepository;
    private final UserPartyRepository userPartyRepository;

    //초대 기능 없이 파티장이 파티 등록했을 때
    public Long makePartyNoInvite(
        @RequestParam String userId,
        @RequestParam String bank,
        @RequestParam String account,
        @RequestParam String nintendoId
    ) {
        return partyService.makeParty(
            userId, bank, account, nintendoId, 0, Status.Waiting
        );
    }

    //초대 기능으로 파티장이 파티 등록했을 때
    public Long makePartyWithInvite(
        @RequestParam String userId,
        @RequestParam String bank,
        @RequestParam String account,
        @RequestParam String nintendoId,
        @RequestParam Integer inviteNum
    ) {
        return partyService.makeParty(
            userId, bank, account, nintendoId, inviteNum, Status.Inviting
        );
    }

    //초대 가능 인원 변경
    public JSONObject changeInviteNum(
        @RequestParam String userId,
        @RequestParam String partyId,
        @RequestParam String inviteNum
    ) {
        User user = userRepository.findById(Long.parseLong(userId)).orElse(null);
        UserParty userParty = userPartyRepository.findUserPartiesByUser(user);
        if (userParty.getRole() == (Role.LEADER)) {
            Party party = partyRepository.findById(Long.parseLong(partyId)).orElse(null);
            party.setInviteNum(Integer.parseInt(inviteNum));
            partyRepository.save(party);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("partyId", party.getId());
            jsonObject.put("inviteNum", party.getInviteNum());
            return jsonObject;
        }
        return null;
    }
}
