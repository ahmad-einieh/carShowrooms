package com.project.carshowrooms.service;

import com.project.carshowrooms.dto.CarDTO;
import com.project.carshowrooms.exception.ResourceNotFoundException;
import com.project.carshowrooms.model.Car;
import com.project.carshowrooms.model.Showroom;
import com.project.carshowrooms.model.User;
import com.project.carshowrooms.repository.CarRepository;
import com.project.carshowrooms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.criteria.Join;

@Service
@RequiredArgsConstructor
public class CarService {
    
    private final CarRepository carRepository;
    private final ShowroomService showroomService;
    private final UserRepository userRepository;
    
    @Transactional
    public CarDTO.Response createCar(Long showroomId, CarDTO.CreateRequest request, String username) {
        Showroom showroom = showroomService.findShowroomById(showroomId, username);
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        
        Car car = new Car();
        car.setVin(request.getVin());
        car.setMaker(request.getMaker());
        car.setModel(request.getModel());
        car.setModelYear(request.getModelYear());
        car.setPrice(request.getPrice());
        car.setShowroom(showroom);
        car.setUser(user);
        
        Car savedCar = carRepository.save(car);
        return mapToResponse(savedCar);
    }
    
    @Transactional(readOnly = true)
    public Page<CarDTO.DetailedResponse> getAllCars(
            String vin, String maker, String model, Integer modelYear, 
            Double minPrice, Double maxPrice, String showroomName, String contactNumber,
            Pageable pageable, String username) {
        
        Specification<Car> spec = (root, query, cb) -> {
            Join<Car, Showroom> showroomJoin = root.join("showroom");
            
            var predicate = cb.equal(showroomJoin.get("deleted"), false);
            
            // Add user filter if username is provided
            if (username != null && !username.isEmpty()) {
                Join<Car, User> userJoin = root.join("user");
                predicate = cb.and(predicate, cb.equal(userJoin.get("username"), username));
            }
            
            if (vin != null) {
                predicate = cb.and(predicate, 
                    cb.like(cb.lower(root.get("vin")), "%" + vin.toLowerCase() + "%"));
            }
            
            if (maker != null) {
                predicate = cb.and(predicate, 
                    cb.like(cb.lower(root.get("maker")), "%" + maker.toLowerCase() + "%"));
            }
            
            if (model != null) {
                predicate = cb.and(predicate, 
                    cb.like(cb.lower(root.get("model")), "%" + model.toLowerCase() + "%"));
            }
            
            if (modelYear != null) {
                predicate = cb.and(predicate, cb.equal(root.get("modelYear"), modelYear));
            }
            
            if (minPrice != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("price"), minPrice));
            }
            
            if (maxPrice != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("price"), maxPrice));
            }
            
            if (showroomName != null) {
                predicate = cb.and(predicate, 
                    cb.like(cb.lower(showroomJoin.get("name")), "%" + showroomName.toLowerCase() + "%"));
            }
            
            if (contactNumber != null) {
                predicate = cb.and(predicate, 
                    cb.like(showroomJoin.get("contactNumber"), "%" + contactNumber + "%"));
            }
            
            return predicate;
        };
        
        return carRepository.findAll(spec, pageable)
                .map(this::mapToDetailedResponse);
    }
    
    @Transactional(readOnly = true)
    public Page<CarDTO.Response> getCarsByShowroomId(Long showroomId, Pageable pageable, String username) {
        // Verify showroom exists and belongs to user
        showroomService.findShowroomById(showroomId, username);
        
        Page<Car> cars;
        if (username != null && !username.isEmpty()) {
            cars = carRepository.findByShowroomIdAndUserUsername(showroomId, username, pageable);
        } else {
            cars = carRepository.findByShowroomId(showroomId, pageable);
        }
        
        return cars.map(this::mapToResponse);
    }
    
    @Transactional(readOnly = true)
    public CarDTO.Response getCarById(Long id, String username) {
        Car car = findCarById(id, username);
        return mapToResponse(car);
    }
    
    private Car findCarById(Long id, String username) {
        if (username != null && !username.isEmpty()) {
            return carRepository.findByIdAndUserUsername(id, username)
                    .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
        } else {
            return carRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
        }
    }
    
    private CarDTO.Response mapToResponse(Car car) {
        return new CarDTO.Response(
                car.getId(),
                car.getVin(),
                car.getMaker(),
                car.getModel(),
                car.getModelYear(),
                car.getPrice()
        );
    }
    
    private CarDTO.DetailedResponse mapToDetailedResponse(Car car) {
        return new CarDTO.DetailedResponse(
                car.getId(),
                car.getVin(),
                car.getMaker(),
                car.getModel(),
                car.getModelYear(),
                car.getPrice(),
                car.getShowroom().getName(),
                car.getShowroom().getContactNumber()
        );
    }
}