// Common utility functions
(function (window) {
  'use strict';

  function formatCurrency(amount) {
    if (amount == null) return '₹0.00';
    // Format as Indian Rupees. Uses en-IN locale; adjust if you prefer other formatting.
    return new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR', maximumFractionDigits: 2 }).format(amount);
  }

  function qs(selector, scope) {
    return (scope || document).querySelector(selector);
  }

  function qsa(selector, scope) {
    return Array.from((scope || document).querySelectorAll(selector));
  }

  window.EComUtils = {
    formatCurrency: formatCurrency,
    qs: qs,
    qsa: qsa
  };
})(window);
