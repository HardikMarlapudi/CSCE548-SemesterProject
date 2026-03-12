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
/* DISPLAY WEATHER CARDS */
/* ================================= */

function displayWeather(data) {

    const container = document.getElementById("weatherCards");

    container.innerHTML = "";

    data.forEach(record => {

        /* Select icon based on condition */

        let icon = "☀️";

        if (record.conditionName.toLowerCase().includes("rain")) icon = "🌧️";
        if (record.conditionName.toLowerCase().includes("cloud")) icon = "☁️";
        if (record.conditionName.toLowerCase().includes("snow")) icon = "❄️";
        if (record.conditionName.toLowerCase().includes("storm")) icon = "⛈️";

        /* Create card */

        const card = document.createElement("div");

        card.className = "weather-card";

        card.innerHTML = `
            <div class="weather-icon">${icon}</div>
            <h3>${record.cityName}, ${record.stateName}</h3>
            <p><b>Condition:</b> ${record.conditionName}</p>
            <p><b>Temperature:</b> ${record.temperature}°F</p>
            <p><b>Humidity:</b> ${record.humidity}%</p>
            <p><b>Date:</b> ${record.recordDate}</p>
        `;

        /* Clicking card fills the edit form */

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
        const data = await response.json();
        displayWeather(data);

    } catch (error) {
        console.error("Error loading weather:", error);
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
        alert("Weather JSON loaded! Check console.");

    } catch (error) {
        console.error("JSON error:", error);
    }

}

/* ================================= */
/* ADD RECORD */
/* ================================= */

async function addRecord() {

    try {

        const data = {

            cityName: city.value,
            stateName: state.value,
            conditionName: condition.value,
            temperature: parseFloat(temperature.value),
            humidity: parseInt(humidity.value),
            recordDate: date.value

        };

        const response = await fetch("http://localhost:8081/weather", {

            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)

        });

        if (!response.ok) throw new Error("Add failed");

        alert("Record Added");

        loadWeatherUI();

    } catch (error) {

        console.error("Add error:", error);

    }

}

/* ================================= */
/* UPDATE RECORD */
/* ================================= */

async function updateRecord() {

    /* Make sure a record is selected */

    if (!recordId.value) {

        alert("Please click a weather card first to select a record.");
        return;

    }

    try {

        const data = {

            cityName: city.value,
            stateName: state.value,
            conditionName: condition.value,
            temperature: parseFloat(temperature.value),
            humidity: parseInt(humidity.value),
            recordDate: date.value

        };

        const response = await fetch(`http://localhost:8081/weather/${recordId.value}`, {

            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)

        });

        if (!response.ok) {
            throw new Error("Update failed");
        }

        alert("Record Updated Successfully");

        loadWeatherUI();

    } catch (error) {

        console.error("Update error:", error);
        alert("Error updating record");

    }

}

/* ================================= */
/* DELETE RECORD */
/* ================================= */

async function deleteRecord() {

    await fetch(`http://localhost:8081/weather/${recordId.value}`, {
        method: "DELETE"
    });
    if (document.getElementById("recordId").value == "" || document.getElementById("city").value == "" || document.getElementById("state").value == "" || document.getElementById("condition").value == "" || document.getElementById("temperature").value == "" || document.getElementById("humidity").value == "" || document.getElementById("date").value == "") {
        alert("Please enter weather card details to remove a weather card");
    } else {
        alert("Record deleted");
        loadWeatherUI();
    }
}

/* ================================= */
/* AUTO LOAD WEATHER ON PAGE START */
/* ================================= */

window.onload = loadWeatherUI;
