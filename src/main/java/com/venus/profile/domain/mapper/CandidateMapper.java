package com.venus.profile.domain.mapper;

import com.venus.profile.domain.dto.CandidateDto;
import com.venus.profile.domain.entity.Candidate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProfileMapper.class})
public interface CandidateMapper {

    CandidateDto toCandidateDto(Candidate source);

    Candidate toCandidate(CandidateDto source);
}
