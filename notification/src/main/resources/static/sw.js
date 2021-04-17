importScripts('https://www.gstatic.com/firebasejs/7.10.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/7.10.0/firebase-messaging.js');

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

self.addEventListener('push', async event => {
	const db = await getDb();
	const tx = this.db.transaction('jokes', 'readwrite');
	const store = tx.objectStore('jokes');

	const data = event.data.json().data;
	data.id = parseInt(data.id);
	store.put(data);

	tx.oncomplete = async e => {
		const allClients = await clients.matchAll({ includeUncontrolled: true });
		for (const client of allClients) {
			client.postMessage('newData');
		}
	};
});

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


messaging.setBackgroundMessageHandler(function(payload) {
  const notificationTitle = 'Background Title (client)';
  const notificationOptions = {
    body: 'Background Body (client)',
    icon: '/mail.png'
  };

  return self.registration.showNotification(notificationTitle,
      notificationOptions);
});


const CACHE_NAME = 'my-site-cache-v1';
const urlsToCache = [
	'/index.html',
	'/index.js',
	'/mail.png',
	'/mail2.png',
	'/manifest.json'
];

self.addEventListener('install', event => {
	event.waitUntil(caches.open(CACHE_NAME)
		.then(cache => cache.addAll(urlsToCache)));
});

self.addEventListener('fetch', event => {
	event.respondWith(
		caches.match(event.request)
			.then(response => {
				if (response) {
					return response;
				}
				return fetch(event.request);
			}
			)
	);
});


