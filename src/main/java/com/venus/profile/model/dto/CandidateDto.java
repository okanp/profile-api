package com.venus.profile.model.dto;

import java.util.UUID;

public class CandidateDto {
    private UUID id;
    private ProfileDto profileA;
    private ProfileDto profileB;
    private int status;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ProfileDto getProfileA() {
        return profileA;
    }

    public void setProfileA(ProfileDto profileA) {
        this.profileA = profileA;
    }

    public ProfileDto getProfileB() {
        return profileB;
    }

    public void setProfileB(ProfileDto profileB) {
        this.profileB = profileB;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
