/* ------------------------------
FORM INPUT REFERENCES
------------------------------ */

const recordId = document.getElementById("recordId");
const city = document.getElementById("city");
const state = document.getElementById("state");
const condition = document.getElementById("condition");
const temperature = document.getElementById("temperature");
const humidity = document.getElementById("humidity");
const date = document.getElementById("date");

/* ------------------------------
DISPLAY WEATHER TABLE
------------------------------ */

function displayWeather(data) {

    const tableBody = document.getElementById("weatherTableBody");

    tableBody.innerHTML = "";

    data.forEach(record => {

        const row = document.createElement("tr");

        row.innerHTML = `
        <td>${record.recordId}</td>
        <td>${record.cityName}</td>
        <td>${record.stateName}</td>
        <td>${record.conditionName}</td>
        <td>${record.temperature}°F</td>
        <td>${record.humidity}%</td>
        <td>${record.recordDate}</td>
        `;

        /* Click row to load record into form */

        row.onclick = () => {

            recordId.value = record.recordId;
            city.value = record.cityName;
            state.value = record.stateName;
            condition.value = record.conditionName;
            temperature.value = record.temperature;
            humidity.value = record.humidity;
            date.value = record.recordDate;

        };

        tableBody.appendChild(row);

    });

}

/* ------------------------------
LOAD WEATHER DATA
------------------------------ */

async function loadWeatherUI() {

    try {

        const response = await fetch("http://localhost:8081/weather");

        const data = await response.json();

        displayWeather(data);

    } catch(error) {

        console.error("Error loading weather data:", error);

    }

}

/* ------------------------------
LOAD WEATHER JSON (DEVTOOLS)
------------------------------ */

async function loadWeatherJSON() {

    try {

        const response = await fetch("http://localhost:8081/weather");
        const data = await response.json();
        console.log("Weather JSON:", data);

    } catch(error) {
        console.error("Error loading JSON:", error);
    }

}

/* ------------------------------
ADD WEATHER RECORD
------------------------------ */

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

        if(!response.ok) throw new Error("Failed to add record");
        alert("Record Added");
        loadWeatherUI();

    } catch(error) {
        console.error("Add error:", error);
    }

}

/* ------------------------------
UPDATE WEATHER RECORD
------------------------------ */

async function updateRecord() {

    if(!recordId.value) {
        alert("Please select a record first");
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
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)

        });

        if(!response.ok) throw new Error("Update failed");
        alert("Record Updated");
        loadWeatherUI();

    } catch(error) {
        console.error("Update error:", error);
    }

}

/* ------------------------------
DELETE WEATHER RECORD
------------------------------ */

async function deleteRecord() {

    if(!recordId.value) {
        alert("Please select a record first");
        return;
    }

    try {
        const response = await fetch(`http://localhost:8081/weather/${recordId.value}`, {
            method: "DELETE"
        });

        if(!response.ok) throw new Error("Delete failed");

        alert("Record Deleted");

        loadWeatherUI();

    } catch(error) {
        console.error("Delete error:", error);
    }

}

/* ------------------------------
LOAD DATA ON PAGE START
------------------------------ */

window.onload = loadWeatherUI;
