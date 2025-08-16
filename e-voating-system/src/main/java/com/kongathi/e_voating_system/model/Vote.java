package com.kongathi.e_voating_system.model;

import jakarta.persistence.*;

@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long voterId;

    private String candidateName;

    public Vote() {
    }

    public Vote(Long voterId, String candidateName) {
        this.voterId = voterId;
        this.candidateName = candidateName;
    }

    public Long getId() {
        return id;
    }

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
