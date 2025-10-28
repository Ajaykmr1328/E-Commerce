Static assets for admin pages

Files added:

- js/utils.js - common utilities
- js/api.js - simple fetch wrapper for backend calls
- js/notifications.js - bootstrap toast helper
- js/admin.js - admin page logic for analytics/products/orders/users/categories
- js/admin.js - admin page logic for analytics/products/orders/users/categories
- js/auth.js - client-side admin login helper (posts to /admin/login)
- css/admin.css - admin-specific styles
- css/components.css - reusable component styles
- css/responsive.css - responsive tweaks

Notes:

- Templates include Bootstrap CDN for quick styling. If you prefer local Bootstrap or Tailwind, install and adjust the templates.
- API endpoints used in `js/api.js` are placeholder paths under `/api` and `/admin/*`. Implement controllers to match.
- API endpoints used in `js/api.js` are placeholder paths under `/api` and `/admin/*`. Implement controllers to match.
- `admin-login.html` is provided as a front-end login page. Server-side authentication (POST /admin/login) must be implemented separately.
