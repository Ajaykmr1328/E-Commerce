# Database Setup Instructions

## Overview
This guide will help you set up the MySQL database for the e-commerce application.

## Option 1: Using XAMPP (Recommended for Windows)

**If you have XAMPP installed, please see [XAMPP_SETUP.md](XAMPP_SETUP.md) for quick setup instructions.**

Quick steps:
1. Start MySQL in XAMPP Control Panel
2. Run: `setup-database-xampp.bat`
3. Verify in phpMyAdmin: http://localhost/phpmyadmin

## Option 2: Using Docker Compose

### Prerequisites
- Docker and Docker Compose installed on your system

### Steps

1. **Start the MySQL database**
   ```bash
   docker-compose up -d
   ```

2. **Verify the database is running**
   ```bash
   docker-compose ps
   ```

3. **Access phpMyAdmin** (optional)
   - Open your browser and navigate to: `http://localhost:8080`
   - Login with:
     - Username: `ecommerce_user`
     - Password: `changeme`

4. **Stop the database**
   ```bash
   docker-compose down
   ```

5. **Stop and remove all data** (use with caution)
   ```bash
   docker-compose down -v
   ```

### Database Connection Details
- **Host**: `localhost`
- **Port**: `3306`
- **Database**: `e_commerce`
- **Username**: `ecommerce_user`
- **Password**: `changeme`

## Option 2: Manual MySQL Installation

### Prerequisites
- MySQL 8.0 or higher installed on your system

### Steps

1. **Start MySQL server**
   - Windows: Start the MySQL service from Services
   - Linux/Mac: `sudo systemctl start mysql` or `brew services start mysql`

2. **Login to MySQL as root**
   ```bash
   mysql -u root -p
   ```

3. **Run the schema file**
   ```sql
   SOURCE /path/to/e_commerce/src/main/resources/schema.sql;
   ```
   
   Or from command line:
   ```bash
   mysql -u root -p < src/main/resources/schema.sql
   ```

4. **Create the database user** (if not already created by schema.sql)
   ```sql
   CREATE USER IF NOT EXISTS 'ecommerce_user'@'localhost' IDENTIFIED BY 'changeme';
   GRANT ALL PRIVILEGES ON e_commerce.* TO 'ecommerce_user'@'localhost';
   FLUSH PRIVILEGES;
   ```

5. **Verify the setup**
   ```bash
   mysql -u ecommerce_user -p e_commerce
   ```
   
   Then run:
   ```sql
   SHOW TABLES;
   ```

## Application Configuration

The application is already configured in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/e_commerce?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8
spring.datasource.username=ecommerce_user
spring.datasource.password=changeme
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### Important Configuration Notes

- **`spring.jpa.hibernate.ddl-auto=update`**: This setting will automatically update the database schema based on your JPA entities. 
  - For **development**: Use `update` or `create-drop`
  - For **production**: Use `validate` or `none`

## Database Schema

The `schema.sql` file creates the following tables:

1. **users** - User accounts with roles (ADMIN, CUSTOMER, etc.)
2. **categories** - Product categories
3. **addresses** - User shipping and billing addresses
4. **products** - Product catalog
5. **product_images** - Product images
6. **reviews** - Product reviews and ratings
7. **carts** - User shopping carts
8. **cart_items** - Items in shopping carts
9. **orders** - Order information
10. **order_items** - Items in orders
11. **payments** - Payment transactions

## Sample Data

The schema includes sample data:
- **Admin User**: 
  - Email: `admin@ecommerce.com`
  - Password: `admin123`
  
- **Customer User**:
  - Email: `customer@example.com`
  - Password: `customer123`

- **5 Sample Categories**: Electronics, Clothing, Books, Home & Kitchen, Sports

## Troubleshooting

### Connection refused
- Ensure MySQL is running
- Check if port 3306 is available
- For Docker: Run `docker-compose logs mysql` to check logs

### Authentication failed
- Verify username and password in `application.properties`
- Check if user has proper privileges

### Table already exists
- If you need to recreate tables, the schema file includes `DROP TABLE IF EXISTS` statements
- Or manually drop the database: `DROP DATABASE e_commerce;`

### Hibernate validation errors
- Set `spring.jpa.hibernate.ddl-auto=none` to prevent Hibernate from modifying the schema
- The schema.sql file already creates all necessary tables

## Running the Application

1. **Start MySQL** (Docker Compose or local MySQL)
2. **Build and run the Spring Boot application**
   ```bash
   ./mvnw spring-boot:run
   ```
   Or on Windows:
   ```cmd
   mvnw.cmd spring-boot:run
   ```

The application will connect to the MySQL database and be ready to use!
