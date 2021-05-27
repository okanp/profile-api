package com.venus.profile.service;

import com.venus.profile.config.ConfigProperties;
import com.venus.profile.domain.dto.ProfileDto;
import com.venus.profile.domain.entity.Profile;
import com.venus.profile.domain.mapper.ProfileMapper;
import com.venus.profile.repository.ProfileRepository;
import com.venus.profile.util.FileUpload;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private Logger logger = LoggerFactory.getLogger(ProfileService.class);

    private final ProfileRepository repository;

    private final ConfigProperties properties;

    private final ProfileMapper mapper;

    public ProfileDto findOne(UUID id) {
        return mapper.toProfileDto(repository.findOneById(id));
    }

    public List<ProfileDto> findAll(Pageable page) {
        return repository.findAll(page).map(mapper::toProfileDto).toList();
    }

    public List<ProfileDto> findAllByLastCandidateSearchTimeBefore(ZonedDateTime time) {
        return repository.findAllByLastCandidateSearchTimeBeforeOrLastCandidateSearchTimeIsNull(time).stream()
                .map(mapper::toProfileDto)
                .collect(Collectors.toList());
    }

    @Transactional
    void updateLastCandidateSearchTime(UUID id) {
        Profile profile = repository.findOneById(id);
        profile.setLastCandidateSearchTime(ZonedDateTime.now());
    }

    @Transactional
    public ProfileDto saveProfile(ProfileDto profileDto) {
        Profile profile = Objects.isNull(profileDto.getId()) ? null : repository.findOneById(profileDto.getId());
        if (profile == null) {
            return mapper.toProfileDto(repository.save(mapper.toProfile(profileDto)));
        }
        Profile source = mapper.toProfile(profileDto);
        profile.setName(source.getName());
        profile.setGender(source.getGender());
        profile.setPhotos(source.getPhotos());
        profile.setBirthday(source.getBirthday());
        profile.setPreference(source.getPreference());
        profile.setLat(source.getLat());
        profile.setLon(source.getLon());
        return mapper.toProfileDto(repository.save(profile));
    }

    @Transactional
    public void uploadImage(UUID uuid, MultipartFile multipartFile) throws IOException {
        String fileName = uuid.toString() + "_" + Instant.now().getEpochSecond();
        FileUpload.saveFile(properties.getImageDir(), fileName, multipartFile);
        Profile profile = repository.findOneById(uuid);
        profile.getPhotos().add(fileName);
    }
}
