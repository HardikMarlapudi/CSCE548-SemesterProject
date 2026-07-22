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
    locations: "http://localhost:8083/locations"
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

function getStateAbbreviation(state) {

        const states = {
            "Alabama": "AL",
            "Alaska": "AK",
            "Arizona": "AZ",
            "Arkansas": "AR",
            "California": "CA",
            "Colorado": "CO",
            "Connecticut": "CT",
            "Delaware": "DE",
            "Florida": "FL",
            "Georgia": "GA",
            "Hawaii": "HI",
            "Idaho": "ID",
            "Illinois": "IL",
            "Indiana": "IN",
            "Iowa": "IA",
            "Kansas": "KS",
            "Kentucky": "KY",
            "Louisiana": "LA",
            "Maine": "ME",
            "Maryland": "MD",
            "Massachusetts": "MA",
            "Michigan": "MI",
            "Minnesota": "MN",
            "Mississippi": "MS",
            "Missouri": "MO",
            "Montana": "MT",
            "Nebraska": "NE",
            "Nevada": "NV",
            "New Hampshire": "NH",
            "New Jersey": "NJ",
            "New Mexico": "NM",
            "New York": "NY",
            "North Carolina": "NC",
            "North Dakota": "ND",
            "Ohio": "OH",
            "Oklahoma": "OK",
            "Oregon": "OR",
            "Pennsylvania": "PA",
            "Rhode Island": "RI",
            "South Carolina": "SC",
            "South Dakota": "SD",
            "Tennessee": "TN",
            "Texas": "TX",
            "Utah": "UT",
            "Vermont": "VT",
            "Virginia": "VA",
            "Washington": "WA",
            "West Virginia": "WV",
            "Wisconsin": "WI",
            "Wyoming": "WY"
        };
    
    return states[state] || state;
}

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
            <h3>${record.cityName}, ${getStateAbbreviation(record.stateName)}</h3>
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

async function loadWeatherJSON() {

    try {
        const res = await fetch(API.weather);
        const data = await res.json();
        
        console.log("============ WEATHER JSON =============");
        console.log(data);

    } catch (error) {
        console.error("Weather JSON error: ", error);
    }
}

function showStatus(message,type) {

    const box = document.getElementById("statusMessage");

    box.textContent = message;

    box.className = "status-message";

    if(type === "success") {
        box.classList.add("status-success");
    } else {
        box.classList.add("status-error");
    }

    box.style.display="block";
    setTimeout(() => {box.style.display = "none"}, 3000);

}

function handleAdminAction() {

    const adminType = document.getElementById("adminType").value;

    switch(adminType) {
        case "station":
            addStation();
            break;
        case "location":
            addLocation();
            break;
        case "condition":
            addCondition();
            break;
        case "alert":
            addAlert();
            break;
        }
}

/* ================================= */
/* WEATHER CRUD */
/* ================================= */

async function addRecord() {

    if(city.value === "" || state.value === "" || condition.value === "" || temperature.value === "" || humidity.value === "" || date.value === "") {
        alert("Fill out all fields.");
        return;
    }

    const data = {
        cityName: city.value,
        stateName: state.value,
        conditionName: condition.value,
        temperature: parseFloat(temperature.value),
        humidity: parseInt(humidity.value),
        recordDate: date.value
    };

    const response = await fetch(API.weather, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    });
    
    if (!response.ok) {
        alert("Request failed.");
        return;
    }
    
    clearWeatherForm();

    await loadWeatherUI();
    await loadWeatherJSON();
    await loadLocations();

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

    const response = await fetch(`${API.weather}/${recordId.value}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    });
    
    if (!response.ok) {
        alert("Request failed.");
        return;
    }

    clearWeatherForm();

    await loadWeatherUI();
    await loadWeatherJSON();
    await loadLocations();

}

async function deleteRecord() {

    if (!recordId.value) return alert("Select record");

    const response = await fetch(`${API.weather}/${recordId.value}`, {
        method: "DELETE"
    });
    
    if (!response.ok) {
        alert("Delete failed.");
        return;
    }
    
    clearWeatherForm();
    loadWeatherUI();
    loadWeatherJSON();

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

        console.log("Locations working on 8083:", data);

    } catch (err) {
        console.error("8083 ERROR:", err);
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
    const alertDate = document.getElementById("alertDate").value;

    console.log(document.getElementById("alertDate"));

    if (!locationId) {
        alert("Select a location");
        return;
    }

    const response = await fetch(API.alerts, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            locationId,
            alertType: "General",
            severity,
            description,
            alertDate
        })
    });

    console.log("Status:", response.status);

    const text = await response.text();
    console.log("Response:", text);

    if (!response.ok) {
        alert("Unable to add alert.");
        return;
    }

    loadAlerts();

} 

async function updateAlert() {

    const alertId = document.getElementById("alertId").value;

    if (!alertId) {
        alert("Select an alert first.");
        return;
    }

    const response = await fetch(API.alerts, {

        method: "PUT",

        headers: {
            "Content-Type":"applicatioon/json"
        },

        body: JSON.stringify({

            alertId: parseInt(alertId),

            locationId: parseInt(
                document.getElementById("locationDropdown").value
            ),

            alertType: "General",

            severity: document.getElementById("adminSeverity").value,

            description: document.getElementById("alertDescription").value,

            alertDate: document.getElementById("alertDate").value

        })

    });

    if(!response.ok) {
        alert("Update failed.");
        return;
    }

    clearAlertForm();

    loadAlerts();
}

async function deleteAlert(id) {

    const response = await fetch (

        `${API.alerts}?id=${id}`,

        {
            method: "DELETE"
        }

    );

    if(!response.ok) {
        alert("Delete failed.");
        return;
    }

    clearAlertForm();

    loadAlerts();
}

function clearAlertForm() {

    document.getElementById("alertId").value = "";

    document.getElementById("locationDropdown").value = "";

    document.getElementById("adminSeverity").value = "";

    document.getElementById("adminDescription").value = "";

    document.getElementById("alertDate").value = "";

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

async function addStation() {
    const stationName = document.getElementById("adminStationName").value;
    const city = document.getElementById("adminCity").value;
    const state = document.getElementById("adminState").value;

    const response = await fetch("http://localhost:8083/locations", {
        method: "POST",
        headers: {"Content-Type":"application/json"},
        body: JSON.stringify({
            stationName,
            city,
            state
        })
    });

    if(!response.ok) {
        alert("Request failed");
        return;
    }

    loadLocations();
}

async function addLocation() {
    alert("Location feature coming next.");
}

async function addCondition() {
    alert("Condition feature coming next.");
}

function displayAlerts(alerts) {

    const container = document.getElementById("alertCards");
    container.innerHTML = "";

    alerts.forEach(alert => {

        const card = document.createElement("div");
        card.className = "weather-card";
        card.style.border = "2px solid red";

        card.innerHTML = `
            <h3>🚨 ALERT</h3>

            <p><b>Type:</b> ${alert.alertType}</p>
            <p><b>Severity:</b> ${alert.severity}</p>

            <p>${alert.description}</p>

            <button class="edit-alert-btn">Edit</button>

            <button class="delete-alert-btn">Delete</button>

        `;

        card.querySelector(".edit-alert-btn").onclick = () => {

            document.getElementById("locationDropdown").value = alert.locationId;

            document.getElementById("adminSeverity").value = alert.severity;

            document.getElementById("adminDescription").value = alert.description;

            document.getElementById("alertDate").value = alert.alertDate;

            document.getElementById("alertId").value = alert.alertId;
        };

        card.querySelector(".delete-alert-btn").onclick = () => {

            if(confirm("Delete this alert?")) {

                deleteAlert(alert.alertId);
            }
         };

         container.appendChild(card);
    });

}

function changeAdminSection() {

    document.querySelectorAll(".admin-section").forEach(section => {
        section.style.display = "none";
    });

    const value = document.getElementById("adminType").value;

    if(value === "station") {
        document.getElementById("stationSection").style.display = "block";
    }

    else if (value === "location") {
        document.getElementById("locationSection").style.display = "block";
    }

    else if(value === "condition") {
        document.getElementById("conditionSection").style.display = "block";
    } 

    else {
        document.getElementById("alertSection").style.display = "block";
    }
}

function clearWeatherForm() {
    recordId.value = "";
    city.value = "";
    state.value = "";
    condition.value = "";
    temperature.value = "";
    humidity.value = "";
    date.value = "";
};

/* ================================= */
/* INIT */
/* ================================= */

window.onload = () => {
    loadWeatherUI();
    loadWeatherJSON();
    loadLocations();
    loadAlerts();
    changeAdminSection();

};
