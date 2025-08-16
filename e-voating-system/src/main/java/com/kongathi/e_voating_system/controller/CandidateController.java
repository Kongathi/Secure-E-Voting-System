package com.kongathi.e_voating_system.controller;

import com.kongathi.e_voating_system.model.Candidate;
import com.kongathi.e_voating_system.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    @Autowired
    private CandidateRepository candidateRepository;

    @PostMapping
    public Candidate addCandidate(@RequestBody Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @GetMapping
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    @GetMapping("/{id}")
    public Candidate getCandidateById(@PathVariable Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate not found with id: " + id));
    }
}
