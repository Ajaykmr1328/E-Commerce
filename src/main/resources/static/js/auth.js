// Client-side auth helper for admin login (placeholder)
document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('adminLoginForm');
  if (!form) return;

  form.addEventListener('submit', async (e) => {
    e.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    try {
      const resp = await API.post('/admin/login', { username, password });
      // Expect server to respond with success and redirect url or token
      if (resp && resp.success) {
        Notifications.show('Login successful', 'Success');
        // If server returns redirect, use it; otherwise go to admin dashboard
        window.location.href = resp.redirect || '/admin/dashboard';
      } else {
        Notifications.show(resp.message || 'Login failed', 'Error');
      }
    } catch (err) {
      Notifications.show('Login request failed: ' + err.message, 'Error');
    }
  });
});
