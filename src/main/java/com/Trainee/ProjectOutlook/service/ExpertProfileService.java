package com.Trainee.ProjectOutlook.service;

import com.Trainee.ProjectOutlook.model.ExpertProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Service
public class ExpertProfileService {

    @Autowired
    private RestTemplate restTemplate;

    private final String PROFILE_SERVICE_URL = "http://profiles-service/api/profiles/";

    public ExpertProfile getExpertProfile(Long expertId) {
        String url = UriComponentsBuilder.fromHttpUrl(PROFILE_SERVICE_URL)
                .queryParam("id", expertId)
                .toUriString();
        ExpertProfile expert = restTemplate.getForObject(url, ExpertProfile.class);
        return expert;
    }

    public List<ExpertProfile> getExpertsBySpecialization(String specialization) {
        String url = UriComponentsBuilder.fromHttpUrl(PROFILE_SERVICE_URL)
                .queryParam("specialization", specialization)
                .toUriString();
        ExpertProfile[] expertsArray = restTemplate.getForObject(url, ExpertProfile[].class);
        return Arrays.asList(expertsArray);
    }
}

