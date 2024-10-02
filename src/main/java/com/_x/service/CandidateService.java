package com._x.service;

import com._x.dto.CandidateDTO;
import com._x.exception.ResourceNotFoundException;
import com._x.mapper.CandidateMapper;
import com._x.model.Candidate;
import com._x.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;

    @Autowired
    private ConsumerServiceClient consumerServiceClient;
    @Autowired
    public CandidateService(CandidateRepository candidateRepository, CandidateMapper candidateMapper) {
        this.candidateRepository = candidateRepository;
        this.candidateMapper = candidateMapper;
    }
    @Transactional
    public CandidateDTO saveCandidate(CandidateDTO candidateDTO) {
        try {
            Candidate candidate = candidateMapper.toEntity(candidateDTO);
            Candidate savedCandidate = candidateRepository.save(candidate);
            consumerServiceClient.sendCandidateData(candidateMapper.toDTO(savedCandidate));
            return candidateMapper.toDTO(savedCandidate);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert or save candidate", e);
        }
    }
    public CandidateDTO updateCandidate(Long candidateId, CandidateDTO candidateDTO) {

        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with id: " + candidateId));

        candidateMapper.updateCandidateFromDto(candidateDTO, candidate);
        Candidate updatedCandidate = candidateRepository.save(candidate);
        return candidateMapper.toDTO(updatedCandidate);
    }

    public Page<CandidateDTO> searchCandidates(
            String candidateName,
            String experience,
            String noticePeriod,
            String education,
            String skills,
            String phone,
            String email,
            String source,
            String history,
            Boolean isOpenToWork,
            String contactStatus,
            int page,
            int limit) {

        Pageable pageable = PageRequest.of(page, limit);
        Specification<Candidate> spec = CandidateSpecification.withFilters(
                candidateName, experience, noticePeriod, education, skills, phone, email, source, history, isOpenToWork
                , contactStatus
        );

        Page<Candidate> candidates = candidateRepository.findAll(spec, pageable);
        return candidates.map(candidateMapper::toDTO);
    }

}