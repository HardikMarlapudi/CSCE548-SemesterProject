const output = document.getElementById("output");

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

function loadWeather() {
    fetchData(
        "http://localhost:8081/weather",
        "Weather Service"
    );
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
