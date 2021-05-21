package com.venus.profile.controller;

import com.venus.profile.annotation.ApiPageable;
import com.venus.profile.model.dto.ProfileDto;
import com.venus.profile.service.ProfileService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/profile")
public class ProfileController {

    private ProfileService service;

    public ProfileController(ProfileService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ProfileDto findProfile(@PathVariable UUID id) {
        return service.findOne(id);
    }

    @PostMapping("/{id}")
    public ProfileDto updateProfile(@RequestBody @Valid ProfileDto profileDto) {
        return service.saveProfile(profileDto);
    }

    @PostMapping
    public ProfileDto saveProfile(@Valid @RequestBody ProfileDto profileDto) {
        return service.saveProfile(profileDto);
    }

    @GetMapping
    @ApiPageable
    public List<ProfileDto> findProfile(Pageable page) {
        return service.findAll(page);
    }

    @PostMapping("{id}/image")
    public void uploadImage(@PathVariable UUID id, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        service.uploadImage(id, multipartFile);
    }
}
