package com._x.controller;

import com._x.security.JwtUtils;
import com._x.security.LoginRequest;
import com._x.security.LoginResponse;
import com._x.service.CandidateService;
import com._x.dto.CandidateDTO;
import com._x.dto.CandidateSearchFilterDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;
    private final JwtUtils jwtUtils;
    @Autowired
    public CandidateController(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.UNAUTHORIZED);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        LoginResponse response = new LoginResponse(userDetails.getUsername(), roles, jwtToken);

        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<CandidateDTO> createCandidate(@Valid @RequestBody CandidateDTO candidate) {
        CandidateDTO newCandidate = candidateService.saveCandidate(candidate);
        return ResponseEntity.ok(newCandidate);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{candidateId}")
    public ResponseEntity<CandidateDTO> updateCandidate(
            @PathVariable Long candidateId,
            @Valid @RequestBody CandidateDTO candidateDTO) {

        CandidateDTO updatedCandidate = candidateService.updateCandidate(candidateId, candidateDTO);
        return ResponseEntity.ok(updatedCandidate);
    }
    @GetMapping("/search")
    public Page<CandidateDTO> searchCandidates(@ModelAttribute CandidateSearchFilterDTO filterDTO,
                                               @RequestParam(value = "page", defaultValue = "1") int page,
                                               @RequestParam(value = "limit", defaultValue = "10") int limit) {
        return candidateService.searchCandidates(
                filterDTO.getCandidateName(),
                filterDTO.getExperience(),
                filterDTO.getNoticePeriod(),
                filterDTO.getEducation(),
                filterDTO.getSkills(),
                filterDTO.getPhone(),
                filterDTO.getEmail(),
                filterDTO.getSource(),
                filterDTO.getHistory(),
                filterDTO.getIsOpenToWork(),
                filterDTO.getContactStatus(),
                page-1,
                limit
        );
    }
}