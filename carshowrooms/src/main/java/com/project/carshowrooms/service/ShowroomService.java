package com.project.carshowrooms.service;

import com.project.carshowrooms.dto.ShowroomDTO;
import com.project.carshowrooms.exception.ResourceAlreadyExistsException;
import com.project.carshowrooms.exception.ResourceNotFoundException;
import com.project.carshowrooms.model.Showroom;
import com.project.carshowrooms.model.User;
import com.project.carshowrooms.repository.ShowroomRepository;
import com.project.carshowrooms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShowroomService {
    
    private final ShowroomRepository showroomRepository;
    private final UserRepository userRepository;
    
    @Transactional
    public ShowroomDTO.DetailedResponse createShowroom(ShowroomDTO.CreateRequest request, String username) {
        if (showroomRepository.existsByCommercialRegistrationNumber(request.getCommercialRegistrationNumber())) {
            throw new ResourceAlreadyExistsException("Showroom with registration number " + 
                request.getCommercialRegistrationNumber() + " already exists");
        }
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        
        Showroom showroom = new Showroom();
        showroom.setName(request.getName());
        showroom.setCommercialRegistrationNumber(request.getCommercialRegistrationNumber());
        showroom.setManagerName(request.getManagerName());
        showroom.setContactNumber(request.getContactNumber());
        showroom.setAddress(request.getAddress());
        showroom.setUser(user);
        
        Showroom savedShowroom = showroomRepository.save(showroom);
        return mapToDetailedResponse(savedShowroom);
    }
    
    @Transactional(readOnly = true)
    public Page<ShowroomDTO.Response> getAllShowrooms(Pageable pageable, String username) {
        Page<Showroom> showrooms;
        
        if (username != null && !username.isEmpty()) {
            showrooms = showroomRepository.findByUserUsernameAndDeletedFalse(username, pageable);
        } else {
            showrooms = showroomRepository.findByDeletedFalse(pageable);
        }
        
        return showrooms.map(this::mapToResponse);
    }
    
    @Transactional(readOnly = true)
    public ShowroomDTO.DetailedResponse getShowroomById(Long id, String username) {
        Showroom showroom;
        
        if (username != null && !username.isEmpty()) {
            showroom = showroomRepository.findByIdAndUserUsernameAndDeletedFalse(id, username)
                    .orElseThrow(() -> new ResourceNotFoundException("Showroom not found with id: " + id));
        } else {
            showroom = showroomRepository.findByIdAndDeletedFalse(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Showroom not found with id: " + id));
        }
        
        return mapToDetailedResponse(showroom);
    }
    
    @Transactional
    public ShowroomDTO.DetailedResponse updateShowroom(Long id, ShowroomDTO.UpdateRequest request, String username) {
        Showroom showroom;
        
        if (username != null && !username.isEmpty()) {
            showroom = showroomRepository.findByIdAndUserUsernameAndDeletedFalse(id, username)
                    .orElseThrow(() -> new ResourceNotFoundException("Showroom not found with id: " + id));
        } else {
            showroom = showroomRepository.findByIdAndDeletedFalse(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Showroom not found with id: " + id));
        }
        
        if (request.getManagerName() != null) {
            showroom.setManagerName(request.getManagerName());
        }
        
        if (request.getContactNumber() != null) {
            showroom.setContactNumber(request.getContactNumber());
        }
        
        if (request.getAddress() != null) {
            showroom.setAddress(request.getAddress());
        }
        
        Showroom updatedShowroom = showroomRepository.save(showroom);
        return mapToDetailedResponse(updatedShowroom);
    }
    
    @Transactional
    public void deleteShowroom(Long id, String username) {
        Showroom showroom;
        
        if (username != null && !username.isEmpty()) {
            showroom = showroomRepository.findByIdAndUserUsernameAndDeletedFalse(id, username)
                    .orElseThrow(() -> new ResourceNotFoundException("Showroom not found with id: " + id));
        } else {
            showroom = showroomRepository.findByIdAndDeletedFalse(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Showroom not found with id: " + id));
        }
        
        showroom.setDeleted(true);
        showroomRepository.save(showroom);
    }
    
    public Showroom findShowroomById(Long id, String username) {
        if (username != null && !username.isEmpty()) {
            return showroomRepository.findByIdAndUserUsernameAndDeletedFalse(id, username)
                    .orElseThrow(() -> new ResourceNotFoundException("Showroom not found with id: " + id));
        } else {
            return showroomRepository.findByIdAndDeletedFalse(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Showroom not found with id: " + id));
        }
    }
    
    private ShowroomDTO.Response mapToResponse(Showroom showroom) {
        return new ShowroomDTO.Response(
                showroom.getId(),
                showroom.getName(),
                showroom.getCommercialRegistrationNumber(),
                showroom.getContactNumber()
        );
    }
    
    private ShowroomDTO.DetailedResponse mapToDetailedResponse(Showroom showroom) {
        return new ShowroomDTO.DetailedResponse(
                showroom.getId(),
                showroom.getName(),
                showroom.getCommercialRegistrationNumber(),
                showroom.getManagerName(),
                showroom.getContactNumber(),
                showroom.getAddress()
        );
    }
}