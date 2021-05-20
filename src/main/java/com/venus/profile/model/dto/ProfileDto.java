package com.venus.profile.model.dto;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public class ProfileDto {
    private UUID id;
    private String name;
    private int age;
    private int gender;
    private List<String> photos;
    private ZonedDateTime birthday;
    private PreferenceDto preference;
    private double lat;
    private double lon;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public ZonedDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(ZonedDateTime birthday) {
        this.birthday = birthday;
    }

    public PreferenceDto getPreference() {
        return preference;
    }

    public void setPreference(PreferenceDto preference) {
        this.preference = preference;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
