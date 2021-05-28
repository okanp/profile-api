package com.venus.profile.domain.mapper;

import com.venus.profile.domain.dto.PreferenceDto;
import com.venus.profile.domain.entity.Preference;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PreferenceMapper {
    PreferenceDto toPreferenceDto(Preference source);

    Preference toPreference(PreferenceDto source);
}
