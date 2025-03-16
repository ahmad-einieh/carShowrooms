CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    full_name VARCHAR(100),
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Add user_id to showroom table
ALTER TABLE showroom 
ADD COLUMN user_id BIGINT,
ADD CONSTRAINT fk_showroom_user FOREIGN KEY (user_id) REFERENCES users(id);

-- Add user_id to car table
ALTER TABLE car 
ADD COLUMN user_id BIGINT,
ADD CONSTRAINT fk_car_user FOREIGN KEY (user_id) REFERENCES users(id);
