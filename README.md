# 🔒 Secure E-Voting System

Hello there! Welcome to my full-stack electronic voting project. I built this to explore how a modern, secure, and user-friendly voting system could work, from the backend engine to the beautiful frontend you see.

It’s a complete application where users can register, cast their vote securely, and watch the results come in live. Plus, there’s a special dashboard for an administrator to manage the whole election. I had a lot of fun building it, and I hope you have fun exploring it!

![Homepage Screenshot](frontend/images/hero-bg.jpg)

---

## ✨ So, What Can It Do?

I've packed this system with features for both the voters and the person running the show.

### For Voters
* **Easy Registration**: A clean, simple form to get signed up for the election.
* **Secure Login**: Just need your unique Voter ID to get to the ballot.
* **Vote Just Once**: The system is smart enough to make sure every person gets one vote, and one vote only.
* **A Beautiful Ballot**: The voting page is designed to be clear and easy to use on any device.
* **Peace of Mind**: Get instant confirmation that your vote has been securely counted.

### For the Election Administrator
* **Private Control Room**: A login-protected dashboard to manage everything behind the scenes.
* **Manage Candidates**: Easily add new candidates to the ballot or remove them if needed.
* **See the Big Picture**: A live results page shows the vote count in real-time with clean, easy-to-read charts.
* **Fresh Start**: Got a new election coming up? A single button securely resets all votes and candidate data.

---

## 🛠️ What's Under the Hood?

I used a combination of powerful and modern technologies to bring this project to life:

* **The Brains (Backend)**: Java & Spring Boot, with Spring Security keeping things locked down.
* **The Memory (Database)**: An in-memory H2 database for easy setup and testing.
* **The Face (Frontend)**: Good old HTML, CSS, and JavaScript, with a modern design and some slick glassmorphism effects.
* **The Foundation (Build Tool)**: Maven to keep all the backend parts working together smoothly.

---

## 🚀 Want to Try It Out?

Getting this running on your own machine is pretty straightforward. Here’s how:

### What You'll Need
* Java (JDK 17 or newer)
* Maven (for the backend)
* A modern web browser (like Chrome or Firefox)
* A code editor (I used VS Code and IntelliJ)

### Let's Get It Running!

1.  **First, clone the project to your computer:**
    ```sh
    git clone [https://github.com/your-username/secure-e-voting-system.git](https://github.com/your-username/secure-e-voting-system.git)
    cd secure-e-voting-system
    ```
    *(Don't forget to replace `your-username` with your actual GitHub username!)*

2.  **Fire up the backend:**
    * Navigate into the backend folder: `cd e-voating-system`
    * Use Maven to start the application:
        ```sh
        ./mvnw spring-boot:run
        ```
    * You should see a bunch of logs, and then a message that the server has started on `http://localhost:8080`.

3.  **Launch the frontend:**
    * Open the `frontend` folder in your code editor.
    * The easiest way to run it is with a tool like the **Live Server** extension in VS Code. Just right-click on `index.html` and choose "Open with Live Server".
    * This will open the website in your browser at an address like `http://127.0.0.1:5500`.

And that's it! You're ready to register, vote, and see what you've built. Thanks for checking out my project!
