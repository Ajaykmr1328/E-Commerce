-- ================================================
-- Insert Sample Data with Correct Password Format
-- ================================================

USE e_commerce;

-- ================================================
-- Insert Users with {noop} password prefix
-- ================================================

-- Admin user (password: admin123)
INSERT INTO users
    (email, password, first_name, last_name, phone_number, role, enabled)
VALUES
    ('admin@ecommerce.com', '{noop}admin123', 'Admin', 'User', '+1234567890', 'ADMIN', TRUE);

-- Customer users (password: customer123)
INSERT INTO users
    (email, password, first_name, last_name, phone_number, role, enabled)
VALUES
    ('customer@example.com', '{noop}customer123', 'John', 'Doe', '+1234567891', 'CUSTOMER', TRUE),
    ('customer2@example.com', '{noop}customer123', 'Jane', 'Williams', '+1234567894', 'CUSTOMER', TRUE),
    ('customer3@example.com', '{noop}customer123', 'Michael', 'Brown', '+1234567895', 'CUSTOMER', TRUE);

-- Seller users (password: seller123)
INSERT INTO users
    (email, password, first_name, last_name, phone_number, role, enabled)
VALUES
    ('seller1@example.com', '{noop}seller123', 'Alice', 'Smith', '+1234567892', 'SELLER', TRUE),
    ('seller2@example.com', '{noop}seller123', 'Bob', 'Johnson', '+1234567893', 'SELLER', TRUE);

-- ================================================
-- Insert Categories
-- ================================================

INSERT INTO categories
    (name, description)
VALUES
    ('Electronics', 'Electronic devices and accessories'),
    ('Clothing', 'Fashion and apparel'),
    ('Books', 'Books and reading materials'),
    ('Home & Kitchen', 'Home appliances and kitchen items'),
    ('Sports', 'Sports equipment and accessories');

-- ================================================
-- Insert Products
-- Note: seller_id 3 = Alice Smith, seller_id 4 = Bob Johnson
-- ================================================

-- Electronics (category_id: 1, seller_id: 3 - Alice Smith)
INSERT INTO products
    (name, description, price, stock_quantity, category_id, seller_id, average_rating, active)
VALUES
    ('Wireless Bluetooth Headphones', 'Premium over-ear headphones with noise cancellation and 30-hour battery life', 79.99, 50, 1, 3, 4.5, TRUE),
    ('Smart Watch Pro', 'Fitness tracker with heart rate monitor, GPS, and waterproof design', 199.99, 30, 1, 3, 4.7, TRUE),
    ('USB-C Fast Charger', '65W fast charging adapter compatible with laptops and smartphones', 29.99, 100, 1, 3, 4.3, TRUE),
    ('Portable Bluetooth Speaker', 'Compact waterproof speaker with 12-hour battery and premium sound', 49.99, 75, 1, 3, 4.6, TRUE),
    ('4K Webcam', 'HD webcam with auto-focus and built-in microphone for video calls', 89.99, 40, 1, 3, 4.4, TRUE);

-- Clothing (category_id: 2, seller_id: 4 - Bob Johnson)
INSERT INTO products
    (name, description, price, stock_quantity, category_id, seller_id, average_rating, active)
VALUES
    ('Classic Cotton T-Shirt', 'Comfortable 100% cotton t-shirt available in multiple colors', 19.99, 200, 2, 4, 4.2, TRUE),
    ('Denim Jeans', 'Classic fit denim jeans with stretch fabric', 59.99, 80, 2, 4, 4.5, TRUE),
    ('Hooded Sweatshirt', 'Soft fleece hoodie with kangaroo pocket', 39.99, 120, 2, 4, 4.6, TRUE),
    ('Running Shoes', 'Lightweight athletic shoes with breathable mesh upper', 89.99, 60, 2, 4, 4.7, TRUE),
    ('Winter Jacket', 'Insulated waterproof jacket for cold weather', 129.99, 45, 2, 4, 4.8, TRUE);

-- Books (category_id: 3, seller_id: 3 - Alice Smith)
INSERT INTO products
    (name, description, price, stock_quantity, category_id, seller_id, average_rating, active)
VALUES
    ('Clean Code: A Handbook', 'Robert C. Martin - Essential reading for software developers', 39.99, 100, 3, 3, 4.9, TRUE),
    ('The Pragmatic Programmer', 'Classic guide to software craftsmanship and best practices', 44.99, 75, 3, 3, 4.8, TRUE),
    ('Design Patterns', 'Gang of Four - Elements of reusable object-oriented software', 49.99, 50, 3, 3, 4.7, TRUE),
    ('Introduction to Algorithms', 'Comprehensive guide to algorithms and data structures', 79.99, 40, 3, 3, 4.9, TRUE);

-- Home & Kitchen (category_id: 4, seller_id: 4 - Bob Johnson)
INSERT INTO products
    (name, description, price, stock_quantity, category_id, seller_id, average_rating, active)
VALUES
    ('Stainless Steel Cookware Set', '10-piece professional cookware set with non-stick coating', 199.99, 25, 4, 4, 4.6, TRUE),
    ('Coffee Maker', 'Programmable drip coffee maker with thermal carafe', 79.99, 35, 4, 4, 4.4, TRUE),
    ('Blender Pro', 'High-speed blender with 1200W motor and multiple settings', 129.99, 30, 4, 4, 4.7, TRUE),
    ('Knife Set', '15-piece professional chef knife set with wooden block', 89.99, 40, 4, 4, 4.5, TRUE);

-- Sports (category_id: 5, seller_id: 3 - Alice Smith)
INSERT INTO products
    (name, description, price, stock_quantity, category_id, seller_id, average_rating, active)
VALUES
    ('Yoga Mat', 'Extra thick exercise mat with carrying strap', 29.99, 80, 5, 3, 4.5, TRUE),
    ('Adjustable Dumbbells', 'Set of 2 adjustable dumbbells (5-25 lbs each)', 149.99, 35, 5, 3, 4.7, TRUE),
    ('Resistance Bands Set', '5-piece resistance band set for home workouts', 24.99, 100, 5, 3, 4.3, TRUE),
    ('Basketball', 'Official size composite leather basketball', 39.99, 60, 5, 3, 4.6, TRUE),
    ('Water Bottle', 'Insulated stainless steel water bottle - 32 oz', 19.99, 150, 5, 3, 4.4, TRUE);

-- ================================================
-- Verify the data was inserted
-- ================================================

SELECT 'Data inserted successfully!' AS Status;
SELECT COUNT(*) AS user_count
FROM users;
SELECT COUNT(*) AS category_count
FROM categories;
SELECT COUNT(*) AS product_count
FROM products;
