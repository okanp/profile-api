package com.venus.profile.domain.mapper;

import com.venus.profile.domain.dto.PreferenceDto;
import com.venus.profile.domain.entity.Preference;
import org.springframework.stereotype.Service;

@Service
class PreferenceMapper {
    PreferenceDto toPreferenceDto(Preference source) {
        if (source == null) {
            return null;
        }
        PreferenceDto target = new PreferenceDto();
        target.setMaxAge(source.getMaxAge());
        target.setMinAge(source.getMinAge());
        target.setSearchDistance(source.getSearchDistance());
        target.setGender(source.getGender());
        return target;
    }

    Preference toPreference(PreferenceDto source) {
        if (source == null) {
            return null;
        }
        Preference target = new Preference();
        target.setMaxAge(source.getMaxAge());
        target.setMinAge(source.getMinAge());
        target.setSearchDistance(source.getSearchDistance());
        target.setGender(source.getGender());
        return target;
    }
}
