package com.Trainee.ProjectOutlook.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
