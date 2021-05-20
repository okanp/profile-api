package com.venus.profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.venus.profile.model.dto.CandidateDto;
import com.venus.profile.model.dto.PreferenceDto;
import com.venus.profile.model.dto.ProfileDto;
import com.venus.profile.model.entity.Candidate;
import com.venus.profile.model.entity.Preference;
import com.venus.profile.model.entity.Profile;
import com.venus.profile.model.mapper.ProfileMapper;
import com.venus.profile.repository.CandidateRepository;
import com.venus.profile.repository.PreferenceRepository;
import com.venus.profile.repository.ProfileRepository;
import com.venus.profile.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.ZonedDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class ProfileApplicationIT {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    ProfileMapper profileMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private PreferenceRepository preferenceRepository;

    @BeforeEach
    void setup() {
        candidateRepository.deleteAll();
        profileRepository.deleteAll();
    }

    @Test
    void should_save_profile() throws Exception {
        ProfileDto pd1 = new ProfileDto();
        pd1.setName("okan");
        pd1.setGender(Constants.MALE);
        pd1.setBirthday(ZonedDateTime.parse("2021-05-30T11:15:30+02:00[Europe/Amsterdam]"));

        assertThat(profileRepository.count(), is(0L));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pd1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());

        List<Profile> profiles = profileRepository.findAll(Pageable.unpaged()).toList();
        assertThat(profiles.size(), is(1));

        Profile profile = profiles.get(0);
        assertThat(profile.getName(), equalTo(pd1.getName()));
        assertThat(profile.getGender(), is(pd1.getGender()));
        assertThat(profile.getBirthday(), equalTo(pd1.getBirthday()));
        assertThat(profile.getId(), notNullValue());
    }

    @Test
    void should_find_the_profile() throws Exception {
        Profile p1 = new Profile();
        p1.setName("okan");
        p1.setGender(Constants.MALE);
        p1.setBirthday(ZonedDateTime.parse("1989-07-22T11:15:30+02:00[Europe/Amsterdam]"));
        profileRepository.save(p1);

        Profile p2 = new Profile();
        p2.setName("udyr");
        p2.setGender(Constants.MALE);
        p2.setBirthday(ZonedDateTime.parse("1988-05-28T08:19:20+02:00[Europe/Amsterdam]"));
        Preference preference = new Preference();
        preference.setMinAge(35);
        p2.setPreference(preference);
        profileRepository.save(p2);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/profile/" + p2.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());

        MvcResult result = resultActions.andReturn();
        ProfileDto pd = objectMapper.readValue(result.getResponse().getContentAsString(), ProfileDto.class);

        assertThat(pd.getName(), equalTo(p2.getName()));
        assertThat(pd.getGender(), is(p2.getGender()));
        assertThat(pd.getId(), equalTo(p2.getId()));
    }

    @Test
    void should_update_the_profile() throws Exception {
        Profile p1 = new Profile();
        p1.setName("okan");
        p1.setGender(Constants.MALE);
        p1.setBirthday(ZonedDateTime.parse("1989-07-22T11:15:30+02:00[Europe/Amsterdam]"));
        p1.setPhotos(List.of("1.jpg"));
        profileRepository.save(p1);

        ProfileDto pd1 = profileMapper.toProfileDto(p1);
        pd1.setName("okan2");
        pd1.setPhotos(List.of("2.jpg"));
        PreferenceDto preference = new PreferenceDto();
        preference.setMaxAge(33);
        pd1.setPreference(preference);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/profile/" + pd1.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pd1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());


        MvcResult result = resultActions.andReturn();
        ProfileDto pd = objectMapper.readValue(result.getResponse().getContentAsString(), ProfileDto.class);

        profileRepository.findOneById(pd1.getId());
        assertThat(pd.getName(), equalTo(pd1.getName()));
        assertThat(pd.getId(), equalTo(pd1.getId()));
        assertThat(pd.getPreference().getMaxAge(), equalTo(pd1.getPreference().getMaxAge()));
        assertThat(pd.getPreference().getMinAge(), equalTo(pd1.getPreference().getMinAge()));
        assertThat(pd.getPreference().getGender(), equalTo(pd1.getPreference().getGender()));
        assertThat(pd.getPhotos().get(0), equalTo(pd1.getPhotos().get(0)));
    }

    @Test
    void should_calculate_candidates_for_the_profile() throws Exception {
        Profile p1 = new Profile();
        p1.setName("okan");
        p1.setGender(Constants.MALE);
        p1.setBirthday(ZonedDateTime.parse("1989-07-22T11:15:30+02:00[Europe/Amsterdam]"));
        p1.setLat(39.92077); // Ankara
        p1.setLon(32.85411);
        p1.setPreference(new Preference());
        p1.getPreference().setGender(Constants.FEMALE);
        profileRepository.save(p1);

        Profile p2 = new Profile();
        p2.setName("janna");
        p2.setGender(Constants.FEMALE);
        p2.setBirthday(ZonedDateTime.parse("1989-07-22T11:15:30+02:00[Europe/Amsterdam]"));
        p2.setLat(39.92077); // Ankara
        p2.setLon(32.85411);
        p2.setPreference(new Preference());
        p2.getPreference().setGender(Constants.MALE);
        profileRepository.save(p2);

        Profile p3 = new Profile();
        p3.setName("tasha");
        p3.setGender(Constants.FEMALE);
        p3.setBirthday(ZonedDateTime.parse("1989-07-22T11:15:30+02:00[Europe/Amsterdam]"));
        p2.setLat(39.951662); // Ankara
        p2.setLon(32.851926);
        p3.setPreference(new Preference());
        p3.getPreference().setGender(Constants.FEMALE);
        profileRepository.save(p3);

        assertThat(candidateRepository.count(), is(0L));
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/candidate-calculator/profile/" + p1.getId().toString() + "/trigger"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());

        List<Candidate> candidates = candidateRepository.findAllByProfileA_IdOrProfileB_Id(p1.getId(), p1.getId(), Pageable.unpaged()).toList();

        assertThat(candidates.size(), is(1));
    }

    @Test
    public void should_upload_picture() throws Exception {
        Profile p1 = new Profile();
        p1.setName("okan");
        p1.setGender(Constants.MALE);
        p1.setBirthday(ZonedDateTime.parse("1989-07-22T11:15:30+02:00[Europe/Amsterdam]"));
        p1.setLat(39.92077); // Ankara
        p1.setLon(32.85411);
        profileRepository.save(p1);

        MockMultipartFile file = new MockMultipartFile(
                "image",
                "hello.png",
                MediaType.IMAGE_JPEG_VALUE,
                "Hello, World!".getBytes()
        );

        mockMvc.perform(multipart("/v1/profile/" + p1.getId().toString() + "/image").file(file))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Profile profile = profileRepository.findOneById(p1.getId());
        assertThat(profile.getPhotos().size(), is(1));
    }

    @Test
    void should_find_candidates_for_profile() throws Exception {
        Profile p1 = new Profile();
        p1.setName("okan");
        p1.setGender(Constants.MALE);
        p1.setBirthday(ZonedDateTime.parse("1989-07-22T11:15:30+02:00[Europe/Amsterdam]"));
        p1.setLat(39.92077); // Ankara
        p1.setLon(32.85411);
        p1.setPreference(new Preference());
        p1.getPreference().setGender(Constants.FEMALE);
        profileRepository.save(p1);

        Profile p2 = new Profile();
        p2.setName("janna");
        p2.setGender(Constants.FEMALE);
        p2.setBirthday(ZonedDateTime.parse("1989-07-22T11:15:30+02:00[Europe/Amsterdam]"));
        p2.setLat(39.92077); // Ankara
        p2.setLon(32.85411);
        p2.setPreference(new Preference());
        p2.getPreference().setGender(Constants.MALE);
        profileRepository.save(p2);

        Profile p3 = new Profile();
        p3.setName("tasha");
        p3.setGender(Constants.FEMALE);
        p3.setBirthday(ZonedDateTime.parse("1989-07-22T11:15:30+02:00[Europe/Amsterdam]"));
        p2.setLat(39.951662); // Ankara
        p2.setLon(32.851926);
        p3.setPreference(new Preference());
        p3.getPreference().setGender(Constants.FEMALE);
        profileRepository.save(p3);

        Candidate candidate = new Candidate();
        candidate.setProfileA(p1);
        candidate.setProfileB(p2);
        candidateRepository.save(candidate);

        Candidate candidate2 = new Candidate();
        candidate.setProfileA(p3);
        candidate.setProfileB(p2);
        candidateRepository.save(candidate2);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/candidate/profile/" + p1.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());

        MvcResult result = resultActions.andReturn();
        CandidateDto[] candidates = objectMapper.readValue(result.getResponse().getContentAsString(), CandidateDto[].class);
        assertThat(candidates.length, is(1));
        assertThat(candidates[0].getProfileA().getName(), equalTo(p2.getName()));
    }

    @Test
    void should_find_candidates_for_profile_by_status() throws Exception {
        Profile p1 = new Profile();
        p1.setName("okan");
        p1.setGender(Constants.MALE);
        p1.setBirthday(ZonedDateTime.parse("1989-07-22T11:15:30+02:00[Europe/Amsterdam]"));
        p1.setLat(39.92077); // Ankara
        p1.setLon(32.85411);
        p1.setPreference(new Preference());
        p1.getPreference().setGender(Constants.FEMALE);
        profileRepository.save(p1);

        Profile p2 = new Profile();
        p2.setName("janna");
        p2.setGender(Constants.FEMALE);
        p2.setBirthday(ZonedDateTime.parse("1989-07-22T11:15:30+02:00[Europe/Amsterdam]"));
        p2.setLat(39.92077); // Ankara
        p2.setLon(32.85411);
        p2.setPreference(new Preference());
        p2.getPreference().setGender(Constants.MALE);
        profileRepository.save(p2);

        Profile p3 = new Profile();
        p3.setName("tasha");
        p3.setGender(Constants.FEMALE);
        p3.setBirthday(ZonedDateTime.parse("1989-07-22T11:15:30+02:00[Europe/Amsterdam]"));
        p2.setLat(39.951662); // Ankara
        p2.setLon(32.851926);
        p3.setPreference(new Preference());
        p3.getPreference().setGender(Constants.FEMALE);
        profileRepository.save(p3);

        Candidate candidate = new Candidate();
        candidate.setProfileA(p1);
        candidate.setProfileB(p2);
        candidateRepository.save(candidate);

        Candidate candidate2 = new Candidate();
        candidate2.setProfileA(p3);
        candidate2.setProfileB(p2);
        candidate2.setStatus(Constants.NOMATCH);
        candidateRepository.save(candidate2);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/candidate/profile/" + p2.getId().toString()).param("status", String.valueOf(Constants.NOMATCH)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());

        MvcResult result = resultActions.andReturn();
        CandidateDto[] candidates = objectMapper.readValue(result.getResponse().getContentAsString(), CandidateDto[].class);
        assertThat(candidates.length, is(1));
        assertThat(candidates[0].getProfileA().getName(), equalTo(p3.getName()));
    }

    @Test
    void should_swipe_the_candidate() throws Exception {
        Profile p1 = new Profile();
        p1.setName("okan");
        p1.setGender(Constants.MALE);
        p1.setBirthday(ZonedDateTime.parse("1989-07-22T11:15:30+02:00[Europe/Amsterdam]"));
        p1.setLat(39.92077); // Ankara
        p1.setLon(32.85411);
        p1.setPreference(new Preference());
        p1.getPreference().setGender(Constants.FEMALE);
        profileRepository.save(p1);

        Profile p2 = new Profile();
        p2.setName("janna");
        p2.setGender(Constants.FEMALE);
        p2.setBirthday(ZonedDateTime.parse("1989-07-22T11:15:30+02:00[Europe/Amsterdam]"));
        p2.setLat(39.92077); // Ankara
        p2.setLon(32.85411);
        p2.setPreference(new Preference());
        p2.getPreference().setGender(Constants.MALE);
        profileRepository.save(p2);

        Candidate candidate = new Candidate();
        candidate.setProfileA(p1);
        candidate.setProfileB(p2);
        candidateRepository.save(candidate);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/candidate/" + candidate.getId() + "/profile/" + p1.getId().toString() + "/swipe").param("response", String.valueOf(Constants.DISLIKE)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();

        Candidate candidate1 = candidateRepository.findOneById(candidate.getId());
        assertThat(Constants.NOMATCH, is(candidate1.getStatus()));
        assertThat(Constants.DISLIKE, is(candidate1.getProfileAResponse()));
        assertThat(candidate1.getProfileAResponseTime(), notNullValue());
    }

    @Test
    void should_swipe_the_candidate_to_match() throws Exception {
        Profile p1 = new Profile();
        p1.setName("okan");
        p1.setGender(Constants.MALE);
        p1.setBirthday(ZonedDateTime.parse("1989-07-22T11:15:30+02:00[Europe/Amsterdam]"));
        p1.setLat(39.92077); // Ankara
        p1.setLon(32.85411);
        p1.setPreference(new Preference());
        p1.getPreference().setGender(Constants.FEMALE);
        profileRepository.save(p1);

        Profile p2 = new Profile();
        p2.setName("janna");
        p2.setGender(Constants.FEMALE);
        p2.setBirthday(ZonedDateTime.parse("1989-07-22T11:15:30+02:00[Europe/Amsterdam]"));
        p2.setLat(39.92077); // Ankara
        p2.setLon(32.85411);
        p2.setPreference(new Preference());
        p2.getPreference().setGender(Constants.MALE);
        profileRepository.save(p2);

        Candidate candidate = new Candidate();
        candidate.setProfileA(p1);
        candidate.setProfileB(p2);
        candidate.setProfileAResponse(Constants.LIKE);
        candidateRepository.save(candidate);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/candidate/" + candidate.getId() + "/profile/" + p2.getId().toString() + "/swipe").param("response", String.valueOf(Constants.SUPERLIKE)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();

        Candidate candidate1 = candidateRepository.findOneById(candidate.getId());
        assertThat(Constants.MATCH, is(candidate1.getStatus()));
        assertThat(Constants.LIKE, is(candidate1.getProfileAResponse()));
        assertThat(candidate1.getProfileBResponseTime(), notNullValue());
    }

}
