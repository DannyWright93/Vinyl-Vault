Vinyl Vault
An Android app for managing your music collection using the Discogs API.
Features

Search releases via Discogs API
Manage your personal collection locally
View detailed release information (cover, tracklist, label, etc.)
Persistent local storage with Room database

Tech Stack

Language: Kotlin
UI: Jetpack Compose
Architecture: MVVM
DI: Hilt/Dagger
Database: Room
Network: Retrofit + OkHttp
Navigation: Navigation Compose
Async: Coroutines + Flow
Images: Coil

Requirements Fulfilled
Block 1: Navigation

3 screens with Jetpack Navigation
navArgument for releaseId

Block 2: MVVM

Clean MVVM architecture
Separation of concerns

Block 3: API Integration

Discogs API via Retrofit
Search and detail endpoints

Block 4: Room Database

Room as source of truth
CRUD operations with Flow

Block 5: Dependency Injection

Hilt/Dagger for all dependencies
Injected ViewModels

Setup

Clone repository
Get a Discogs API token from https://www.discogs.com/settings/developers
Add token in di/AppModule.kt:
.addQueryParameter("token", "YOUR_TOKEN")

Open project in Android Studio
Run on device or emulator (min SDK 26)

Usage

Tap + to search for releases
Select a release to view details
Tap heart icon to add to collection
Tap trash icon to remove from collection

Author
Danny Wright
Project Information
This project was developed as a final project for the Android Advanced course at FH Technikum Wien.
