package com.project.carshowrooms.repository;

import com.project.carshowrooms.model.Showroom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShowroomRepository extends JpaRepository<Showroom, Long> {
    
    Page<Showroom> findByDeletedFalse(Pageable pageable);
    
    Optional<Showroom> findByIdAndDeletedFalse(Long id);
    
    boolean existsByCommercialRegistrationNumber(String commercialRegistrationNumber);
    
    Optional<Showroom> findByCommercialRegistrationNumberAndDeletedFalse(String commercialRegistrationNumber);
    
    Page<Showroom> findByUserUsernameAndDeletedFalse(String username, Pageable pageable);
    
    Optional<Showroom> findByIdAndUserUsernameAndDeletedFalse(Long id, String username);
}