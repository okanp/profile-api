package com.venus.profile.domain.dto;

import com.venus.profile.domain.enums.CandidateStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class CandidateDto {
    private UUID id;
    private ProfileDto profileA;
    private ProfileDto profileB;
    private CandidateStatus status;
}
