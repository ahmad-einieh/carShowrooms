package com.project.carshowrooms.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "car")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 25)
    private String vin;
    
    @Column(nullable = false, length = 25)
    private String maker;
    
    @Column(nullable = false, length = 25)
    private String model;
    
    @Column(name = "model_year", nullable = false)
    private Integer modelYear;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showroom_id", nullable = false)
    @JsonIgnore
    private Showroom showroom;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}