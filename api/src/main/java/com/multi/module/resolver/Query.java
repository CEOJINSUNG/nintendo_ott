package com.multi.module.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.multi.module.domain.Party;
import com.multi.module.domain.Role;
import com.multi.module.domain.User;
import com.multi.module.domain.UserParty;
import com.multi.module.dto.MyPartyDto;
import com.multi.module.repository.PartyRepository;
import com.multi.module.repository.UserPartyRepository;
import com.multi.module.repository.UserRepository;
import com.multi.module.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;


@Component
@RequiredArgsConstructor
public class Query implements GraphQLQueryResolver {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PartyRepository partyRepository;
    private final UserPartyRepository userPartyRepository;

    public User loginUser(@RequestParam String code) {
        return userService.oauth2AuthorizationKakao(code);
    }

    public MyPartyDto getMyParty(
        @RequestParam String userId,
        @RequestParam String partyId
    ) {
        // 1. 파티 아이디로 파티 찾기
        Party party = partyRepository.findById(Long.parseLong(partyId)).orElse(null);

        // 2. MyPartyDto 생성 후 파티 아이디, 게스트, 상태 리턴
        MyPartyDto myPartyDto = new MyPartyDto();
        myPartyDto.setPartyId(partyId);
        myPartyDto.setStatus(party.getStatus());

        // 3. 초대 가능한 인원, host, guests 찾기
        ArrayList<UserParty> userPartyList = userPartyRepository.findUserPartiesByParty(party);
        myPartyDto.setInviteNum((7-userPartyList.toArray().length));

        ArrayList<User> guests = new ArrayList<>();
        userPartyList.stream().forEach(userParty -> {
            if (userParty.getRole().equals(Role.LEADER)) {
                myPartyDto.setHost(userParty.getUser());
            } else {
                guests.add(userParty.getUser());
            }
        });
        myPartyDto.setGuests(guests);

        return myPartyDto;
    }

}
