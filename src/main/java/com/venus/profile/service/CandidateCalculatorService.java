package com.venus.profile.service;

import com.venus.profile.model.entity.Candidate;
import com.venus.profile.model.entity.Preference;
import com.venus.profile.model.entity.Profile;
import com.venus.profile.repository.CandidateRepository;
import com.venus.profile.repository.ProfileRepository;
import com.venus.profile.util.Coordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CandidateCalculatorService {

    private Logger logger = LoggerFactory.getLogger(CandidateCalculatorService.class);

    private ProfileRepository profileRepository;

    private CandidateRepository repository;

    private ProfileService profileService;

    public CandidateCalculatorService(ProfileRepository profileRepository, CandidateRepository repository, ProfileService profileService) {
        this.profileRepository = profileRepository;
        this.repository = repository;
        this.profileService = profileService;
    }

    void calculate(UUID profileId) {
        repository.deleteAllByProfileA_IdAndProfileAResponseAndProfileBResponse(profileId, 0, 0);
        Profile profile = profileRepository.findOneById(profileId);
        Preference preference = profile.getPreference() == null ? new Preference() : profile.getPreference();
        double[] boundingBox = Coordinates.getBoundingBox(profile.getLat(), profile.getLon(), preference.getSearchDistance());
        int limit = 100;
        ZonedDateTime minBirthday = ZonedDateTime.now().minusYears(preference.getMaxAge());
        ZonedDateTime maxBirthday = ZonedDateTime.now().minusYears(preference.getMinAge());
        int age = (int) ChronoUnit.YEARS.between(profile.getBirthday(), ZonedDateTime.now());

        List<Profile> profiles = profileRepository.findAllCandidatesByConditions(profile.getId().toString(),
                preference.getGender(), profile.getGender(),
                boundingBox[0], boundingBox[2], boundingBox[1], boundingBox[3],
                minBirthday, maxBirthday, age,
                limit);
        List<Candidate> candidates = profiles.stream().map(x -> {
            logger.info("{} <3? {}", profile.getName(), x.getName());
            Candidate c = new Candidate();
            c.setProfileA(profile);
            c.setProfileB(x);
            return c;
        }).collect(Collectors.toList());

        repository.saveAll(candidates);
    }

    public void trigger(UUID id) {
        calculate(id);
        profileService.updateLastCandidateSearchTime(id);
    }
}
