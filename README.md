# Food App developed in Java for Android

This is a food ordering app for Android. Users choose the products they want, set a desired quantity and manage a shopping cart saved in SQLite locally.

## Features

1. The main screen that contains the food categories and popular products
2. Product page with a selector for quantity and total price
3. Shopping cart saved in a SQLite database which can be adjusted according to the user's needs: add, update, remove quanity
4. Cart page that contains order summary and order checkout

## Technologies
Java, Android SDK, SQLite (SQLiteOpenHelper), RecyclerView, Glide

## App Structure

1. Activity - screens - Main, ShowDetail, Cart
2. Adapter - RecyclerView adapters
3. Domains - FoodDomain, CategoriesDomain
4. Helper - CartManager (cart logic), ShoppingCartDatabase(SQLite)
5. Interface - ChangeIndexItemsListener - updates the totals when a quantity changes

## Testing Process

1. Logcat - tested the database with logged cases
2. Android Studio Database Inspector - checked the stored data using SQL queries.
3. Tested manually the full application flow

## Run App

1. Clone the app from the GitHub repository.
2. Open it and run it in Android Studio

## Future Improvements
1. Create a login feature
2. Use REST API (Spring Boot, MySql) to serve the products instead os static data
3. Filter products by category and/or add more available products
