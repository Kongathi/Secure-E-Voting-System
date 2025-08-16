package com.kongathi.e_voating_system.model;

import jakarta.persistence.*;

@Entity
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String party;

    private Integer voteCount = 0;

    public Candidate() {}

    public Candidate(String name, String party) {
        this.name = name;
        this.party = party;
        this.voteCount = 0;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    // Optional: Add vote increment method
    public void incrementVote() {
        this.voteCount++;
    }
}
