package com.venus.profile.task;

import com.venus.profile.model.dto.ProfileDto;
import com.venus.profile.service.CandidateCalculatorService;
import com.venus.profile.service.CandidateService;
import com.venus.profile.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;

@Component
@Profile("pro")
public class CandidateTask {

    private Logger logger = LoggerFactory.getLogger(CandidateTask.class);

    private ProfileService profileService;

    private CandidateService candidateService;

    private CandidateCalculatorService candidateCalculatorService;

    public CandidateTask(ProfileService profileService, CandidateCalculatorService candidateCalculatorService, CandidateService candidateService) {
        this.profileService = profileService;
        this.candidateCalculatorService = candidateCalculatorService;
        this.candidateService = candidateService;
    }

    /*
     * TODO
     * Bu scheduled job ileride profile idlerini bir queue'ya basacak
     * farkli instancelar alip isleyebilecek. uzun is olabilir scalability icin sart
     * simdilik idleri alip servisi cagiriyor sadece
     *
     * su an her dakika calisiyor ve pro profile ile uygulama baslatrsa calisiyor
     */
    @Scheduled(cron = "0 */5 * * * ?")
    public void findCandidates() {
        logger.info("findCandidates job started");
        ZonedDateTime time = ZonedDateTime.now().minusHours(24);
        List<ProfileDto> profiles = profileService.findAllByLastCandidateSearchTimeBefore(time);
        profiles.forEach(x -> candidateCalculatorService.trigger(x.getId()));
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void deleteOldCandidates() {
        ZonedDateTime time = ZonedDateTime.now().minusHours(48);
        candidateService.deleteAllByCreatedAtBefore(time);
    }
}
