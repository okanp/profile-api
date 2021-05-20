package com.venus.profile.repository;

import com.venus.profile.model.entity.Candidate;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferenceRepository extends PagingAndSortingRepository<Candidate, Long> {

}
