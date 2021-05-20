package com.venus.profile.controller;

import com.venus.profile.service.CandidateCalculatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/candidate-calculator")
public class CandidateCalculatorController {

    private CandidateCalculatorService candidateCalculatorService;

    public CandidateCalculatorController(CandidateCalculatorService candidateCalculatorService) {
        this.candidateCalculatorService = candidateCalculatorService;
    }

    @GetMapping("/profile/{profileId}/trigger")
    void triggerCandidateCalculationForProfile(@PathVariable UUID profileId) {
        candidateCalculatorService.trigger(profileId);
    }
}
