const output = document.getElementById("output");

const weatherIcons = {
    "Sunny": "☀️",
    "Cloudy": "☁️",
    "Rainy": "🌧️",
    "Snowy": "❄️",
    "Stormy": "⛈️",
    "Windy": "💨",
    "Fog": "🌫️",
    "Partly Cloudy": "⛅",
    "Overcast": "🌥️",
    "Clear": "🌙"
}

function displayWeather(data) {

    const container = document.getElementById("weatherSection");

    if (!container) {
        console.error("weatherSection element not found in HTML");
        return;
    }

    container.innerHTML = "";

    data.forEach(record => {

        const icon = weatherIcons[record.conditionName] || "⛅";

        const card = document.createElement("div");
        card.className = "weather-card";

        card.innerHTML = `
        <div class="icon">${icon}</div>
        <h3>${record.cityName}</h3>
        <p><strong>Station:</strong> ${record.stationName}</p>
        <p><strong>Condition:</strong> ${record.conditionName}</p>
        <p><strong>Temperature:</strong> ${record.temperature}°C</p>
        <p><strong>Humidity:</strong> ${record.humidity}%</p>
        <p><strong>Date:</strong> ${record.recordDate}</p>
        `;

        container.appendChild(card);
    });
}

async function fetchData(url, title) {
    try {

        output.textContent = "Loading " + title + "...";

        const response = await fetch(url, {
            method: 'GET',

            headers: {
                'Content-Type': 'text/plain'
            }
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const text = await response.text();

        output.textContent = "===== " + title + " =====\n\n" + text;
    } catch (error) {
        output.textContent = "ERROR:\n" + error.message;
    }
}

// LOAD WEATHER
async function loadWeather() {

    const response = await fetch("http://localhost:8081/weather");

    const data = await response.json();

    output.textContent =
        "===== Weather Service =====\n\n" +
        JSON.stringify(data, null, 2);

    displayWeather(data);
}

async function loadWeatherUI() {

    const response = await fetch("http://localhost:8081/weather");

    const data = await response.json();

    displayWeather(data);
}

// ADD WEATHER (POST)
async function addRecord() {
    const data = {
        cityName: document.getElementById("city").value,
        stationName: document.getElementById("station").value,
        conditionName: document.getElementById("condition").value,
        temperature: parseFloat(document.getElementById("temperature").value),
        humidity: parseInt(document.getElementById("humidity").value),
        recordDate: document.getElementById("date").value
    };

    await fetch("http://localhost:8081/weather", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    alert("Operation Successful!");
    loadWeather();
}

// UPDATE WEATHER (PUT)
async function updateRecord() {
    const id = document.getElementById("recordId").value;

    if(!id) {
        alert("Enter Record ID to update.");
        return;
    }

    const data = {
        cityName: document.getElementById("city").value,
        stationName: document.getElementById("station").value,
        conditionName: document.getElementById("condition").value,
        temperature: parseFloat(document.getElementById("temperature").value),
        humidity: parseInt(document.getElementById("humidity").value),
        recordDate: document.getElementById("date").value
    };

    await fetch(`http://localhost:8081/weather/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })

    alert("Operation Successful!");
    loadWeather();
}

// DELETE WEATHER (DELETE)
async function deleteRecord() {
    const id = document.getElementById("recordId").value;

    if (!id) {
        alert("Enter Record ID to delete.");
        return;
    }

    await fetch(`http://localhost:8081/weather/${id}`, {
        method: 'DELETE',
    });

    alert("Operation Successful!");
    loadWeather();
}

function loadAlerts() {
    fetchData(
        "http://localhost:8082/alerts",
        "Alert Service"
    );
}

function loadLocations() {
    fetchData(
        "http://localhost:8083/locations",
        "Location Service"
    );
}
