package com.venus.profile.domain.dto;

import com.venus.profile.domain.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ProfileDto {
    private UUID id;
    @NotNull(message = "name cannot be null")
    @Length(min = 2, max = 32, message = "name length must be between 2 and 32")
    private String name;
    private int age;
    @NotNull(message = "gender cannot ne null")
    private Gender gender;
    private List<String> photos;
    @NotNull(message = "birthday cannot be null")
    @Past(message = "birthday must be in the past")
    private ZonedDateTime birthday;
    private PreferenceDto preference;
    @NotNull(message = "lat cannot be null")
    private double lat;
    @NotNull(message = "lon cannot be null")
    private double lon;
}
