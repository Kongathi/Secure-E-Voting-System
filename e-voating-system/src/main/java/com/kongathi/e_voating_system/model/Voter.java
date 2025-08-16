package com.kongathi.e_voating_system.model;

import jakarta.persistence.*;

@Entity
public class Voter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private boolean hasVoted;

    public Voter() {}

    public Voter(String name, String email, boolean hasVoted) {
        this.name = name;
        this.email = email;
        this.hasVoted = hasVoted;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isHasVoted() { return hasVoted; }
    public void setHasVoted(boolean hasVoted) { this.hasVoted = hasVoted; }
}

