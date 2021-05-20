package com.venus.profile.model.dto;

public class PreferenceDto {
    private int searchDistance = 10;
    private int maxAge = 70;
    private int minAge = 18;
    private int gender;

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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
