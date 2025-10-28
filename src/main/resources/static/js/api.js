// Simple API wrapper using fetch. Replace baseUrl with your backend endpoints when ready.
const API = (function () {
  const baseUrl = '/api';

  async function get(path) {
    const res = await fetch(baseUrl + path, { credentials: 'same-origin' });
    if (!res.ok) throw new Error('Network response was not ok');
    return res.json();
  }

  async function post(path, body) {
    const res = await fetch(baseUrl + path, {
      method: 'POST',
      credentials: 'same-origin',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body)
    });
    if (!res.ok) throw new Error('Network response was not ok');
    return res.json();
  }

  async function put(path, body) {
    const res = await fetch(baseUrl + path, {
      method: 'PUT',
      credentials: 'same-origin',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body)
    });
    if (!res.ok) throw new Error('Network response was not ok');
    return res.json();
  }

  async function del(path) {
    const res = await fetch(baseUrl + path, {
      method: 'DELETE',
      credentials: 'same-origin'
    });
    if (!res.ok) throw new Error('Network response was not ok');
    return res;
  }

  return { get, post, put, del };
})();
