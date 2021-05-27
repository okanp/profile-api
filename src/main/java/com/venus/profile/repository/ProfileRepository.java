package com.venus.profile.repository;

import com.venus.profile.domain.entity.Profile;
import com.venus.profile.domain.enums.Gender;
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
            " p.gender = :#{#targetGender.name} AND pr.gender = :#{#gender.name} " +
            " AND point(:lat, :lon) <@> point (p.lat, p.lon) <= least(:searchDistance, pr.search_distance) " +
            " AND p.ID != :profileId " +
            " AND p.birthday >= :minBirthday and p.birthday <= :maxBirthday " +
            " AND pr.min_age <= :age and pr.max_age >= :age " +
            " AND p.ID NOT IN (   SELECT c1.profile_a as profile_id" +
            "                           FROM candidate c1 " +
            "                           WHERE c1.profile_a = :profileId OR c1.profile_a_response = 'NONE' " +
            "                                   " +
            "                     UNION ALL" +
            "                     " +
            "                           SELECT c2.profile_b as profile_id" +
            "                           FROM candidate c2 " +
            "                           WHERE c2.profile_b = :profileId OR c2.profile_b_response = 'NONE' " +
            "                  )" +
            " ORDER by p.last_active_time DESC " +
            " LIMIT 10000" +
            " ;";

    Profile findOneById(UUID id);

    @Override
    Page<Profile> findAll(Pageable page);

    List<Profile> findAllByLastCandidateSearchTimeBeforeOrLastCandidateSearchTimeIsNull(ZonedDateTime time);

    @Query(value = candidateQuery, nativeQuery = true)
    List<Profile> findAllCandidatesByConditions(UUID profileId, Gender targetGender, Gender gender, double lat, double lon, int searchDistance, ZonedDateTime minBirthday, ZonedDateTime maxBirthday, int age);

    void deleteOneById(UUID id);
}
