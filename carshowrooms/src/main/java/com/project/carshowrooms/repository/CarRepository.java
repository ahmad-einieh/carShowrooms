package com.project.carshowrooms.repository;

import com.project.carshowrooms.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {
    
    Page<Car> findByShowroomId(Long showroomId, Pageable pageable);
    
    @Query("SELECT c FROM Car c JOIN c.showroom s WHERE s.deleted = false")
    Page<Car> findAllWithActiveShowrooms(Pageable pageable);
}