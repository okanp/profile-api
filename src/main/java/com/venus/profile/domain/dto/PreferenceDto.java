package com.venus.profile.domain.dto;

import com.venus.profile.domain.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PreferenceDto {
    private int searchDistance = 10;
    private int maxAge = 70;
    private int minAge = 18;
    private Gender gender;
}
