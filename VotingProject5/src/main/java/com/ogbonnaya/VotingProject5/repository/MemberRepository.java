package com.ogbonnaya.VotingProject5.repository;

import com.ogbonnaya.VotingProject5.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository <Members, Long> {
}
