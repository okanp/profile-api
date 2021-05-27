package com.venus.profile.domain.entity;

import com.venus.profile.domain.enums.Gender;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "preference")
public class Preference {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;
    @Column(name = "search_distance")
    private int searchDistance = 10;
    @Column(name = "max_age")
    private int maxAge = 70;
    @Column(name = "min_age")
    private int minAge = 18;
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public int getSearchDistance() {
        return searchDistance;
    }

    public void setSearchDistance(int searchDistance) {
        this.searchDistance = searchDistance;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
