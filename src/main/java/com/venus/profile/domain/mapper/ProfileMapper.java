package com.venus.profile.domain.mapper;

import com.venus.profile.domain.dto.ProfileDto;
import com.venus.profile.domain.entity.Profile;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class ProfileMapper {

    private PreferenceMapper preferenceMapper;

    public ProfileMapper(PreferenceMapper preferenceMapper) {
        this.preferenceMapper = preferenceMapper;
    }

    public ProfileDto toProfileDto(Profile source) {
        ProfileDto target = new ProfileDto();
        target.setName(source.getName());
        target.setId(source.getId());
        int years = (int) ChronoUnit.YEARS.between(source.getBirthday(), ZonedDateTime.now());
        target.setAge(years + 1);
        target.setGender(source.getGender());
        target.setPhotos(source.getPhotos());
        target.setPreference(preferenceMapper.toPreferenceDto(source.getPreference()));
        target.setBirthday(source.getBirthday());
        return target;
    }

    public Profile toProfile(ProfileDto source) {
        Profile target = new Profile();
        target.setName(source.getName());
        target.setId(source.getId());
        target.setGender(source.getGender());
        target.setPhotos(source.getPhotos());
        target.setBirthday(source.getBirthday());
        target.setPreference(preferenceMapper.toPreference(source.getPreference()));
        if (target.getPreference() != null) {
            target.getPreference().setProfile(target);
        }
        target.setLat(source.getLat());
        target.setLon(source.getLon());
        return target;
    }

}
