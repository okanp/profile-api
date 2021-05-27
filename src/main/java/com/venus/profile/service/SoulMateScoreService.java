package com.venus.profile.service;

import com.venus.profile.domain.entity.Profile;
import com.venus.profile.util.astrology.Astrology;
import com.venus.profile.util.astrology.ZodiacBasedPairing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class SoulMateScoreService {
    double calculateScore(Profile p1, Profile p2) {
        return ZodiacBasedPairing.isBestCouple(
                Astrology.zodiac(p1.getBirthday()),
                Astrology.zodiac(p2.getBirthday())) ? 100 : 0;
    }
}
