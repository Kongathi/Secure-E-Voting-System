package com.kongathi.e_voating_system.dto;

public class VoteSummaryDto {
    private String candidateName;
    private int voteCount;

    public VoteSummaryDto(String candidateName, int voteCount) {
        this.candidateName = candidateName;
        this.voteCount = voteCount;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public int getVoteCount() {
        return voteCount;
    }

}
