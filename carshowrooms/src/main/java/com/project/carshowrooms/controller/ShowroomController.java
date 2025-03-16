package com.project.carshowrooms.controller;

import com.project.carshowrooms.dto.ShowroomDTO;
import com.project.carshowrooms.service.ShowroomService;
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
@RequestMapping("/api/showrooms")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Showrooms", description = "Showroom management API")
@SecurityRequirement(name = "Bearer Authentication")
public class ShowroomController {
    
    private final ShowroomService showroomService;
    
    @PostMapping
    @Operation(summary = "Create a new showroom")
    public ResponseEntity<ShowroomDTO.DetailedResponse> createShowroom(
            @Valid @RequestBody ShowroomDTO.CreateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        return new ResponseEntity<>(showroomService.createShowroom(request, username), HttpStatus.CREATED);
    }
    
    @GetMapping
    @Operation(summary = "Get all showrooms")
    public ResponseEntity<Page<ShowroomDTO.Response>> getAllShowrooms(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        return ResponseEntity.ok(showroomService.getAllShowrooms(pageable, username));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get showroom by ID")
    public ResponseEntity<ShowroomDTO.DetailedResponse> getShowroomById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        return ResponseEntity.ok(showroomService.getShowroomById(id, username));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update showroom")
    public ResponseEntity<ShowroomDTO.DetailedResponse> updateShowroom(
            @PathVariable Long id, 
            @Valid @RequestBody ShowroomDTO.UpdateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        return ResponseEntity.ok(showroomService.updateShowroom(id, request, username));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete showroom")
    public ResponseEntity<Void> deleteShowroom(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        showroomService.deleteShowroom(id, username);
        return ResponseEntity.noContent().build();
    }
}