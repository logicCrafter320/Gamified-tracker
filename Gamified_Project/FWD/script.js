// SWITCH FORMS
function showSignup() {
    document.getElementById("loginForm").classList.add("hidden");
    document.getElementById("signupForm").classList.remove("hidden");
}

function showLogin() {
    document.getElementById("signupForm").classList.add("hidden");
    document.getElementById("loginForm").classList.remove("hidden");
}


// SIGNUP
function signup() {

    let name = document.getElementById("signupName").value;
    let pass = document.getElementById("signupPass").value;

    if (name === "" || pass === "") {
        alert("Enter details");
        return;
    }

    let users = JSON.parse(localStorage.getItem("users")) || {};

    if (users[name]) {
        alert("User already exists");
        return;
    }

    users[name] = {
        password: pass,
        points: 0,
        history: []
    };

    localStorage.setItem("users", JSON.stringify(users));

    alert("Account created successfully!");

    showLogin();
}


// LOGIN
function login() {

    let name = document.getElementById("loginName").value;
    let pass = document.getElementById("loginPass").value;

    let users = JSON.parse(localStorage.getItem("users")) || {};

    if (!users[name] || users[name].password !== pass) {
        alert("Invalid credentials");
        return;
    }

    localStorage.setItem("currentUser", name);

    window.location = "dashboard.html";
}



// DASHBOARD INIT
function initDashboard() {

    let name = localStorage.getItem("currentUser");

    if (!name) {
        window.location = "login.html";
        return;
    }

    document.getElementById("welcome").innerText = "Welcome " + name + " 👋";

    updateUI();
    createChart();
}



// COMPLETE TASK
function completeTask() {

    let name = localStorage.getItem("currentUser");
    let users = JSON.parse(localStorage.getItem("users"));

    users[name].points += 10;
    users[name].history.push(users[name].points);

    localStorage.setItem("users", JSON.stringify(users));

    updateUI();
    createChart();
}



// UPDATE UI
function updateUI() {

    let name = localStorage.getItem("currentUser");
    let users = JSON.parse(localStorage.getItem("users"));

    let points = users[name].points;

    let level = Math.floor(points / 100) + 1;

    document.getElementById("points").innerText = points;
    document.getElementById("level").innerText = level;

    document.getElementById("badge").innerText = getBadge(points);

    document.getElementById("progress").style.width = (points % 100) + "%";

    updateLeaderboard(users);
}



// BADGE
function getBadge(points) {

    if (points >= 300) return "👑 Master";
    if (points >= 150) return "🏆 Achiever";
    if (points >= 50) return "⭐ Beginner";

    return "None";
}



// LEADERBOARD
function updateLeaderboard(users) {

    let board = document.getElementById("leaderboard");

    board.innerHTML = "";

    let sorted = Object.entries(users)
        .sort((a, b) => b[1].points - a[1].points);

    sorted.forEach(user => {
        board.innerHTML += `<li>${user[0]} — ${user[1].points} pts</li>`;
    });
}



// CHART
let chartInstance = null;

function createChart() {

    let name = localStorage.getItem("currentUser");
    let users = JSON.parse(localStorage.getItem("users"));

    let data = users[name].history;

    const ctx = document.getElementById('myChart');

    if (!ctx) return;

    if (chartInstance) chartInstance.destroy();

    chartInstance = new Chart(ctx, {
        type: 'line',
        data: {
            labels: data.map((_, i) => i + 1),
            datasets: [{
                label: 'Points Progress',
                data: data,
                tension: 0.3
            }]
        }
    });
}



// LOGOUT
function logout() {
    localStorage.removeItem("currentUser");
    window.location = "login.html";
}