package com.project.carshowrooms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ShowroomDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRequest {
        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must be less than 100 characters")
        private String name;
        
        @NotBlank(message = "Commercial registration number is required")
        @Pattern(regexp = "\\d{10}", message = "Commercial registration number must be exactly 10 digits")
        private String commercialRegistrationNumber;
        
        @Size(max = 100, message = "Manager name must be less than 100 characters")
        private String managerName;
        
        @NotBlank(message = "Contact number is required")
        @Pattern(regexp = "\\d{1,15}", message = "Contact number must be numeric and less than 15 digits")
        private String contactNumber;
        
        @Size(max = 255, message = "Address must be less than 255 characters")
        private String address;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateRequest {
        @Size(max = 100, message = "Manager name must be less than 100 characters")
        private String managerName;
        
        @Pattern(regexp = "\\d{1,15}", message = "Contact number must be numeric and less than 15 digits")
        private String contactNumber;
        
        @Size(max = 255, message = "Address must be less than 255 characters")
        private String address;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String name;
        private String commercialRegistrationNumber;
        private String contactNumber;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailedResponse {
        private Long id;
        private String name;
        private String commercialRegistrationNumber;
        private String managerName;
        private String contactNumber;
        private String address;
    }
}