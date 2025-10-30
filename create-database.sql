-- Create the database
CREATE DATABASE
IF NOT EXISTS e_commerce 
    CHARACTER
SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Use it
USE e_commerce;

-- Show that it was created
SELECT 'Database e_commerce created successfully!' AS Status;
