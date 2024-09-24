package com._x.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long candidateId;

    private String candidateName;
    private String experience;
    private String noticePeriod;
    private String education;
    private String skills;
    private String phone;
    private String email;
    private String source;
    private String history;
    private boolean isOpenToWork;
    private String contactStatus;

    public Candidate(String candidateName){
        this.candidateName=candidateName;
    }
}