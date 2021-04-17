async function init() {

  const registration = await navigator.serviceWorker.register('/sw.js');
  await navigator.serviceWorker.ready;
  firebase.initializeApp({
apiKey: "AIzaSyCwZXT7BhVVMsqq7qnTwf2qR7WIg9OgICg",
      authDomain: "trading-app-d9209.firebaseapp.com",
      projectId: "trading-app-d9209",
      storageBucket: "trading-app-d9209.appspot.com",
      messagingSenderId: "471880654329",
      appId: "1:471880654329:web:d0f44d60f883b60bf3b128",
      measurementId: "G-BMEN4H5Y70"
  });
  const messaging = firebase.messaging();
  messaging.usePublicVapidKey('BFC_rEf8xOvYTTtacZYs0rI2TBzbiKjYadiQQ6UQyaSX2KQr6nTzQs2QAVbOvYbkbVobkXgw8kZtmwq0gdAxGGQ');
  messaging.useServiceWorker(registration);

  try {
    await messaging.requestPermission();
  } catch (e) {
    console.log('Unable to get permission', e);
    return;
  }

  navigator.serviceWorker.addEventListener('message', event => {
    if (event.data === 'newData') {
      showData();
    }
  });

  const currentToken = await messaging.getToken();
  fetch('/register', { method: 'post', body: currentToken });
  showData();

  messaging.onTokenRefresh(async () => {
    console.log('token refreshed');
    const newToken = await messaging.getToken();
    fetch('/register', { method: 'post', body: currentToken });
  });

}

async function showData() {
  const db = await getDb();
  const tx = db.transaction('jokes', 'readonly');
  const store = tx.objectStore('jokes');
  store.getAll().onsuccess = e => showJokes(e.target.result);
}

function showJokes(jokes) {
  const table = document.getElementById('outTable');

  jokes.sort((a, b) => parseInt(b.ts) - parseInt(a.ts));
  const html = [];
  jokes.forEach(j => {
    const date = new Date(parseInt(j.ts));
    html.push(`<div><div class="header">${date.toISOString()} ${j.id} (${j.seq})</div><div class="joke">${j.joke}</div></div>`);
  });
  table.innerHTML = html.join('');
}

async function getDb() {
  if (this.db) {
    return Promise.resolve(this.db);
  }
  return new Promise(resolve => {
    const openRequest = indexedDB.open("Chuck", 1);

    openRequest.onupgradeneeded = event => {
      const db = event.target.result;
      db.createObjectStore('jokes', { keyPath: 'id' });
    };

    openRequest.onsuccess = event => {
      this.db = event.target.result;
      resolve(this.db);
    }
  });
}

init();
