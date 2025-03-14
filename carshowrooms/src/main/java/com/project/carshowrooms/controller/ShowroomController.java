package com.project.carshowrooms.controller;

import com.project.carshowrooms.dto.ShowroomDTO;
import com.project.carshowrooms.service.ShowroomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/showrooms")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ShowroomController {
    
    private final ShowroomService showroomService;
    
    @PostMapping
    public ResponseEntity<ShowroomDTO.DetailedResponse> createShowroom(
            @Valid @RequestBody ShowroomDTO.CreateRequest request) {
        return new ResponseEntity<>(showroomService.createShowroom(request), HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<Page<ShowroomDTO.Response>> getAllShowrooms(Pageable pageable) {
        return ResponseEntity.ok(showroomService.getAllShowrooms(pageable));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ShowroomDTO.DetailedResponse> getShowroomById(@PathVariable Long id) {
        return ResponseEntity.ok(showroomService.getShowroomById(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ShowroomDTO.DetailedResponse> updateShowroom(
            @PathVariable Long id, 
            @Valid @RequestBody ShowroomDTO.UpdateRequest request) {
        return ResponseEntity.ok(showroomService.updateShowroom(id, request));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShowroom(@PathVariable Long id) {
        showroomService.deleteShowroom(id);
        return ResponseEntity.noContent().build();
    }
}