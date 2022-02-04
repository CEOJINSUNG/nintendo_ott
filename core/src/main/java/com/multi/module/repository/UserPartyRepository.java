package com.multi.module.repository;

import com.multi.module.domain.Party;
import com.multi.module.domain.User;
import com.multi.module.domain.UserParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface UserPartyRepository extends JpaRepository<UserParty, Long> {
    UserParty findUserPartiesByUser(User user);
    ArrayList<UserParty> findUserPartiesByParty(Party party);
}
