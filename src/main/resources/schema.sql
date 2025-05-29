-- Create Employee Table
CREATE TABLE employee (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    active BOOLEAN DEFAULT TRUE
);

-- Create Address Table
CREATE TABLE address (
    id BIGSERIAL PRIMARY KEY,
    street VARCHAR(255),
    city VARCHAR(255),
    state VARCHAR(255),
    zip_code VARCHAR(20),
    employee_id BIGINT REFERENCES employee (id) ON DELETE CASCADE
);


INSERT INTO employee (name, email, active)
VALUES
('John Doe', 'john.doe@example.com', true),
('Jane Smith', 'jane.smith@example.com', true),
('Michael Johnson', 'michael.johnson@example.com', true),
('Emily Davis', 'emily.davis@example.com', false); -- This employee is inactive


INSERT INTO address (street, city, state, zip_code, employee_id)
VALUES
('123 Main St', 'Mumbai', 'Maharashtra', '400001', 1), -- John Doe's address
('456 Elm St', 'Pune', 'Maharashtra', '411001', 1),    -- John Doe's second address
('789 Oak St', 'Delhi', 'Delhi', '110001', 1),         -- John Doe's third address

('101 Maple St', 'Bangalore', 'Karnataka', '560001', 2), -- Jane Smith's address
('202 Pine St', 'Hyderabad', 'Telangana', '500001', 2), -- Jane Smith's second address

('303 Cedar St', 'Chennai', 'Tamil Nadu', '600001', 3); -- Michael Johnson's address
