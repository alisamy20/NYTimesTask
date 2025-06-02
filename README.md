# Kotlin News - Android Application

This Android application is built using **Clean Architecture** with **MVI (Model-View-Intent)** as the state management pattern. The app fetches Nytime  News JSON feeds, displays them in a list, and allows users to interact with the artciles. It is divided into modules to ensure scalability and maintainability. We are using **Kotlin** as the programming language and **Git Flow** for version control management.

## App Features

### 1. **Home Module**
   - **Fetches artciles from Nytime API**: The Home module retrieves a list of articles from a Nytime News JSON feed via API.
   - **Local Database Caching**: In case of no network connectivity, the app fetches saved artciles from the local database.
   - **Bookmarking**: Users can add artciles to their bookmarks (favorites).
   - **Search**: artciles can be searched by title.

### 2. **book mark Module**
   - **Displays Saved articles**: The Book mark module contains articles that the user has saved from the Home module.
   - **Manages Bookmarked articles**: Users can view their favorite articles from this module.

### 3. **Details Module**
   - **Handles Article Navigation**: This module is responsible for displaying the full article when a user taps on a article from either the Home or book mark module.
   - **Supports Navigation**: Navigates from the main view (Home or Book mark) to a detailed article view and vice versa.

### 4. **Common Feature**
   - This module contains shared functionality, like networking utilities, database handling, and common UI components, used across all other modules.

## Clean Architecture

The app follows **Clean Architecture**, which is designed to separate the code into layers for better maintainability, testability, and scalability. These layers include:

- **Presentation Layer**: This includes the View and ViewModel which are responsible for displaying data to the user and handling UI logic. 
- **Domain Layer**: Contains business logic and entities, often referred to as use cases. The domain layer is independent of other layers and should only know about the data needed to execute the use cases.
- **Data Layer**: This layer handles data fetching, whether it's from a local database or remote API. It contains repositories that manage data from these sources.

The app uses **MVI (Model-View-Intent)** as the state management pattern, where:
- **Model** represents the application's state.
- **View** represents the UI.
- **Intent** represents user actions that can change the state.

## Coroutines with Flow

I use **Kotlin Coroutines** with **Flow** for asynchronous programming and state management because:
- **Lightweight Threads**: Coroutines are lightweight and do not block the thread, enabling us to handle background tasks efficiently and prevent UI freezes.
-  **Flow in Coroutines** : you can handle streams of data and events just like RxJava, but with simpler integration and less overhead.
  

## Git Flow

I follow the **Git Flow** branching model to maintain an organized version control system. It helps with managing feature development, releases, and hotfixes in a structured manner. The key branches are:
- **`master`**: Contains the production-ready code.
- **`develop`**: Contains the latest development changes.
- **`feature/*`**: Feature branches created for each new feature.

## Key Features

- **Lazy Column for Article List**: The main screen displays a list of articles retrieved from Ny time. Each list item shows the article title and, if available, a thumbnail image. The items are dynamically resized based on whether an image is present.
- **Full Article View**: Tapping on an article opens a detailed view, showing the full content of the article, including its thumbnail image if available.
- **Navigation**: Users can navigate back and forth between the main article list and detailed article views.
- **Search & Bookmark**: Users can search for articles by title and bookmark articles to view later in the Favourite module.


## Dependencies

- **Retrofit**: For networking and fetching data from the NyTime API.
- **Room**: For local database storage to save articles when there is no network.
- **Coroutines**: For asynchronous programming and background tasks.
- **StateFlow & SharedFlow**: For managing state and communication between components using Kotlin's Flow API.
- **Dagger Hilt**: For dependency injection and managing app's dependencies.
- **Jetpack Compose**: For building modern, UI components using a declarative approach.
- **MockK**: For mocking objects in unit tests.
- **ViewModel**: To manage UI-related data lifecycle-consciously.
- **MVI Pattern**: For managing the app's architecture using the Model-View-Intent pattern.
- **Koil**: For image loading and caching .

## KSP and DSL Gradle

This project uses **Kotlin Symbol Processing (KSP)** for annotation processing. KSP is faster and more efficient than traditional **KAPT** (Kotlin Annotation Processing Tool), and it is designed specifically for Kotlin. KSP improves build times by processing annotations during compilation and works well with libraries like **Dagger Hilt** for dependency injection.

We also use **DSL Gradle** to configure the build system. **Kotlin DSL** allows for writing Gradle build scripts in Kotlin, making the build configuration more readable and type-safe.

## Testing


### 1. **Unit Testing for Home View Model **






