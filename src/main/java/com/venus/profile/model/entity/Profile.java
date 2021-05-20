package com.venus.profile.model.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "birthday")
    private ZonedDateTime birthday;
    @Column(name = "gender")
    private int gender;
    @Column(name = "last_candidate_search_time")
    private ZonedDateTime lastCandidateSearchTime;
    @Column(name = "lat")
    private Double lat;
    @Column(name = "lon")
    private Double lon;
    @Column(name = "last_active_time")
    private ZonedDateTime lastActiveTime = ZonedDateTime.now();
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "photos")
    private List<String> photos;
    @OneToOne(mappedBy = "profile", orphanRemoval = true, cascade = CascadeType.ALL)
    private Preference preference;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(ZonedDateTime birthday) {
        this.birthday = birthday;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public ZonedDateTime getLastCandidateSearchTime() {
        return lastCandidateSearchTime;
    }

    public void setLastCandidateSearchTime(ZonedDateTime lastCandidateSearchTime) {
        this.lastCandidateSearchTime = lastCandidateSearchTime;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public ZonedDateTime getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(ZonedDateTime lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public Preference getPreference() {
        return preference;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
        if (this.preference != null) {
            preference.setProfile(this);
        }
    }
}
