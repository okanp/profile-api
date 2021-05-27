package com.venus.profile.domain.entity;

import com.venus.profile.domain.enums.CandidateResponse;
import com.venus.profile.domain.enums.CandidateStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity(name = "candidate")
@Data
@NoArgsConstructor
public class Candidate {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_a", referencedColumnName = "id")
    private Profile profileA;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_b", referencedColumnName = "id")
    private Profile profileB;
    @Column(name = "profile_a_response", length = 32)
    @Enumerated(EnumType.STRING)
    private CandidateResponse profileAResponse = CandidateResponse.NONE;
    @Column(name = "profile_b_response", length = 32)
    @Enumerated(EnumType.STRING)
    private CandidateResponse profileBResponse = CandidateResponse.NONE;
    @Column(name = "profile_a_response_date")
    private ZonedDateTime profileAResponseTime;
    @Column(name = "profile_b_response_date")
    private ZonedDateTime profileBResponseTime;
    @Column(name = "created_at")
    private ZonedDateTime createdAt = ZonedDateTime.now();
    @Column(name = "status", length = 32)
    @Enumerated(EnumType.STRING)
    private CandidateStatus status = CandidateStatus.NONE;
    @Column(name = "soul_mate_score")
    private double soulMateScore = 100;
}
