package com.project.carshowrooms.controller;

import com.project.carshowrooms.dto.CarDTO;
import com.project.carshowrooms.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CarController {
    
    private final CarService carService;
    
    @PostMapping("/api/showrooms/{showroomId}/cars")
    public ResponseEntity<CarDTO.Response> createCar(
            @PathVariable Long showroomId,
            @Valid @RequestBody CarDTO.CreateRequest request) {
        return new ResponseEntity<>(carService.createCar(showroomId, request), HttpStatus.CREATED);
    }
    
    @GetMapping("/api/showrooms/{showroomId}/cars")
    public ResponseEntity<Page<CarDTO.Response>> getCarsByShowroomId(
            @PathVariable Long showroomId,
            Pageable pageable) {
        return ResponseEntity.ok(carService.getCarsByShowroomId(showroomId, pageable));
    }
    
    @GetMapping("/api/cars")
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
        return ResponseEntity.ok(carService.getAllCars(
                vin, maker, model, modelYear, minPrice, maxPrice, showroomName, contactNumber, pageable));
    }
    
    @GetMapping("/api/cars/{id}")
    public ResponseEntity<CarDTO.Response> getCarById(@PathVariable Long id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }
}