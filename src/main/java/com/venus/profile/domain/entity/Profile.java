package com.venus.profile.domain.entity;

import com.venus.profile.domain.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity(name = "profile")
@Data
@NoArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "birthday")
    private ZonedDateTime birthday;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
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
    @Column(name = "elo_ranking")
    private Integer eloRanking = 800;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")

    public void setPreference(Preference preference) {
        this.preference = preference;
        if (this.preference != null) {
            preference.setProfile(this);
        }
    }
}
