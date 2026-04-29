/* ================================= */
/* INPUT FIELD REFERENCES */
/* ================================= */

const recordId = document.getElementById("recordId");
const city = document.getElementById("city");
const state = document.getElementById("state");
const condition = document.getElementById("condition");
const temperature = document.getElementById("temperature");
const humidity = document.getElementById("humidity");
const date = document.getElementById("date");

/* ================================= */
/* API ENDPOINTS */
/* ================================= */

const API = {
    weather: "http://localhost:8081/weather",
    alerts: "http://localhost:8082/alerts",
    locations: "http://localhost:8083/locations" // ✅ CORRECT
};

/* ================================= */
/* WEATHER ICON LOGIC */
/* ================================= */

function getWeatherIcon(condition) {
    if (!condition) return "🌤️";

    const c = condition.toLowerCase();

    if (c.includes("sun") || c.includes("clear")) return "☀️";
    if (c.includes("cloud")) return "☁️";
    if (c.includes("rain")) return "🌧️";
    if (c.includes("storm")) return "⛈️";
    if (c.includes("snow")) return "❄️";
    if (c.includes("fog")) return "🌫️";

    return "🌤️";
}

/* ================================= */
/* DISPLAY WEATHER */
/* ================================= */

function displayWeather(data) {

    const container = document.getElementById("weatherCards");
    container.innerHTML = "";

    if (!data || data.length === 0) {
        container.innerHTML = "<p>No weather data available.</p>";
        return;
    }

    data.forEach(record => {

        const icon = getWeatherIcon(record.conditionName);

        const card = document.createElement("div");
        card.className = "weather-card";

        card.innerHTML = `
            <div style="font-size: 30px">${icon}</div>
            <h3>${record.cityName}, ${record.stateName}</h3>
            <p><b>Condition:</b> ${record.conditionName}</p>
            <p><b>Temp:</b> ${record.temperature}°F</p>
            <p><b>Humidity:</b> ${record.humidity}%</p>
            <p><b>Date:</b> ${record.recordDate}</p>
        `;

        card.onclick = () => {
            recordId.value = record.recordId;
            city.value = record.cityName;
            state.value = record.stateName;
            condition.value = record.conditionName;
            temperature.value = record.temperature;
            humidity.value = record.humidity;
            date.value = record.recordDate;
        };

        container.appendChild(card);
    });
}

/* ================================= */
/* LOAD WEATHER */
/* ================================= */

async function loadWeatherUI() {
    try {
        const res = await fetch(API.weather);
        const data = await res.json();
        displayWeather(data);
    } catch (err) {
        console.error("Weather error:", err);
        alert("Weather service error");
    }
}

/* ================================= */
/* WEATHER CRUD */
/* ================================= */

async function addRecord() {

    const data = {
        cityName: city.value,
        stateName: state.value,
        conditionName: condition.value,
        temperature: parseFloat(temperature.value),
        humidity: parseInt(humidity.value),
        recordDate: date.value
    };

    await fetch(API.weather, {
        method: "POST",
        headers: {"Content-Type":"application/json"},
        body: JSON.stringify(data)
    });

    loadWeatherUI();
}

async function updateRecord() {

    if (!recordId.value) return alert("Select record first");

    const data = {
        cityName: city.value,
        stateName: state.value,
        conditionName: condition.value,
        temperature: parseFloat(temperature.value),
        humidity: parseInt(humidity.value),
        recordDate: date.value
    };

    await fetch(`${API.weather}/${recordId.value}`, {
        method: "PUT",
        headers: {"Content-Type":"application/json"},
        body: JSON.stringify(data)
    });

    loadWeatherUI();
}

async function deleteRecord() {

    if (!recordId.value) return alert("Select record");

    await fetch(`${API.weather}/${recordId.value}`, {
        method: "DELETE"
    });

    loadWeatherUI();
}

/* ================================= */
/* LOAD LOCATIONS (8083 FIXED) */
/* ================================= */

async function loadLocations() {
    try {
        const res = await fetch(API.locations);

        if (!res.ok) throw new Error("Location fetch failed");

        const data = await res.json();

        const dropdown = document.getElementById("locationDropdown");
        dropdown.innerHTML = "<option value=''>Select Location</option>";

        data.forEach(l => {
            dropdown.innerHTML += `
                <option value="${l.locationId}">
                    ${l.city}, ${l.state}
                </option>
            `;
        });

        console.log("✅ Locations working on 8083:", data);

    } catch (err) {
        console.error("❌ 8083 ERROR:", err);
        alert("Location service (8083) not reachable");
    }
}

/* ================================= */
/* ALERTS */
/* ================================= */

async function addAlert() {

    const locationId = parseInt(document.getElementById("locationDropdown").value);
    const severity = document.getElementById("adminSeverity").value;
    const description = document.getElementById("adminDescription").value;

    if (!locationId) return alert("Select a location");

    await fetch(API.alerts, {
        method: "POST",
        headers: {"Content-Type":"application/json"},
        body: JSON.stringify({
            locationId,
            alertType: "General",
            severity,
            description
        })
    });

    loadAlerts();
}

async function loadAlerts() {
    try {
        const res = await fetch(API.alerts);
        const data = await res.json();
        displayAlerts(data);
    } catch (err) {
        console.error(err);
        alert("Alert service error");
    }
}

function displayAlerts(alerts) {

    const container = document.getElementById("weatherCards");
    container.innerHTML = "";

    alerts.forEach(a => {

        const card = document.createElement("div");
        card.className = "weather-card";
        card.style.border = "2px solid red";

        card.innerHTML = `
            <h3>🚨 ALERT</h3>
            <p><b>Type:</b> ${a.alertType}</p>
            <p><b>Severity:</b> ${a.severity}</p>
            <p>${a.description}</p>
        `;

        container.appendChild(card);
    });
}

/* ================================= */
/* INIT */
/* ================================= */

window.onload = () => {
    loadWeatherUI();
    loadLocations(); // 🔥 THIS CONFIRMS 8083
    loadAlerts();
};
