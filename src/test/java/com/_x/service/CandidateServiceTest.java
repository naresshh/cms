package com._x.service;

import com._x.dto.CandidateDTO;
import com._x.exception.ResourceNotFoundException;
import com._x.mapper.CandidateMapper;
import com._x.model.Candidate;
import com._x.repository.CandidateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CandidateServiceTest {

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private CandidateMapper candidateMapper;

    @InjectMocks
    private CandidateService candidateService;

    @Test
    void saveCandidate() {
        CandidateDTO candidateDTO = new CandidateDTO();
        candidateDTO.setCandidateName("Naresh");
        candidateDTO.setExperience("4 years");

        Candidate candidate = new Candidate();
        candidate.setCandidateName("Naresh");
        candidate.setExperience("4 years");

       when(candidateMapper.toEntity(candidateDTO)).thenReturn(candidate);
       when(candidateRepository.save(candidate)).thenReturn(candidate);
       when(candidateMapper.toDTO(candidate)).thenReturn(candidateDTO);

        CandidateDTO result = candidateService.saveCandidate(candidateDTO);

        assertNotNull(result, "The result should not be null");
        assertEquals(candidateDTO.getCandidateName(), result.getCandidateName(), "Candidate name should match");
        assertEquals(candidateDTO.getExperience(), result.getExperience(), "Experience should match");
    }

    @Test
    void updateCandidate_ShouldReturnUpdatedCandidateDTO() {
        Long candidateId = 1L;
        CandidateDTO candidateDTO = createCandidateDTO();
        Candidate existingCandidate = createExistingCandidate(candidateId);
        Candidate updatedCandidate = createUpdatedCandidate(candidateId);

        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(existingCandidate));
        when(candidateRepository.save(existingCandidate)).thenReturn(updatedCandidate);
        when(candidateMapper.toDTO(updatedCandidate)).thenReturn(candidateDTO);

        CandidateDTO result = candidateService.updateCandidate(candidateId, candidateDTO);
        assertEquals(candidateDTO, result);
        assertEquals("Naresh", result.getCandidateName());
        assertEquals("4 years", result.getExperience());

    }

    @Test
    void updateCandidate_WhenNotFound_ShouldThrowException() {
        Long candidateId = 1L;
        CandidateDTO candidateDTO = new CandidateDTO();
        when(candidateRepository.findById(candidateId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> candidateService.updateCandidate(candidateId, candidateDTO));
    }

    @Test
    void testSearchCandidates() {

        Pageable pageable = PageRequest.of(0, 10);

        List<Candidate> candidates = List.of(
                new Candidate("John"),
                new Candidate("Jane")
        );

        // Initialize the Page object with the candidate entity list
        Page<Candidate> candidatePage = new PageImpl<>(candidates, pageable, candidates.size());

        // Mock the repository method call to return the candidatePage (Page<Candidate>)
        when(candidateRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(candidatePage);

        // Mock the CandidateMapper behavior to return DTOs
        when(candidateMapper.toDTO(any(Candidate.class)))
                .thenAnswer(invocation -> new CandidateDTO(((Candidate) invocation.getArgument(0)).getCandidateName()));

        Page<CandidateDTO> result = candidateService.searchCandidates(
                "John", null, null, null, null, null, null, null, null, null, null, 0, 10);

        // Then
        assertNotNull(result); // Ensure result is not null
        assertEquals(2, result.getTotalElements()); // Ensure two candidates were returned
        assertEquals("John", result.getContent().get(0).getCandidateName()); // Verify first candidate's name
    }



    private CandidateDTO createCandidateDTO() {
        CandidateDTO dto = new CandidateDTO();
        dto.setCandidateName("Naresh");
        dto.setExperience("4 years");
        return dto;
    }

    private Candidate createExistingCandidate(Long id) {
        Candidate candidate = new Candidate();
        candidate.setCandidateId(id);
        candidate.setCandidateName("Naresh M");
        candidate.setExperience("5 years");
        return candidate;
    }

    private Candidate createUpdatedCandidate(Long id) {
        Candidate candidate = createExistingCandidate(id);
        candidate.setCandidateName("Naresh");
        candidate.setExperience("4 years");
        return candidate;
    }

}