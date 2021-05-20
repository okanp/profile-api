package com.venus.profile.service;

import com.venus.profile.model.dto.CandidateDto;
import com.venus.profile.model.entity.Candidate;
import com.venus.profile.model.entity.Profile;
import com.venus.profile.model.mapper.CandidateMapper;
import com.venus.profile.repository.CandidateRepository;
import com.venus.profile.repository.ProfileRepository;
import com.venus.profile.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class CandidateService {

    private Logger logger = LoggerFactory.getLogger(CandidateService.class);

    private static final Set<Integer> LIKES = Set.of(Constants.LIKE, Constants.SUPERLIKE);

    private ProfileRepository profileRepository;

    private CandidateRepository repository;

    private CandidateMapper mapper;

    public CandidateService(ProfileRepository profileRepository, CandidateRepository repository, CandidateMapper mapper) {
        this.profileRepository = profileRepository;
        this.repository = repository;
        this.mapper = mapper;
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public void deleteAllByCreatedAtBefore(ZonedDateTime time) {
        repository.deleteAllByCreatedAtBefore(time);
    }

    @Transactional
    public void swipe(UUID id, UUID profileId, int response) {
        Profile profile = profileRepository.findOneById(profileId);
        Candidate candidate = repository.findOneById(id);
        boolean isProfileA = profileId.equals(candidate.getProfileA().getId());
        int otherResponse;
        if (isProfileA) {
            candidate.setProfileAResponse(response);
            candidate.setProfileAResponseTime(ZonedDateTime.now());
            otherResponse = candidate.getProfileBResponse();
        } else {
            candidate.setProfileBResponse(response);
            candidate.setProfileBResponseTime(ZonedDateTime.now());
            otherResponse = candidate.getProfileAResponse();
        }

        int status = 0;
        if (response == Constants.DISLIKE) {
            status = Constants.NOMATCH;
        } else if (LIKES.contains(response) && LIKES.contains(otherResponse)) {
            status = Constants.MATCH;
        }

        candidate.setStatus(status);
        profile.setLastActiveTime(ZonedDateTime.now());
        logger.info("{} {} {}", candidate.getProfileA(), candidate.getProfileB(), status);
    }

    public List<CandidateDto> findAll(Pageable page) {
        return repository.findAll(page).map(mapper::toCandidateDto).toList();
    }

    public List<CandidateDto> findAllCandidatesByProfileId(UUID id, int status, Pageable page) {
        return repository.findAllByProfileAorBAndStatus(id.toString(), status, page)
                .map(mapper::toCandidateDto)
                .map(candidate -> {
                    if (candidate.getProfileA().getId().equals(id)) {
                        candidate.setProfileA(candidate.getProfileB());
                    }
                    candidate.setProfileB(null);
                    return candidate;
                })
                .toList();
    }
}
