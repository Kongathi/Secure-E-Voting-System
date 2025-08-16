package com.kongathi.e_voating_system.model;

public class VoteRequest {
    private Long voterId;
    private String candidateName;

    // Getters and setters
    public Long getVoterId() {
        return voterId;
    }

    public void setVoterId(Long voterId) {
        this.voterId = voterId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }
}
