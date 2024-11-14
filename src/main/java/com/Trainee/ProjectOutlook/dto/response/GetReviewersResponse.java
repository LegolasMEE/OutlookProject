package com.Trainee.ProjectOutlook.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class GetReviewersResponse {
    private String name;

    private Long expertId;

    private String specialization;

    public GetReviewersResponse(String name, Long expertId, String specialization) {
        this.name = name;
        this.expertId = expertId;
        this.specialization = specialization;
    }
}
