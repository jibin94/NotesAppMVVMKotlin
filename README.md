## Notes App (MVVM + Room Database)
This is a simple Notes App built with Kotlin using the MVVM architecture pattern and Room Database for local data storage.

### What is Room?
Room is a persistence library provided by Google as part of the Android Jetpack suite.
It acts as an abstraction layer over SQLite, making it easier to work with databases in Android applications.

Instead of writing raw SQL queries and managing database connections manually, Room allows developers to interact with a database using high-level, type-safe objects and methods.

### ⚡ Why Room?
* Simplifies database operations by **reducing boilerplate code**.
* **Verifies SQL queries at compile time**, helping catch errors early.
* **Integrates easily with other Jetpack components** like LiveData and ViewModel.
* **Handles migrations safely** when the database schema changes.
* **Optimizes performance** through background threading and indexing.
* **Official support** by Google ensures reliability, updates, and best practices.

### 🛠 How Room Works

Room is built around three main components:

| Component                    | Description                                                                    |
|:-----------------------------|:-------------------------------------------------------------------------------|
| **Entity**                   | Represents a table in the database. (e.g., a `Notes` data class)               |
| **DAO (Data Access Object)** | Defines methods to interact with the database (insert, delete, update, query). |
| **Database**                 | The main access point to the database, holding references to DAOs.             |


