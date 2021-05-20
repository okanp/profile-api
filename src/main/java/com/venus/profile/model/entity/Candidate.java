package com.venus.profile.model.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity(name = "candidate")
public class Candidate {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    private UUID id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_a", referencedColumnName = "id")
    private Profile profileA;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_b", referencedColumnName = "id")
    private Profile profileB;
    @Column(name = "profile_a_response")
    private int profileAResponse = 0;
    @Column(name = "profile_b_response")
    private int profileBResponse = 0;
    @Column(name = "profile_a_response_date")
    private ZonedDateTime profileAResponseTime;
    @Column(name = "profile_b_response_date")
    private ZonedDateTime profileBResponseTime;
    @Column(name = "created_at")
    private ZonedDateTime createdAt = ZonedDateTime.now();
    @Column(name = "status")
    private int status = 0;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Profile getProfileA() {
        return profileA;
    }

    public void setProfileA(Profile profileA) {
        this.profileA = profileA;
    }

    public Profile getProfileB() {
        return profileB;
    }

    public void setProfileB(Profile profileB) {
        this.profileB = profileB;
    }

    public ZonedDateTime getProfileAResponseTime() {
        return profileAResponseTime;
    }

    public void setProfileAResponseTime(ZonedDateTime profileAResponseTime) {
        this.profileAResponseTime = profileAResponseTime;
    }

    public ZonedDateTime getProfileBResponseTime() {
        return profileBResponseTime;
    }

    public void setProfileBResponseTime(ZonedDateTime profileBResponseTime) {
        this.profileBResponseTime = profileBResponseTime;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getProfileAResponse() {
        return profileAResponse;
    }

    public void setProfileAResponse(int profileAResponse) {
        this.profileAResponse = profileAResponse;
    }

    public int getProfileBResponse() {
        return profileBResponse;
    }

    public void setProfileBResponse(int profileBResponse) {
        this.profileBResponse = profileBResponse;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
