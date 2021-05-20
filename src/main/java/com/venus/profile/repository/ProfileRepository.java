package com.venus.profile.repository;

import com.venus.profile.model.entity.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProfileRepository extends PagingAndSortingRepository<Profile, Long> {

    String candidateQuery = "SELECT * FROM profile p LEFT JOIN preference pr ON p.id = pr.profile_id WHERE " +
            " p.gender = :targetGender AND pr.gender = :gender " +
            " AND lat < :maxLat AND lat > :minLat " +
            " AND lon < :maxLon AND lon > :minLon " +
            " AND p.ID != :profileId " +
            " AND p.birthday >= :minBirthday and p.birthday <= :maxBirthday " +
            " AND pr.min_age <= :age and pr.max_age >= :age " +
            " AND p.ID NOT IN (SELECT (CASE WHEN c1.profile_a = :profileId THEN c1.profile_a ELSE c1.profile_b END) as profile_id" +
            "                           FROM candidate c1 " +
            "                           WHERE (c1.profile_a = :profileId OR c1.profile_b = :profileId) " +
            "                           AND (c1.profile_a_response = -1 OR c1.profile_a_response = -1)" +
            "                  )" +
            " ORDER by p.last_active_time DESC " +
            " LIMIT :limit " +
            " ;";

    Profile findOneById(UUID id);
    Page<Profile> findAll(Pageable page);
    List<Profile> findAllByLastCandidateSearchTimeBeforeOrLastCandidateSearchTimeIsNull(ZonedDateTime time);

    @Query(value = candidateQuery, nativeQuery = true)
    List<Profile> findAllCandidatesByConditions(String profileId, int targetGender, int gender, double minLat, double maxLat, double minLon, double maxLon, ZonedDateTime minBirthday, ZonedDateTime maxBirthday, int age, int limit);

    void deleteOneById(UUID id);
}
