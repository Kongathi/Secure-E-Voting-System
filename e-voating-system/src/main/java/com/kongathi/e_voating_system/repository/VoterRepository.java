package com.kongathi.e_voating_system.repository;

import com.kongathi.e_voating_system.model.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoterRepository extends JpaRepository<Voter, Long> {
    // You can add custom methods here if needed later
}
