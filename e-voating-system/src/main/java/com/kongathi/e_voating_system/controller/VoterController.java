package com.kongathi.e_voating_system.controller;

import com.kongathi.e_voating_system.model.Voter;
import com.kongathi.e_voating_system.repository.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/voters")
public class VoterController {

    @Autowired
    private VoterRepository voterRepository;

    // Register a new voter
    @PostMapping
    public Voter createVoter(@RequestBody Voter voter) {
        return voterRepository.save(voter);
    }

    // Get all voters
    @GetMapping
    public List<Voter> getAllVoters() {
        return voterRepository.findAll();
    }

    // Get voter by ID
    @GetMapping("/{id}")
    public ResponseEntity<Voter> getVoterById(@PathVariable Long id) {
        return voterRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a voter
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVoter(@PathVariable Long id) {
        if (!voterRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        voterRepository.deleteById(id);
        return ResponseEntity.ok("Voter deleted successfully.");
    }

    // Reset a voter's vote status
    @PutMapping("/{id}/reset")
    public ResponseEntity<String> resetVoteStatus(@PathVariable Long id) {
        return voterRepository.findById(id)
                .map(voter -> {
                    voter.setHasVoted(false);
                    voterRepository.save(voter);
                    return ResponseEntity.ok("Voter vote status reset.");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<Map<String, Object>> checkVoteStatus(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        return voterRepository.findById(id)
                .map(voter -> {
                    response.put("voterId", voter.getId());
                    response.put("hasVoted", voter.isHasVoted());
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    response.put("error", "Voter not found");
                    return ResponseEntity.status(404).body(response);
                });
    }

}
