package com.multi.module.repository;

import com.multi.module.domain.UserParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPartyRepository extends JpaRepository<UserParty, Long> {
}
