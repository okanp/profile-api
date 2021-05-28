package com.venus.profile.domain.mapper;

import com.venus.profile.domain.dto.ProfileDto;
import com.venus.profile.domain.entity.Profile;
import org.mapstruct.*;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Mapper(componentModel = "spring", uses = {PreferenceMapper.class})
public abstract class ProfileMapper {

    @Mapping(source = "birthday", target = "age", qualifiedByName = "birthdayToAge")
    public abstract ProfileDto toProfileDto(Profile source);

    public abstract Profile toProfile(ProfileDto source);

    @AfterMapping
    void setPreferenceProfile(@MappingTarget Profile profile) {
        if (profile.getPreference() != null) {
            profile.getPreference().setProfile(profile);
        }
    }

    @Named("birthdayToAge")
    static double birthdayToAge(ZonedDateTime birthday) {
        return ((int) ChronoUnit.YEARS.between(birthday, ZonedDateTime.now())) + 1;
    }
}
