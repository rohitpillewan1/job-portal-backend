package com.rohit.job_protal.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rohit.job_protal.enums.Gender;
import lombok.Data;

@JsonPropertyOrder({
        "id",
        "gender",
        "phone",
        "city",
        "state",
        "country",
        "totalExperience"
})
@Data
public class UserProfileResponseDto {
    private Long id;
    private Gender gender;
    private String phone;
    private String city;
    private String state;
    private String country;
    private Integer totalExperience;
}
