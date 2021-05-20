package com.venus.profile.repository;

import com.venus.profile.model.entity.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.UUID;

@Repository
public interface CandidateRepository extends PagingAndSortingRepository<Candidate, Long> {
    Page<Candidate> findAllByProfileA_IdOrProfileB_Id(UUID id, UUID id2, Pageable page);

    void deleteAllByProfileA_IdAndProfileAResponseAndProfileBResponse(UUID profileAId, int profileAResponse, int profileBResponse);

    void deleteAllByCreatedAtBefore(ZonedDateTime time);

    Candidate findOneById(UUID id);

    @Query(value = "SELECT * FROM CANDIDATE WHERE (profile_a = :id or profile_b = :id ) and status = :status", nativeQuery = true)
    Page<Candidate> findAllByProfileAorBAndStatus(String id, int status, Pageable page);
}