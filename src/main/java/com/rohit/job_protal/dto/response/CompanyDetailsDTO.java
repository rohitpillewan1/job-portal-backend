package com.rohit.job_protal.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class CompanyDetailsDTO {
    @NotBlank
    private String name;

    private String description;

    @NotBlank
    private String websiteUrl;

    private String logoUrl;
}
