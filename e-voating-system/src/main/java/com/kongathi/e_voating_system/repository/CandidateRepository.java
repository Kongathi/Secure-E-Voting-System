package com.kongathi.e_voating_system.repository;

import com.kongathi.e_voating_system.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
}
