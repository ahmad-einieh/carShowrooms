CREATE TABLE showroom (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    commercial_registration_number VARCHAR(10) NOT NULL UNIQUE,
    manager_name VARCHAR(100),
    contact_number VARCHAR(15) NOT NULL,
    address VARCHAR(255),
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE car (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    vin VARCHAR(25) NOT NULL,
    maker VARCHAR(25) NOT NULL,
    model VARCHAR(25) NOT NULL,
    model_year INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    showroom_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (showroom_id) REFERENCES showroom(id)
);

CREATE INDEX idx_car_showroom ON car(showroom_id);
CREATE INDEX idx_car_maker ON car(maker);
CREATE INDEX idx_car_model ON car(model);
CREATE INDEX idx_car_vin ON car(vin);