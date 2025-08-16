package com.kongathi.e_voating_system.repository;

import com.kongathi.e_voating_system.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByVoterId(Long voterId); // To prevent double voting
}
