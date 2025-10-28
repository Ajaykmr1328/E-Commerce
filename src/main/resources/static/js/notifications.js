// Minimal toast notification helper using Bootstrap toasts (expects a toast container in page or will create one)
(function (window) {
  'use strict';

  function _ensureContainer() {
    let container = document.getElementById('toast-container');
    if (!container) {
      container = document.createElement('div');
      container.id = 'toast-container';
      container.className = 'toast-container position-fixed bottom-0 end-0 p-3';
      document.body.appendChild(container);
    }
    return container;
  }

  function show(message, title = '', type = 'info', delay = 4000) {
    const container = _ensureContainer();
    const toast = document.createElement('div');
    toast.className = 'toast align-items-center text-bg-light border-0';
    toast.role = 'alert';
    toast.ariaLive = 'assertive';
    toast.ariaAtomic = 'true';
    toast.innerHTML = `
      <div class="d-flex">
        <div class="toast-body">
          ${title ? `<strong>${title}</strong><br/>` : ''}${message}
        </div>
        <button type="button" class="btn-close me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
      </div>
    `;
    container.appendChild(toast);
    const bsToast = bootstrap.Toast.getOrCreateInstance(toast, { delay });
    bsToast.show();
    toast.addEventListener('hidden.bs.toast', () => toast.remove());
  }

  window.Notifications = { show };
})(window);
