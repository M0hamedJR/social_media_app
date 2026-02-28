# 📱 Social Media App (JavaFX)

A fully functional social media application built using the **MVC
architecture** and the **DAO pattern** for data access.\
It provides a smooth user experience for managing friends and posts.

------------------------------------------------------------------------

## 🚀 Core Features

-   **Smart Friend Management:**\
    Send and receive friend requests with business logic that prevents
    the logged-in user from appearing in their own friend list.

-   **MySQL Database Integration:**\
    Structured relational database design including users and friendship
    relationships.

-   **Modern UI:**\
    Built with JavaFX and FXML, featuring a sidebar navigation system.

-   **Profile Viewing:**\
    View friends' profiles and interact with them.

------------------------------------------------------------------------

## 🛠️ Database Schema

The project uses a relational database model to manage user
relationships:

### 1️⃣ Users Table

Stores core user information: - `id` - `name` - `email` - `password`

### 2️⃣ Friends Table

Manages friendships using: - `user1_id` - `user2_id` - `created_at`

------------------------------------------------------------------------

## 💻 Tech Stack

-   **Language:** Java 17+
-   **UI Framework:** JavaFX (FXML)
-   **Database:** MySQL 8.0
-   **Version Control:** Git & GitHub

------------------------------------------------------------------------

## ⚙️ Quick Start

1.  Create the database using the provided SQL file.
2.  Configure JavaFX libraries in your IDE (IntelliJ or Eclipse).
3.  Update the `DatabaseConnection` class with your local database
    credentials.
4.  Run the main class `App.java`.

------------------------------------------------------------------------
