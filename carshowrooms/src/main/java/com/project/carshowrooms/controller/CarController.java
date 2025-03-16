package com.project.carshowrooms.controller;

import com.project.carshowrooms.dto.CarDTO;
import com.project.carshowrooms.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Cars", description = "Car management API")
@SecurityRequirement(name = "Bearer Authentication")
public class CarController {
    
    private final CarService carService;
    
    @PostMapping("/api/showrooms/{showroomId}/cars")
    @Operation(summary = "Create a new car in a showroom")
    public ResponseEntity<CarDTO.Response> createCar(
            @PathVariable Long showroomId,
            @Valid @RequestBody CarDTO.CreateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        return new ResponseEntity<>(carService.createCar(showroomId, request, username), HttpStatus.CREATED);
    }
    
    @GetMapping("/api/showrooms/{showroomId}/cars")
    @Operation(summary = "Get all cars in a showroom")
    public ResponseEntity<Page<CarDTO.Response>> getCarsByShowroomId(
            @PathVariable Long showroomId,
            Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        return ResponseEntity.ok(carService.getCarsByShowroomId(showroomId, pageable, username));
    }
    
    @GetMapping("/api/cars")
    @Operation(summary = "Get all cars with filtering options")
    public ResponseEntity<Page<CarDTO.DetailedResponse>> getAllCars(
            @RequestParam(required = false) String vin,
            @RequestParam(required = false) String maker,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Integer modelYear,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String showroomName,
            @RequestParam(required = false) String contactNumber,
            Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        return ResponseEntity.ok(carService.getAllCars(
                vin, maker, model, modelYear, minPrice, maxPrice, showroomName, contactNumber, pageable, username));
    }
    
    @GetMapping("/api/cars/{id}")
    @Operation(summary = "Get car by ID")
    public ResponseEntity<CarDTO.Response> getCarById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        return ResponseEntity.ok(carService.getCarById(id, username));
    }
}