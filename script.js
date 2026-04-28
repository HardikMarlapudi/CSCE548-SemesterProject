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

const API = {
    weather: "http://localhost:8081/weather",
    locations: "http://localhost:8082/locations",
    conditions: "http://localhost:8083/conditions",
    alerts: "http://localhost:8084/alerts"
};

/* ================================= */
/* INPUT VALIDATION */
/* ================================= */

function validateWeatherInput() {

    if (!city.value.trim()) {
        alert("City is required.");
        return false;
    }

    if (!state.value.trim()) {
        alert("State is required.");
        return false;
    }

    if (!condition.value.trim()) {
        alert("Condition is required.");
        return false;
    }

    if (isNaN(temperature.value)) {
        alert("Temperature must be a number.");
        return false;
    }

    if (temperature.value < -100 || temperature.value > 150) {
        alert("Temperature must be between -100 and 150.");
        return false;
    }

    if (isNaN(humidity.value)) {
        alert("Humidity must be a number.");
        return false;
    }

    if (humidity.value < 0 || humidity.value > 100) {
        alert("Humidity must be between 0 and 100.");
        return false;
    }

    if (!date.value) {
        alert("Date is required.");
        return false;
    }

    return true;
}

/* ================================= */
/* HTML ESCAPE (XSS PROTECTION) */
/* ================================= */

function escapeHTML(text) {

    if (!text) return "";

    return text
        .toString()
        .replace(/&/g,"&amp;")
        .replace(/</g,"&lt;")
        .replace(/>/g,"&gt;");
}

/* ================================= */
/* CONDITION NORMALIZATION */
/* ================================= */

function normalizeCondition(conditionName) {

    if (!conditionName) return "unknown";

    const c = conditionName.toLowerCase().trim();

    if (c.includes("sun") || c.includes("clear")) return "sunny";
    if (c.includes("cloud")) return "cloudy";
    if (c.includes("rain") || c.includes("drizzle")) return "rainy";
    if (c.includes("snow") || c.includes("blizzard")) return "snow";
    if (c.includes("storm") || c.includes("thunder")) return "storm";
    if (c.includes("fog") || c.includes("mist")) return "fog";
    if (c.includes("wind")) return "wind";

    return "unknown";
}

/* ================================= */
/* RETURN WEATHER ICON */
/* ================================= */

function getWeatherIcon(conditionName) {

    const condition = normalizeCondition(conditionName);

    const icons = {
        sunny: "☀️",
        cloudy: "☁️",
        rainy: "🌧️",
        snow: "❄️",
        storm: "⛈️",
        fog: "🌫️",
        wind: "💨",
        unknown: "🌤️"
    };

    return icons[condition];
}

/* ================================= */
/* DISPLAY WEATHER CARDS */
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
            <div class="weather-icon">${icon}</div>
            <h3>${escapeHTML(record.cityName)}, ${escapeHTML(record.stateName)}</h3>
            <p><b>Condition:</b> ${escapeHTML(record.conditionName)}</p>
            <p><b>Temperature:</b> ${record.temperature}°F</p>
            <p><b>Humidity:</b> ${record.humidity}%</p>
            <p><b>Date:</b> ${escapeHTML(record.recordDate)}</p>
        `;

        card.addEventListener("click", () => {

            recordId.value = record.recordId;
            city.value = record.cityName;
            state.value = record.stateName;
            condition.value = record.conditionName;
            temperature.value = record.temperature;
            humidity.value = record.humidity;
            date.value = record.recordDate;

        });

        container.appendChild(card);

    });

}

/* ================================= */
/* LOAD WEATHER DATA */
/* ================================= */

async function loadWeatherUI() {

    try {

        const response = await fetch("http://localhost:8081/weather");

        if (!response.ok) {
            throw new Error("Failed to load weather");
        }

        const data = await response.json();

        displayWeather(data);

    } catch (error) {

        console.error("Error loading weather:", error);
        alert("Weather service is unavailable. Please check the server.");

    }

}

/* ================================= */
/* DEBUG WEATHER JSON */
/* ================================= */

async function loadWeatherJSON() {

    try {

        const response = await fetch("http://localhost:8081/weather");
        const data = await response.json();

        console.log("Weather JSON:", data);

        alert("Weather JSON loaded. Check console.");

    } catch (error) {

        console.error("JSON error:", error);

    }

}

/* ================================= */
/* ADD RECORD */
/* ================================= */

async function addRecord() {

    if (!validateWeatherInput()) return;

    const data = {

        cityName: city.value.trim(),
        stateName: state.value.trim(),
        conditionName: condition.value.trim().toLowerCase(),
        temperature: parseFloat(temperature.value),
        humidity: parseInt(humidity.value),
        recordDate: date.value

    };

    try {

        const response = await fetch("http://localhost:8081/weather", {
            method: "POST",
            headers: {"Content-Type":"application/json"},
            body: JSON.stringify(data)
        });

        if (!response.ok) throw new Error("Add failed");

        alert("Record Added");

        loadWeatherUI();

    } catch(error) {

        console.error(error);
        alert("Server error while adding record.");

    }

}

/* ================================= */
/* UPDATE RECORD */
/* ================================= */

async function updateRecord() {

    if (!recordId.value) {

        alert("Please select a weather card first.");
        return;

    }

    if (!validateWeatherInput()) return;

    const data = {

        cityName: city.value.trim(),
        stateName: state.value.trim(),
        conditionName: condition.value.trim().toLowerCase(),
        temperature: parseFloat(temperature.value),
        humidity: parseInt(humidity.value),
        recordDate: date.value

    };

    try {

        const response = await fetch(`http://localhost:8081/weather/${recordId.value}`, {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(data)
        });

        if (!response.ok) throw new Error("Update failed");

        alert("Record Updated");

        loadWeatherUI();

    } catch (error) {

        console.error(error);
        alert("Error updating record");

    }

}

/* ================================= */
/* DELETE RECORD */
/* ================================= */

async function deleteRecord() {

    if (!recordId.value) {

        alert("Please select a record to delete.");
        return;

    }

    try {

        const response = await fetch(`http://localhost:8081/weather/${recordId.value}`, {
            method: "DELETE"
        });

        if (!response.ok) throw new Error("Delete failed");

        alert("Record Deleted");

        loadWeatherUI();

    } catch(error) {

        console.error(error);
        alert("Error deleting record");

    }

}

async function addWeatherAdmin() {

    const city = document.getElementById("adminCity").value;
    const state = document.getElementById("adminState").value;
    const condition = document.getElementById("adminCondition").value;
    const temp = document.getElementById("adminTemp").value;
    const humidity = document.getElementById("adminHumidity").value;
    const date = document.getElementById("adminDate").value;

    // BASIC VALIDATION (don’t skip this)
    if (!city || !state || !condition || !date) {
        alert("Fill all fields.");
        return;
    }

    const data = {
        cityName: city.trim(),
        stateName: state.trim(),
        conditionName: condition.trim().toLowerCase(),
        temperature: parseFloat(temp),
        humidity: parseInt(humidity),
        recordDate: date
    };

    try {

        const response = await fetch("http://localhost:8081/weather", {
            method: "POST",
            headers: {"Content-Type":"application/json"},
            body: JSON.stringify(data)
        });

        if (!response.ok) throw new Error("Add failed");

        alert("Admin Record Added");

        // reload UI
        loadWeatherUI();

    } catch(error) {
        console.error(error);
        alert("Admin add failed");
    }
}

async function handleAdminAdd() {

    const type = document.getElementById("adminType").value;

    document.getElementById("adminCity").style.display = (type === "location") ? "block" : "none";
    document.getElementById("adminState").style.display = (type === "location") ? "block" : "none";

    document.getElementById("adminCondition").style.display = (type === "condition") ? "block" : "none";
    document.getElementById("adminTemp").style.display = (type === "condition") ? "block" : "none";
    document.getElementById("adminHumidity").style.display = (type === "condition") ? "block" : "none";

    document.getElementById("adminType").addEventListener("change", () => {

        const type = document.getElementById("adminType").value;
    
        const isAlert = (type === "alert");
    
        // Hide text inputs when alert
        document.getElementById("adminCity").style.display = isAlert ? "none" : "block";
        document.getElementById("adminState").style.display = isAlert ? "none" : "block";
        document.getElementById("adminCondition").style.display = isAlert ? "none" : "block";
        document.getElementById("adminTemp").style.display = isAlert ? "none" : "block";
        document.getElementById("adminHumidity").style.display = isAlert ? "none" : "block";
    
        // Show dropdowns for alert
        document.getElementById("locationDropdown").style.display = isAlert ? "block" : "none";
        document.getElementById("conditionDropdown").style.display = isAlert ? "block" : "none";
    
    });

    try {

        // LOCATION
        if (type === "location") {

            await fetch("http://localhost:8082/locations", {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({
                    city_name: city,
                    state_name: state,
                    latitude: 0,
                    longitude: 0
                })
            });

            loadLocations();
        }

        // CONDITION
        else if (type === "condition") {

            await fetch(API.conditions, {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({
                    condition_name: conditionVal,
                    temperature: temp,
                    humidity: hum
                })
            });

            loadConditions();
        }

        // ALERT
        else if (type === "alert") {

            const location_id = document.getElementById("locationDropdown").value;
            const condition_id = document.getElementById("conditionDropdown").value;
            const severity = document.getElementById("adminSeverity").value;
            const description = document.getElementById("adminDescription").value;

            await fetch(API.alerts, {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({
                    location_id,
                    condition_id,
                    severity,
                    description
                })
            });
        }

        alert("Success");
        
        // reload everything
        await loadWeatherUI();
        await loadAlerts();
        await loadLocations();
        await loadConditions();

    } catch (err) {
        console.error(err);
        alert("Admin failed");
    }
}

// FIXED loadConditions
async function loadConditions() {
    try {
        const res = await fetch("http://localhost:8083/conditions");

        if (!res.ok) throw new Error();

        const data = await res.json();

        const dropdown = document.getElementById("conditionDropdown");
        dropdown.innerHTML = "<option value=''>Select Condition</option>";

        data.forEach(c => {
            dropdown.innerHTML += `
                <option value="${c.condition_id}">
                    ${c.condition_name}
                </option>
            `;
        });

    } catch (err) {
        console.error(err);
        alert("Condition service not reachable");
    }
}

async function addAlert() {

    const location_id = document.getElementById("locationDropdown").value;
    const severity = document.getElementById("adminSeverity").value;
    const description = document.getElementById("adminDescription").value;

    if (!location_id) {
        alert("Select a location");
        return;
    }

    try {
        const res = await fetch("http://localhost:8084/alerts", { // FIXED PORT
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                location_id,
                alert_type: "General", // TEMP until condition service exists
                severity,
                description
            })
        });

        if (!res.ok) throw new Error("Alert failed");

        alert("Alert Added");

    } catch (err) {
        console.error(err);
        alert("Alert service not working");
    }
}

async function loadAlerts() {
    try {
        const res = await fetch("http://localhost:8084/alerts");
        if (!res.ok) throw new Error();

        const data = await res.json();

        console.log("ALERTS:", data);

        displayAlerts(data);

    } catch (err) {
        console.error(err);
        alert("Alert service not reachable");
    }
}

async function loadLocations() {
    try {
        const res = await fetch("http://localhost:8082/locations");

        if (!res.ok) throw new Error("Location fetch failed");

        const data = await res.json();

        // store globally for your text-input matching
        window.locationsData = data;

        console.log("Locations loaded:", data);

    } catch (err) {
        console.error("Location load failed:", err);
    }
}

function displayAlerts(alerts) {

    const container = document.getElementById("weatherCards");

    alerts.forEach(alert => {

        const card = document.createElement("div");
        card.className = "weather-card";

        card.style.border = "2px solid red";

        card.innerHTML = `
            <h3>🚨 ALERT</h3>
            <p><b>Severity:</b> ${alert.severity}</p>
            <p><b>Description:</b> ${alert.description}</p>
        `;

        container.appendChild(card);
    });
}

function findLocationIdByName(name, locations) {
    return locations.find(l =>
        (l.city_name + ", " + l.state_name).toLowerCase() === name.toLowerCase()
    )?.location_id;
}

function findConditionIdByName(name, conditions) {
    return conditions.find(c =>
        c.condition_name.toLowerCase() === name.toLowerCase()
    )?.condition_id;
}

/* ================================= */
/* AUTO LOAD WEATHER */
/* ================================= */

window.onload = () => {
    loadWeatherUI();
    loadLocations();
    loadConditions();
    loadAlerts();
};
