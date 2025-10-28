// Basic admin interactions: fetch analytics, list orders/products/users/categories
document.addEventListener('DOMContentLoaded', () => {
  // Wire UI controls
  const refreshBtn = document.getElementById('refreshAnalytics');
  if (refreshBtn) refreshBtn.addEventListener('click', fetchAnalytics);

  // Page-specific init
  if (document.getElementById('recentOrdersBody')) fetchRecentOrders();
  if (document.getElementById('ordersBody')) fetchOrders();
  if (document.getElementById('productsList')) fetchProducts();
  if (document.getElementById('usersBody')) fetchUsers();
  if (document.getElementById('categoriesBody')) fetchCategories();
});

async function fetchAnalytics() {
  try {
    const data = await API.get('/admin/analytics');
    document.getElementById('totalSales').textContent = EComUtils.formatCurrency(data.totalSales || 0);
    document.getElementById('totalOrders').textContent = data.orders || 0;
    document.getElementById('totalCustomers').textContent = data.customers || 0;
    document.getElementById('totalProducts').textContent = data.products || 0;
  } catch (err) {
    Notifications.show('Failed to load analytics: ' + err.message, 'Error', 'error');
  }
}

async function fetchRecentOrders() {
  try {
    const orders = await API.get('/admin/orders/recent');
    const body = document.getElementById('recentOrdersBody');
    body.innerHTML = '';
    if (!orders || orders.length === 0) {
      body.innerHTML = '<tr><td colspan="6">No recent orders</td></tr>';
      return;
    }
    orders.forEach(o => {
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td>${o.id}</td>
        <td>${o.customerName}</td>
        <td>${o.date}</td>
        <td>${o.status}</td>
        <td>${EComUtils.formatCurrency(o.total)}</td>
        <td><button class="btn btn-sm btn-primary" onclick="viewOrder(${o.id})">View</button></td>
      `;
      body.appendChild(tr);
    });
  } catch (err) {
    Notifications.show('Failed to load orders: ' + err.message, 'Error', 'error');
  }
}

async function fetchOrders() {
  try {
    const orders = await API.get('/admin/orders');
    const body = document.getElementById('ordersBody');
    body.innerHTML = '';
    if (!orders || orders.length === 0) {
      body.innerHTML = '<tr><td colspan="6">No orders</td></tr>';
      return;
    }
    orders.forEach(o => {
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td>${o.id}</td>
        <td>${o.customerName}</td>
        <td>${o.date}</td>
        <td>${o.status}</td>
        <td>${EComUtils.formatCurrency(o.total)}</td>
        <td>
          <button class="btn btn-sm btn-success" onclick="updateOrderStatus(${o.id}, 'SHIPPED')">Ship</button>
          <button class="btn btn-sm btn-danger" onclick="updateOrderStatus(${o.id}, 'CANCELLED')">Cancel</button>
        </td>
      `;
      body.appendChild(tr);
    });
  } catch (err) {
    Notifications.show('Failed to load orders: ' + err.message, 'Error', 'error');
  }
}

async function updateOrderStatus(orderId, status) {
  try {
    await API.put(`/admin/orders/${orderId}/status`, { status });
    Notifications.show('Order updated', 'Success');
    fetchOrders();
  } catch (err) {
    Notifications.show('Failed to update order: ' + err.message, 'Error', 'error');
  }
}

async function fetchProducts() {
  try {
    const products = await API.get('/admin/products');
    const container = document.getElementById('productsList');
    container.innerHTML = '';
    if (!products || products.length === 0) {
      container.innerHTML = '<div class="col-12">No products</div>';
      return;
    }
    products.forEach(p => {
      const col = document.createElement('div');
      col.className = 'col-sm-6 col-md-4 col-lg-3';
      col.innerHTML = `
        <div class="card product-card">
          <img src="${p.imageUrl || '/images/placeholder.png'}" class="card-img-top" alt="">
          <div class="card-body">
            <h5 class="card-title">${p.name}</h5>
            <p class="card-text text-muted">${p.shortDescription || ''}</p>
            <div class="d-flex justify-content-between align-items-center">
              <span class="fw-bold">${EComUtils.formatCurrency(p.price)}</span>
              <div>
                <button class="btn btn-sm btn-secondary" onclick="editProduct(${p.id})">Edit</button>
                <button class="btn btn-sm btn-danger" onclick="deleteProduct(${p.id})">Delete</button>
              </div>
            </div>
          </div>
        </div>
      `;
      container.appendChild(col);
    });
  } catch (err) {
    Notifications.show('Failed to load products: ' + err.message, 'Error', 'error');
  }
}

async function deleteProduct(id) {
  if (!confirm('Delete product #' + id + '?')) return;
  try {
    await API.del(`/admin/products/${id}`);
    Notifications.show('Product deleted', 'Success');
    fetchProducts();
  } catch (err) {
    Notifications.show('Delete failed: ' + err.message, 'Error', 'error');
  }
}

function editProduct(id) { 
  // placeholder; when controller available, navigate to edit page
  window.location.href = `/admin/products/${id}/edit`;
}

async function fetchUsers() {
  try {
    const users = await API.get('/admin/users');
    const body = document.getElementById('usersBody');
    body.innerHTML = '';
    if (!users || users.length === 0) {
      body.innerHTML = '<tr><td colspan="5">No users</td></tr>';
      return;
    }
    users.forEach(u => {
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td>${u.id}</td>
        <td>${u.name}</td>
        <td>${u.email}</td>
        <td>${u.role}</td>
        <td>
          <button class="btn btn-sm btn-danger" onclick="deleteUser(${u.id})">Delete</button>
        </td>
      `;
      body.appendChild(tr);
    });
  } catch (err) {
    Notifications.show('Failed to load users: ' + err.message, 'Error', 'error');
  }
}

async function deleteUser(id) {
  if (!confirm('Delete user #' + id + '?')) return;
  try {
    await API.del(`/admin/users/${id}`);
    Notifications.show('User deleted', 'Success');
    fetchUsers();
  } catch (err) {
    Notifications.show('Delete failed: ' + err.message, 'Error', 'error');
  }
}

async function fetchCategories() {
  try {
    const cats = await API.get('/admin/categories');
    const body = document.getElementById('categoriesBody');
    body.innerHTML = '';
    if (!cats || cats.length === 0) {
      body.innerHTML = '<tr><td colspan="3">No categories</td></tr>';
      return;
    }
    cats.forEach(c => {
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td>${c.id}</td>
        <td>${c.name}</td>
        <td>
          <button class="btn btn-sm btn-secondary" onclick="editCategory(${c.id})">Edit</button>
          <button class="btn btn-sm btn-danger" onclick="deleteCategory(${c.id})">Delete</button>
        </td>
      `;
      body.appendChild(tr);
    });
  } catch (err) {
    Notifications.show('Failed to load categories: ' + err.message, 'Error', 'error');
  }
}

function editCategory(id) {
  window.location.href = `/admin/categories/${id}/edit`;
}

async function deleteCategory(id) {
  if (!confirm('Delete category #' + id + '?')) return;
  try {
    await API.del(`/admin/categories/${id}`);
    Notifications.show('Category deleted', 'Success');
    fetchCategories();
  } catch (err) {
    Notifications.show('Delete failed: ' + err.message, 'Error', 'error');
  }
}

function viewOrder(id) {
  window.location.href = `/admin/orders/${id}`;
}
