package com.venus.profile.model.mapper;

import com.venus.profile.model.dto.CandidateDto;
import com.venus.profile.model.entity.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateMapper {

    @Autowired
    private ProfileMapper profileMapper;

    public CandidateDto toCandidateDto(Candidate source) {
        if (source == null) {
            return null;
        }
        CandidateDto target = new CandidateDto();
        target.setId(source.getId());
        target.setProfileA(profileMapper.toProfileDto(source.getProfileA()));
        target.setProfileB(profileMapper.toProfileDto(source.getProfileB()));
        target.setStatus(source.getStatus());
        return target;
    }

    public Candidate toCandidate(CandidateDto source) {
        if (source == null) {
            return null;
        }
        Candidate target = new Candidate();
        target.setId(source.getId());
        target.setProfileA(profileMapper.toProfile(source.getProfileA()));
        target.setProfileB(profileMapper.toProfile(source.getProfileB()));
        target.setStatus(source.getStatus());
        return target;
    }
}
