package com.venus.profile.model.mapper;

import com.venus.profile.model.dto.PreferenceDto;
import com.venus.profile.model.entity.Preference;
import org.springframework.stereotype.Service;

@Service
public class PreferenceMapper {
    public PreferenceDto toPreferenceDto(Preference source) {
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

    public Preference toPreference(PreferenceDto source) {
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
