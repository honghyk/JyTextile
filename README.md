# Fabric Roll Stock Management System

A cross-platform inventory management system built with Kotlin Multiplatform (KMM), designed to manage fabric roll inventory in a small business environment.

## Overview
This app was developed to support stock management for my family’s textile company. It allows staff to easily record fabric roll entries, track stock, and search rolls.  
Currently, the project is in early stage of development and contains minimum features:
- In/Out Management: Record incoming and outgoing fabric rolls with essential details
- Search: Look up rolls by id, item number or order number

## Tech Stack
- Kotlin Multiplatform (KMM) - Shared business logic across Android, iOS and Desktop
- Compose Multiplatform - UI framework for building cross-platform apps
- Room – Local database for offline storage
- Ktor - HTTP client for networking and potential backend communication
- [kotlin-inject](https://github.com/evant/kotlin-inject) – Dependency injection
• [circuit](https://github.com/slackhq/circuit) - UI architecture framework for Compose, handling navigation and presentation logic
- [Store5](https://github.com/MobileNativeFoundation/Store) - Data loading and caching layer, simplifying offline and network data access
- [kermit](https://github.com/touchlab/Kermit) - Cross-platform logging library

## 🚧In Progress
- Export inventory data as CSV
- Authentication and user management
- Add support for Android and iOS apps (currently only supports Desktop)
- Set up proper distribution for each platform
