# Demo Login Credentials

## Admin Account
**Email:** `admin@ecommerce.com`  
**Password:** `admin123`  
**Role:** ADMIN  
**Redirects to:** `/admin/dashboard` after login

## Customer Accounts
**Email:** `customer@example.com`  
**Password:** `customer123`  
**Role:** CUSTOMER  
**Redirects to:** `/` (home page) after login

---

**Email:** `customer2@example.com`  
**Password:** `customer123`  
**Role:** CUSTOMER  
**Redirects to:** `/` (home page) after login

---

**Email:** `customer3@example.com`  
**Password:** `customer123`  
**Role:** CUSTOMER  
**Redirects to:** `/` (home page) after login

---

## Quick Test
1. Start the app: `.\mvnw.cmd spring-boot:run`
2. Navigate to: http://localhost:8080/auth/login
3. Login with admin credentials above
4. Should redirect to admin dashboard

## Notes
- All passwords use `{noop}` encoding for demo purposes (stored as plain text in DB)
- For production, switch to BCrypt by changing the password encoder
- The role-based redirect is handled by `RoleBasedAuthenticationSuccessHandler`
