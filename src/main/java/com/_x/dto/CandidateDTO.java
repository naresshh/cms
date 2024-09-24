package com._x.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateDTO {
    @NotBlank(message = "Candidate name is required")
    private String candidateName;

    @NotBlank(message = "Experience is required")
    private String experience;

    @NotBlank(message = "Notice period is required")
    private String noticePeriod;

    @NotBlank(message = "Education is required")
    private String education;

    @NotBlank(message = "Skills are required")
    private String skills;

    @Size(min = 10, max = 10, message = "Phone number must be 10 digits")
    private String phone;

    @Email(message = "Invalid email address")
    @NotBlank(message = "Email is required")
    private String email;

    private String source;
    private String history;

    private boolean isOpenToWork;

    @NotBlank(message = "Contact status is required")
    private String contactStatus;

    public CandidateDTO(String candidateName){
        this.candidateName=candidateName;
    }
}