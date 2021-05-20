package com.venus.profile.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "profile")
public class ConfigProperties {
    private String imageDir;

    private String candidateSearchJobCron;

    public String getImageDir() {
        return imageDir;
    }

    public void setImageDir(String imageDir) {
        this.imageDir = imageDir;
    }

    public String getCandidateSearchJobCron() {
        return candidateSearchJobCron;
    }

    public void setCandidateSearchJobCron(String candidateSearchJobCron) {
        this.candidateSearchJobCron = candidateSearchJobCron;
    }
}
