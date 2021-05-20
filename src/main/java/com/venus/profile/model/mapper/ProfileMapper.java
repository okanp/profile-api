package com.venus.profile.model.mapper;

import com.venus.profile.model.dto.ProfileDto;
import com.venus.profile.model.entity.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileMapper {

    @Autowired
    private PreferenceMapper preferenceMapper;

    public List<ProfileDto> toProfileDto(List<Profile> profile) {
        return profile.stream().map(this::toProfileDto).collect(Collectors.toList());
    }

    public List<Profile> toProfile(List<ProfileDto> profile) {
        return profile.stream().map(this::toProfile).collect(Collectors.toList());
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
