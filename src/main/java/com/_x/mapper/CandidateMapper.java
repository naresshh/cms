package com._x.mapper;

import com._x.dto.CandidateDTO;
import com._x.model.Candidate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CandidateMapper {
    Candidate toEntity(CandidateDTO candidateDTO);
    CandidateDTO toDTO(Candidate candidate);
    // Method to update an existing Entity with DTO data
    @Mapping(target = "candidateId", ignore = true)
    void updateCandidateFromDto(CandidateDTO candidateDTO, @MappingTarget Candidate candidate);

}