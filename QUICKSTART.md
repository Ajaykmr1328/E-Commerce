# Quick Start - XAMPP Setup

## For XAMPP Users (You!)

Since you're using XAMPP, here's the fastest way to set up your database:

### 1️⃣ Start MySQL in XAMPP
- Open **XAMPP Control Panel**
- Click **Start** next to MySQL

### 2️⃣ Run Setup Script
```cmd
setup-database-xampp.bat
```
- When asked for password, just press **Enter** (XAMPP default has no password)

### 3️⃣ Load Sample Data (Optional but Recommended)
```cmd
load-sample-data.bat
```
- Adds 23 products, 6 users, reviews, orders, and more
- Great for testing the application

### 4️⃣ Verify (Optional)
- Open phpMyAdmin: http://localhost/phpmyadmin
- Check that `e_commerce` database exists with 11 tables

### 5️⃣ Run Your Application
```cmd
mvnw.cmd spring-boot:run
```

**That's it!** Your application will connect to XAMPP's MySQL automatically.

---

## Connection Info
- Host: localhost:3306
- Database: e_commerce  
- User: ecommerce_user
- Password: changeme

## Sample Login Credentials

**After loading sample data, you'll have:**

- **Admin**: admin@ecommerce.com / admin123
- **Customers**: 
  - customer@example.com / customer123
  - customer2@example.com / customer123
  - customer3@example.com / customer123
- **Sellers**: 
  - seller1@example.com / admin123
  - seller2@example.com / admin123

## Files Created for You

✅ **setup-database-xampp.bat** - Quick setup script for XAMPP
✅ **src/main/resources/schema.sql** - Database schema (can import via phpMyAdmin)
✅ **XAMPP_SETUP.md** - Detailed instructions
✅ **application.properties** - Already configured for MySQL

## Troubleshooting

**"mysql is not recognized"**
- Use phpMyAdmin import method instead
- Or add MySQL to PATH: `C:\xampp\mysql\bin`

**Connection refused**
- Make sure MySQL is running in XAMPP Control Panel

---

See **XAMPP_SETUP.md** for detailed instructions and troubleshooting.
