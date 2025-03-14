package com.project.carshowrooms.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

public class CarDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRequest {
        @NotBlank(message = "VIN is required")
        @Size(max = 25, message = "VIN must be less than 25 characters")
        private String vin;
        
        @NotBlank(message = "Maker is required")
        @Size(max = 25, message = "Maker must be less than 25 characters")
        private String maker;
        
        @NotBlank(message = "Model is required")
        @Size(max = 25, message = "Model must be less than 25 characters")
        private String model;
        
        @NotNull(message = "Model year is required")
        @Min(value = 1900, message = "Model year must be at least 1900")
        @Max(value = 9999, message = "Model year must be at most 9999")
        private Integer modelYear;
        
        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
        private BigDecimal price;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String vin;
        private String maker;
        private String model;
        private Integer modelYear;
        private BigDecimal price;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailedResponse {
        private Long id;
        private String vin;
        private String maker;
        private String model;
        private Integer modelYear;
        private BigDecimal price;
        private String carShowroomName;
        private String contactNumber;
    }
}