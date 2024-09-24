package com._x.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateSearchFilterDTO {
    private String candidateName;
    private String experience;
    private String noticePeriod;
    private String education;
    private String skills;
    private String phone;
    private String email;
    private String source;
    private String history;
    private Boolean isOpenToWork;
    private String contactStatus;
    private int page;
    private int limit;
}