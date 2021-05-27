package com.venus.profile.repository;

import com.venus.profile.domain.entity.Candidate;
import com.venus.profile.domain.enums.CandidateResponse;
import com.venus.profile.domain.enums.CandidateStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.UUID;

@Repository
public interface CandidateRepository extends PagingAndSortingRepository<Candidate, Long> {
    Page<Candidate> findAllByProfileA_IdOrProfileB_Id(UUID id, UUID id2, Pageable page);

    void deleteAllByProfileA_IdAndProfileAResponseAndProfileBResponse(UUID profileAId, CandidateResponse profileAResponse, CandidateResponse profileBResponse);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM CANDIDATE where (profile_a = :id or profile_b = :id) and (profile_a_response = :#{#response.name} and profile_b_response = :#{#response.name})", nativeQuery = true)
    void deleteAllByProfileAOrProfileBAndBothProfileResponse(UUID id, CandidateResponse response);

    void deleteAllByCreatedAtBefore(ZonedDateTime time);

    Candidate findOneById(UUID id);

    @Query(value = "SELECT * FROM CANDIDATE WHERE (profile_a = :id or profile_b = :id) and status = :#{#status.name}", nativeQuery = true)
    Page<Candidate> findAllByProfileAorBAndStatus(UUID id, CandidateStatus status, Pageable page);

    @Query(value = "SELECT * FROM CANDIDATE WHERE ((profile_a = :id and profile_a_response = :#{#response}) or (profile_b = :id and profile_b_response = :#{#response.name})) and status = :#{#status}", nativeQuery = true)
    Page<Candidate> findAllCandidatesByProfileIdAndResponseAndStatus(String id, CandidateResponse response, CandidateStatus status, Pageable page);
}