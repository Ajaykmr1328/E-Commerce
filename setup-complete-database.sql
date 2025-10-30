-- ================================================
-- Complete Database Setup Script
-- This will create the database, tables, and sample data
-- Run this in phpMyAdmin or MySQL command line
-- ================================================

-- Create and use database
CREATE DATABASE
IF NOT EXISTS e_commerce CHARACTER
SET utf8mb4
COLLATE utf8mb4_unicode_ci;
USE e_commerce;

-- ================================================
-- Drop existing tables (in reverse dependency order)
-- ================================================
DROP TABLE IF EXISTS cart_items;
DROP TABLE IF EXISTS carts;
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS product_images;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS addresses;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;

-- ================================================
-- Create Tables
-- ================================================

-- Users table
CREATE TABLE users
(
    id BIGINT
    AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR
    (255) NOT NULL UNIQUE,
    password VARCHAR
    (255) NOT NULL,
    first_name VARCHAR
    (100) NOT NULL,
    last_name VARCHAR
    (100) NOT NULL,
    phone_number VARCHAR
    (20) NOT NULL,
    role VARCHAR
    (20) NOT NULL DEFAULT 'CUSTOMER',
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_email
    (email),
    INDEX idx_role
    (role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

    -- Categories table
    CREATE TABLE categories
    (
        id BIGINT
        AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR
        (100) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_name
        (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

        -- Addresses table
        CREATE TABLE addresses
        (
            id BIGINT
            AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    street_address VARCHAR
            (255) NOT NULL,
    city VARCHAR
            (100) NOT NULL,
    state VARCHAR
            (100) NOT NULL,
    country VARCHAR
            (100) NOT NULL,
    zip_code VARCHAR
            (20) NOT NULL,
    address_type VARCHAR
            (20),
    is_default BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY
            (user_id) REFERENCES users
            (id) ON
            DELETE CASCADE,
    INDEX idx_user_id (user_id)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

            -- Products table
            CREATE TABLE products
            (
                id BIGINT
                AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR
                (255) NOT NULL,
    description TEXT,
    price DECIMAL
                (10, 2) NOT NULL,
    stock_quantity INT NOT NULL DEFAULT 0,
    category_id BIGINT NOT NULL,
    seller_id BIGINT NOT NULL,
    average_rating DECIMAL
                (3, 2) DEFAULT 0.00,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY
                (category_id) REFERENCES categories
                (id) ON
                DELETE CASCADE,
    FOREIGN KEY (seller_id)
                REFERENCES users
                (id) ON
                DELETE CASCADE,
    INDEX idx_category_id (category_id),
    INDEX idx_seller_id
                (seller_id),
    INDEX idx_active
                (active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

                -- Product Images table
                CREATE TABLE product_images
                (
                    id BIGINT
                    AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    image_url VARCHAR
                    (500) NOT NULL,
    is_primary BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY
                    (product_id) REFERENCES products
                    (id) ON
                    DELETE CASCADE,
    INDEX idx_product_id (product_id)
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

                    -- Reviews table
                    CREATE TABLE reviews
                    (
                        id BIGINT
                        AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    rating INT NOT NULL CHECK
                        (rating BETWEEN 1 AND 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY
                        (product_id) REFERENCES products
                        (id) ON
                        DELETE CASCADE,
    FOREIGN KEY (user_id)
                        REFERENCES users
                        (id) ON
                        DELETE CASCADE,
    INDEX idx_product_id (product_id),
    INDEX idx_user_id
                        (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

                        -- Orders table
                        CREATE TABLE orders
                        (
                            id BIGINT
                            AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL
                            (10, 2) NOT NULL,
    status VARCHAR
                            (50) NOT NULL DEFAULT 'PENDING',
    shipping_address_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY
                            (user_id) REFERENCES users
                            (id) ON
                            DELETE CASCADE,
    FOREIGN KEY (shipping_address_id)
                            REFERENCES addresses
                            (id),
    INDEX idx_user_id
                            (user_id),
    INDEX idx_status
                            (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

                            -- Order Items table
                            CREATE TABLE order_items
                            (
                                id BIGINT
                                AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL
                                (10, 2) NOT NULL,
    FOREIGN KEY
                                (order_id) REFERENCES orders
                                (id) ON
                                DELETE CASCADE,
    FOREIGN KEY (product_id)
                                REFERENCES products
                                (id) ON
                                DELETE CASCADE,
    INDEX idx_order_id (order_id),
    INDEX idx_product_id
                                (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

                                -- Payments table
                                CREATE TABLE payments
                                (
                                    id BIGINT
                                    AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    payment_method VARCHAR
                                    (50) NOT NULL,
    payment_status VARCHAR
                                    (50) NOT NULL DEFAULT 'PENDING',
    amount DECIMAL
                                    (10, 2) NOT NULL,
    transaction_id VARCHAR
                                    (255),
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY
                                    (order_id) REFERENCES orders
                                    (id) ON
                                    DELETE CASCADE,
    INDEX idx_order_id (order_id)
                                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

                                    -- Carts table
                                    CREATE TABLE carts
                                    (
                                        id BIGINT
                                        AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY
                                        (user_id) REFERENCES users
                                        (id) ON
                                        DELETE CASCADE,
    INDEX idx_user_id (user_id)
                                        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

                                        -- Cart Items table
                                        CREATE TABLE cart_items
                                        (
                                            id BIGINT
                                            AUTO_INCREMENT PRIMARY KEY,
    cart_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY
                                            (cart_id) REFERENCES carts
                                            (id) ON
                                            DELETE CASCADE,
    FOREIGN KEY (product_id)
                                            REFERENCES products
                                            (id) ON
                                            DELETE CASCADE,
    INDEX idx_cart_id (cart_id),
    INDEX idx_product_id
                                            (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

                                            -- ================================================
                                            -- Insert Sample Data
                                            -- ================================================

                                            -- Insert sample users (with {noop} prefix for passwords)
                                            INSERT INTO users
                                                (email, password, first_name, last_name, phone_number, role, enabled)
                                            VALUES
                                                ('admin@ecommerce.com', '{noop}admin123', 'Admin', 'User', '+1234567890', 'ADMIN', TRUE),
                                                ('customer@example.com', '{noop}customer123', 'John', 'Doe', '+1234567891', 'CUSTOMER', TRUE),
                                                ('seller1@example.com', '{noop}seller123', 'Alice', 'Smith', '+1234567892', 'SELLER', TRUE),
                                                ('seller2@example.com', '{noop}seller123', 'Bob', 'Johnson', '+1234567893', 'SELLER', TRUE),
                                                ('customer2@example.com', '{noop}customer123', 'Jane', 'Williams', '+1234567894', 'CUSTOMER', TRUE),
                                                ('customer3@example.com', '{noop}customer123', 'Michael', 'Brown', '+1234567895', 'CUSTOMER', TRUE);

                                            -- Insert categories
                                            INSERT INTO categories
                                                (name, description)
                                            VALUES
                                                ('Electronics', 'Electronic devices and accessories'),
                                                ('Clothing', 'Apparel and fashion items'),
                                                ('Books', 'Books and educational materials'),
                                                ('Home & Kitchen', 'Home appliances and kitchen items'),
                                                ('Sports', 'Sports equipment and accessories');

                                            -- Insert addresses
                                            INSERT INTO addresses
                                                (user_id, street_address, city, state, country, zip_code, address_type, is_default)
                                            VALUES
                                                (2, '123 Main Street', 'New York', 'NY', 'USA', '10001', 'SHIPPING', TRUE),
                                                (2, '456 Billing Ave', 'New York', 'NY', 'USA', '10002', 'BILLING', FALSE),
                                                (5, '789 Oak Road', 'Los Angeles', 'CA', 'USA', '90001', 'SHIPPING', TRUE),
                                                (5, '789 Oak Road', 'Los Angeles', 'CA', 'USA', '90001', 'BILLING', TRUE),
                                                (6, '321 Pine Street', 'Chicago', 'IL', 'USA', '60601', 'SHIPPING', TRUE);

                                            -- Insert products (Electronics - seller_id: 3)
                                            INSERT INTO products
                                                (name, description, price, stock_quantity, category_id, seller_id, average_rating, active)
                                            VALUES
                                                ('Wireless Bluetooth Headphones', 'Premium over-ear headphones with noise cancellation and 30-hour battery life', 79.99, 50, 1, 3, 4.5, TRUE),
                                                ('Smart Watch Pro', 'Fitness tracker with heart rate monitor, GPS, and waterproof design', 199.99, 30, 1, 3, 4.7, TRUE),
                                                ('USB-C Fast Charger', '65W fast charging adapter compatible with laptops and smartphones', 29.99, 100, 1, 3, 4.3, TRUE),
                                                ('Portable Bluetooth Speaker', 'Compact waterproof speaker with 12-hour battery and premium sound', 49.99, 75, 1, 3, 4.6, TRUE),
                                                ('4K Webcam', 'HD webcam with auto-focus and built-in microphone for video calls', 89.99, 40, 1, 3, 4.4, TRUE);

                                            -- Insert products (Clothing - seller_id: 4)
                                            INSERT INTO products
                                                (name, description, price, stock_quantity, category_id, seller_id, average_rating, active)
                                            VALUES
                                                ('Classic Cotton T-Shirt', 'Comfortable 100% cotton t-shirt available in multiple colors', 19.99, 200, 2, 4, 4.2, TRUE),
                                                ('Denim Jeans', 'Classic fit denim jeans with stretch fabric', 59.99, 80, 2, 4, 4.5, TRUE),
                                                ('Hooded Sweatshirt', 'Soft fleece hoodie with kangaroo pocket', 39.99, 120, 2, 4, 4.6, TRUE),
                                                ('Running Shoes', 'Lightweight athletic shoes with breathable mesh upper', 89.99, 60, 2, 4, 4.7, TRUE),
                                                ('Winter Jacket', 'Insulated waterproof jacket for cold weather', 129.99, 45, 2, 4, 4.8, TRUE);

                                            -- Insert products (Books - seller_id: 3)
                                            INSERT INTO products
                                                (name, description, price, stock_quantity, category_id, seller_id, average_rating, active)
                                            VALUES
                                                ('Clean Code: A Handbook', 'Robert C. Martin - Essential reading for software developers', 39.99, 100, 3, 3, 4.9, TRUE),
                                                ('The Pragmatic Programmer', 'Classic guide to software craftsmanship and best practices', 44.99, 75, 3, 3, 4.8, TRUE),
                                                ('Design Patterns', 'Gang of Four - Elements of reusable object-oriented software', 49.99, 50, 3, 3, 4.7, TRUE),
                                                ('Introduction to Algorithms', 'Comprehensive guide to algorithms and data structures', 79.99, 40, 3, 3, 4.9, TRUE);

                                            -- Insert products (Home & Kitchen - seller_id: 4)
                                            INSERT INTO products
                                                (name, description, price, stock_quantity, category_id, seller_id, average_rating, active)
                                            VALUES
                                                ('Stainless Steel Cookware Set', '10-piece professional cookware set with non-stick coating', 199.99, 25, 4, 4, 4.6, TRUE),
                                                ('Coffee Maker', 'Programmable drip coffee maker with thermal carafe', 79.99, 35, 4, 4, 4.4, TRUE),
                                                ('Blender Pro', 'High-speed blender with 1200W motor and multiple settings', 129.99, 30, 4, 4, 4.7, TRUE),
                                                ('Knife Set', '15-piece professional chef knife set with wooden block', 89.99, 40, 4, 4, 4.5, TRUE);

                                            -- Insert products (Sports - seller_id: 3)
                                            INSERT INTO products
                                                (name, description, price, stock_quantity, category_id, seller_id, average_rating, active)
                                            VALUES
                                                ('Yoga Mat', 'Extra thick exercise mat with carrying strap', 29.99, 80, 5, 3, 4.5, TRUE),
                                                ('Adjustable Dumbbells', 'Set of 2 adjustable dumbbells (5-25 lbs each)', 149.99, 35, 5, 3, 4.7, TRUE),
                                                ('Resistance Bands Set', '5-piece resistance band set for home workouts', 24.99, 100, 5, 3, 4.3, TRUE),
                                                ('Basketball', 'Official size composite leather basketball', 39.99, 60, 5, 3, 4.6, TRUE),
                                                ('Water Bottle', 'Insulated stainless steel water bottle - 32 oz', 19.99, 150, 5, 3, 4.4, TRUE);

                                            -- Insert product images
                                            INSERT INTO product_images
                                                (product_id, image_url, is_primary)
                                            VALUES
                                                (1, '/images/products/headphones-1.jpg', TRUE),
                                                (2, '/images/products/smartwatch-1.jpg', TRUE),
                                                (3, '/images/products/charger-1.jpg', TRUE),
                                                (4, '/images/products/speaker-1.jpg', TRUE),
                                                (5, '/images/products/webcam-1.jpg', TRUE),
                                                (6, '/images/products/tshirt-1.jpg', TRUE),
                                                (7, '/images/products/jeans-1.jpg', TRUE),
                                                (8, '/images/products/hoodie-1.jpg', TRUE),
                                                (9, '/images/products/shoes-1.jpg', TRUE),
                                                (10, '/images/products/jacket-1.jpg', TRUE);

                                            -- Success message
                                            SELECT 'Database setup completed successfully!' AS Status,
                                                (SELECT COUNT(*)
                                                FROM users) AS Users,
                                                (SELECT COUNT(*)
                                                FROM categories) AS Categories,
                                                (SELECT COUNT(*)
                                                FROM products) AS Products,
                                                (SELECT COUNT(*)
                                                FROM addresses) AS Addresses;
