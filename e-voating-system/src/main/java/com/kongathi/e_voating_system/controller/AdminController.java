package com.kongathi.e_voating_system.controller;

import com.kongathi.e_voating_system.dto.VoteSummaryDto;
import com.kongathi.e_voating_system.model.Candidate;
import com.kongathi.e_voating_system.model.Voter;
import com.kongathi.e_voating_system.repository.CandidateRepository;
import com.kongathi.e_voating_system.repository.VoteRepository;
import com.kongathi.e_voating_system.repository.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private VoterRepository voterRepository;

    @PostMapping("/candidates")
    public Candidate addCandidate(@RequestBody Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @GetMapping("/candidates")
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    @DeleteMapping("/candidates/{id}")
    public ResponseEntity<String> deleteCandidate(@PathVariable Long id) {
        if (!candidateRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        candidateRepository.deleteById(id);
        return ResponseEntity.ok("Candidate deleted successfully.");
    }

    @PutMapping("/reset")
    public ResponseEntity<String> resetElection() {
        candidateRepository.findAll().forEach(candidate -> {
            candidate.setVoteCount(0);
            candidateRepository.save(candidate);
        });

        voteRepository.deleteAll();

        voterRepository.findAll().forEach(voter -> {
            voter.setHasVoted(false);
            voterRepository.save(voter);
        });

        return ResponseEntity.ok("Election data reset successfully.");
    }

    @GetMapping("/results")
    public List<VoteSummaryDto> getResults() {
        return candidateRepository.findAll()
                .stream()
                .map(candidate -> new VoteSummaryDto(candidate.getName(), candidate.getVoteCount()))
                .collect(Collectors.toList());
    }
}
