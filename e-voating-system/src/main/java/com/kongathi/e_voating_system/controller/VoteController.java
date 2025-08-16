package com.kongathi.e_voating_system.controller;

import com.kongathi.e_voating_system.dto.VoteSummaryDto;
import com.kongathi.e_voating_system.model.Candidate;
import com.kongathi.e_voating_system.model.Vote;
import com.kongathi.e_voating_system.model.VoteRequest;
import com.kongathi.e_voating_system.repository.CandidateRepository;
import com.kongathi.e_voating_system.repository.VoteRepository;
import com.kongathi.e_voating_system.repository.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/votes")
public class VoteController {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private VoterRepository voterRepository;

    @PostMapping
    public String castVote(@RequestBody VoteRequest voteRequest) {
        if (!voterRepository.existsById(voteRequest.getVoterId())) {
            return "Invalid Voter ID. You must be registered to vote.";
        }

        if (voteRepository.findByVoterId(voteRequest.getVoterId()).isPresent()) {
            return "You have already voted!";
        }

        Candidate candidate = candidateRepository.findAll()
                .stream()
                .filter(c -> c.getName().equalsIgnoreCase(voteRequest.getCandidateName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        candidate.setVoteCount(candidate.getVoteCount() + 1);
        candidateRepository.save(candidate);

        Vote vote = new Vote();
        vote.setVoterId(voteRequest.getVoterId());
        vote.setCandidateName(voteRequest.getCandidateName());
        voteRepository.save(vote);

        return "Vote cast successfully!";
    }

    @GetMapping
    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    @GetMapping("/test")
    public String testVoteEndpoint() {
        return "Vote endpoint is reachable";
    }

    @GetMapping("/summary")
    public List<VoteSummaryDto> getVoteSummary() {
        return candidateRepository.findAll()
                .stream()
                .map(candidate -> new VoteSummaryDto(candidate.getName(), candidate.getVoteCount()))
                .collect(Collectors.toList());
    }
    @GetMapping("/hasVoted")
    public boolean hasVoted(@RequestParam Long voterId) {
        return voteRepository.findByVoterId(voterId).isPresent();
    }

}
