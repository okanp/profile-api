package com.venus.profile.controller;

import com.venus.profile.model.dto.CandidateDto;
import com.venus.profile.service.CandidateService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/candidate")
public class CandidateController {

    private CandidateService service;

    public CandidateController(CandidateService service) {
        this.service = service;
    }

    @DeleteMapping
    void delete() {
        service.deleteAll();
    }

    @GetMapping
    public List<CandidateDto> findAll(Pageable page) {
        return service.findAll(page);
    }

    @PostMapping("/{id}/profile/{profileId}/swipe")
    public void swipe(@PathVariable UUID id, @PathVariable UUID profileId, @RequestParam int response) {
        service.swipe(id, profileId, response);
    }

    @GetMapping("/profile/{profileId}")
    public List<CandidateDto> findCandidates(@PathVariable UUID profileId, @RequestParam(defaultValue = "0") int status, Pageable page) {
        return service.findAllCandidatesByProfileId(profileId, status, page);
    }
}
