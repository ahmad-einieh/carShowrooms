package com.project.carshowrooms.model;

import jakarta.persistence.*;   
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "showroom")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Showroom {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(name = "commercial_registration_number", nullable = false, length = 10, unique = true)
    private String commercialRegistrationNumber;
    
    @Column(name = "manager_name", length = 100)
    private String managerName;
    
    @Column(name = "contact_number", nullable = false, length = 15)
    private String contactNumber;
    
    @Column(length = 255)
    private String address;
    
    @Column(nullable = false)
    private boolean deleted = false;
    
    @OneToMany(mappedBy = "showroom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Car> cars = new ArrayList<>();
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public void addCar(Car car) {
        cars.add(car);
        car.setShowroom(this);
    }
    
    public void removeCar(Car car) {
        cars.remove(car);
        car.setShowroom(null);
    }
}