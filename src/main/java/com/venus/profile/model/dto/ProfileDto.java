package com.venus.profile.model.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public class ProfileDto {
    private UUID id;
    @NotNull(message = "name cannot be null")
    @Length(min = 2, max = 32, message = "name length must be between 2 and 32")
    private String name;
    private int age;
    @NotNull(message = "gender cannot ne null")
    @Min(value = 1, message = "gender must be [1,2]")
    @Max(value = 2, message = "gender must be [1,2]")
    private int gender;
    private List<String> photos;
    @NotNull(message = "birthday cannot be null")
    @Past(message = "birthday must be in the past")
    private ZonedDateTime birthday;
    private PreferenceDto preference;
    @NotNull(message = "lat cannot be null")
    private double lat;
    @NotNull(message = "lon cannot be null")
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
