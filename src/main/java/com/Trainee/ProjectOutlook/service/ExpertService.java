package com.Trainee.ProjectOutlook.service;

import com.Trainee.ProjectOutlook.dto.response.GetReviewersResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    @Value("${external.service.url}")
    private String externalUrl;

    public ResponseEntity<List<GetReviewersResponse>> getExperts(String specialization) {
        String url = UriComponentsBuilder.fromHttpUrl(externalUrl)
            .queryParamIfPresent("position", Optional.ofNullable(specialization))
            .toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-TOKEN", "3GXJiyB2SmiGf0O7j-U0luHjm-mrEFU6DB-D86rWRopwalYwzEEhaCLjiE4OOOFd");
        System.out.println(url);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String responseBody = response.getBody();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            List<GetReviewersResponse> resultList = new ArrayList<>();
            for (JsonNode expertNode : jsonNode) {
                GetReviewersResponse result = new GetReviewersResponse();
                result.setExpertId(expertNode.get("id").asLong());
                result.setName(expertNode.get("name").asText());
                result.setSpecialization(expertNode.path("positionResponse").path("name").asText());
                resultList.add(result);
            }
            return new ResponseEntity<>(resultList, HttpStatus.OK);
        } catch (Exception e) {
            throw new EntityNotFoundException("No users found");
        }
    }
}

