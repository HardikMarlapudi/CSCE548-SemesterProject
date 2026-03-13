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
    return text
        .replace(/&/g,"&amp;")
        .replace(/</g,"&lt;")
        .replace(/>/g,"&gt;");
}

/* ================================= */
/* DISPLAY WEATHER CARDS */
/* ================================= */

function displayWeather(data) {

    const container = document.getElementById("weatherCards");

    container.innerHTML = "";

    data.forEach(record => {

        let icon = "☀️";

        if (record.conditionName.toLowerCase().includes("rain")) icon = "🌧️";
        if (record.conditionName.toLowerCase().includes("cloud")) icon = "☁️";
        if (record.conditionName.toLowerCase().includes("snow")) icon = "❄️";
        if (record.conditionName.toLowerCase().includes("storm")) icon = "⛈️";

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

        /* Fill form when card clicked */

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
/* LOAD WEATHER DATA */
/* ================================= */

async function loadWeatherUI() {

    try {
        const response = await fetch("http://localhost:8081/weather");
        if (!response.ok) throw new Error("Failed to load weather");
        const data = await response.json();
        displayWeather(data);
    } catch (error) {
        console.error("Error loading weather:", error);
        alert("Unable to load weather data");
    }

}

/* ================================= */
/* LOAD WEATHER JSON (DEBUG TOOL) */
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
        conditionName: condition.value.trim(),
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
        conditionName: condition.value.trim(),
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

/* ================================= */
/* AUTO LOAD WEATHER */
/* ================================= */

window.onload = loadWeatherUI;
