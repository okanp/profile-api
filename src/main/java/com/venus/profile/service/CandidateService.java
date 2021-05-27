package com.venus.profile.service;

import com.venus.profile.domain.dto.CandidateDto;
import com.venus.profile.domain.entity.Candidate;
import com.venus.profile.domain.entity.Preference;
import com.venus.profile.domain.entity.Profile;
import com.venus.profile.domain.enums.CandidateResponse;
import com.venus.profile.domain.enums.CandidateStatus;
import com.venus.profile.domain.mapper.CandidateMapper;
import com.venus.profile.repository.CandidateRepository;
import com.venus.profile.repository.ProfileRepository;
import com.venus.profile.util.Elo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CandidateService {

    private Logger logger = LoggerFactory.getLogger(CandidateService.class);

    private static final Set<CandidateResponse> LIKES = Set.of(CandidateResponse.LIKE, CandidateResponse.SUPER_LIKE);

    private final ProfileRepository profileRepository;

    private final CandidateRepository repository;

    private final CandidateMapper mapper;

    private final SoulMateScoreService soulMateScoreService;

    private final ProfileService profileService;

    public void deleteAll() {
        repository.deleteAll();
    }

    public void deleteAllByCreatedAtBefore(ZonedDateTime time) {
        repository.deleteAllByCreatedAtBefore(time);
    }

    @Transactional
    public void swipe(UUID id, UUID profileId, CandidateResponse response) {
        Profile profile = profileRepository.findOneById(profileId);
        Candidate candidate = repository.findOneById(id);
        boolean isProfileA = profileId.equals(candidate.getProfileA().getId());
        CandidateResponse otherResponse;
        Profile otherProfile;
        if (isProfileA) {
            candidate.setProfileAResponse(response);
            candidate.setProfileAResponseTime(ZonedDateTime.now());
            otherResponse = candidate.getProfileBResponse();
            otherProfile = candidate.getProfileB();
        } else {
            candidate.setProfileBResponse(response);
            candidate.setProfileBResponseTime(ZonedDateTime.now());
            otherResponse = candidate.getProfileAResponse();
            otherProfile = candidate.getProfileA();
        }

        // it's no match if only both sides dislike
        CandidateStatus status = CandidateStatus.NONE;
        if (otherResponse == CandidateResponse.NONE && response == CandidateResponse.DISLIKE) {
            status = CandidateStatus.NO_MATCH;
        } else if (otherResponse == CandidateResponse.NONE && response == CandidateResponse.LIKE) {
            // do nothing
        } else if (otherResponse == CandidateResponse.LIKE && response == CandidateResponse.DISLIKE) {
            int[] results = Elo.game(profile.getEloRanking(), otherProfile.getEloRanking(), true);
            profile.setEloRanking(results[0]);
        } else if (otherResponse == CandidateResponse.DISLIKE && response == CandidateResponse.LIKE) {
            int[] results = Elo.game(profile.getEloRanking(), otherProfile.getEloRanking(), false);
            profile.setEloRanking(results[1]);
        } else if (otherResponse == CandidateResponse.DISLIKE && response == CandidateResponse.DISLIKE) {
            status = CandidateStatus.NO_MATCH;
        } else if (LIKES.contains(response) && LIKES.contains(otherResponse)) {
            status = CandidateStatus.MATCH;
        }

        candidate.setStatus(status);
        profile.setLastActiveTime(ZonedDateTime.now());
        logger.info("{} {} {}", candidate.getProfileA(), candidate.getProfileB(), status);
    }

    public List<CandidateDto> findAll(Pageable page) {
        return repository.findAll(page).map(mapper::toCandidateDto).toList();
    }

    public List<CandidateDto> findAllCandidatesByProfileIdAndStatus(UUID id, CandidateStatus status, Pageable page) {
        return repository.findAllByProfileAorBAndStatus(id, status, page)
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

    private List<Profile> findPeopleMatchingPreference(Profile profile) {
        repository.deleteAllByProfileA_IdAndProfileAResponseAndProfileBResponse(profile.getId(), CandidateResponse.NONE, CandidateResponse.NONE);
        Preference preference = profile.getPreference() == null ? new Preference() : profile.getPreference();
        ZonedDateTime minBirthday = ZonedDateTime.now().minusYears(preference.getMaxAge());
        ZonedDateTime maxBirthday = ZonedDateTime.now().minusYears(preference.getMinAge());
        int age = (int) ChronoUnit.YEARS.between(profile.getBirthday(), ZonedDateTime.now());

        return profileRepository.findAllCandidatesByConditions(profile.getId(),
                preference.getGender(), profile.getGender(),
                profile.getLat(), profile.getLon(), preference.getSearchDistance(),
                minBirthday, maxBirthday, age);
    }

    private void createCandidates(UUID profileId) {
        repository.deleteAllByProfileAOrProfileBAndBothProfileResponse(profileId, CandidateResponse.NONE);
        Profile profile = profileRepository.findOneById(profileId);
        List<Profile> possibleCandidates = findPeopleMatchingPreference(profile);

        possibleCandidates.forEach(otherProfile -> {
            logger.info("{} <3? {}", profile.getName(), otherProfile.getName());
            Candidate c = new Candidate();
            c.setProfileA(profile);
            c.setProfileB(otherProfile);
            c.setSoulMateScore(soulMateScoreService.calculateScore(profile, otherProfile));
            repository.save(c);
        });
    }

    public void trigger(UUID id) {
        createCandidates(id);
        profileService.updateLastCandidateSearchTime(id);
    }

    public List<CandidateDto> findAllCandidatesByProfileId(UUID id, CandidateStatus status, CandidateResponse response, Pageable page) {
        if (response == null) {
            return findAllCandidatesByProfileIdAndStatus(id, status, page);
        }

        return repository.findAllCandidatesByProfileIdAndResponseAndStatus(id.toString(), response, status, page)
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
