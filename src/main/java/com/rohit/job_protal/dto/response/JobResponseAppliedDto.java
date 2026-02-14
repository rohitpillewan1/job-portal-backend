package com.rohit.job_protal.dto.response;

import lombok.Data;

@Data
public class JobResponseAppliedDto {
    private Long id;
    private String title;
    private String location;
    private Boolean hasApplied;
}
