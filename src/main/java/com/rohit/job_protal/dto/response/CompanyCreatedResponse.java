package com.rohit.job_protal.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@JsonPropertyOrder(
        {
                "id",
                "name",
                "description",
                "website_url",
                "logo_url",
                "createdAt"
        }
)
@Data
public class CompanyCreatedResponse {
    private long id;
    private String name;
    private String description;
    private String website_url;
    private String logo_url;
    private LocalDateTime createdAt;
}
