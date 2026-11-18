// Keep your original "package ..." line here (e.g. package com.example.proiect.Helper;)

package com.example.proiect.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.proiect.Domain.FoodDomain;


import java.util.ArrayList;
import java.util.List;

/**
 * Local SQLite database that stores the user's shopping cart.
 * Each row in the "items" table is one food item added to the cart.
 */
public class ShoppingCartDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "shopping_cart.db";
    // Bumped to 5: onUpgrade() rebuilds the table with the fixed price column and the new quantity column
    private static final int DATABASE_VERSION = 5;

    private static final String TABLE_ITEMS = "items";
    private static final String COLUMN_ITEM_ID = "id";
    private static final String COLUMN_ITEM_TITLE = "title";
    private static final String COLUMN_ITEM_PHOTO = "photo";
    private static final String COLUMN_ITEM_DESCRIPTION = "description";
    private static final String COLUMN_ITEM_TYPE = "type";
    private static final String COLUMN_ITEM_PRICE = "price";
    private static final String COLUMN_ITEM_FEE = "fee";
    private static final String COLUMN_ITEM_STAR = "star";
    private static final String COLUMN_ITEM_CALORIES = "calories";
    private static final String COLUMN_ITEM_TIME = "time";
    private static final String COLUMN_ITEM_QUANTITY = "quantity";   // how many of this item are in the cart

    public ShoppingCartDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_ITEMS + " (" +
                COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ITEM_TITLE + " TEXT, " +
                COLUMN_ITEM_PHOTO + " TEXT, " +          // drawable resource name, e.g. "burger"
                COLUMN_ITEM_DESCRIPTION + " TEXT, " +
                COLUMN_ITEM_TYPE + " TEXT, " +
                COLUMN_ITEM_PRICE + " REAL, " +           // FIX: space was missing before REAL
                COLUMN_ITEM_FEE + " REAL, " +
                COLUMN_ITEM_STAR + " INTEGER, " +
                COLUMN_ITEM_CALORIES + " REAL, " +
                COLUMN_ITEM_TIME + " INTEGER, " +
                COLUMN_ITEM_QUANTITY + " INTEGER DEFAULT 1)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Simple strategy for a cart: drop and recreate (cart contents are lost on upgrade)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }

    // ---------- INSERT ----------

    /** Adds one item to the cart. Returns the new row id, or -1 if the insert failed. */
    public long insertFoodItem(FoodDomain food) {
        SQLiteDatabase db = getWritableDatabase();
        return insertFoodItem(db, food);
    }

    /** Internal version that reuses an already-open database (needed inside transactions). */
    private long insertFoodItem(SQLiteDatabase db, FoodDomain food) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_TITLE, food.getTitle());
        values.put(COLUMN_ITEM_PHOTO, food.getPicFood());
        values.put(COLUMN_ITEM_DESCRIPTION, food.getDescription());
        values.put(COLUMN_ITEM_TYPE, food.getType());
        values.put(COLUMN_ITEM_PRICE, food.getPrice());
        values.put(COLUMN_ITEM_FEE, food.getFee());
        values.put(COLUMN_ITEM_STAR, food.getStar());
        values.put(COLUMN_ITEM_CALORIES, food.getCalories());
        values.put(COLUMN_ITEM_TIME, food.getTime());
        // A quantity of 0 or less makes no sense in a cart, so store at least 1
        values.put(COLUMN_ITEM_QUANTITY, Math.max(1, food.getIndexInCart()));
        return db.insert(TABLE_ITEMS, null, values);
    }

    /**
     * Replaces the whole cart with the given list, in a single transaction:
     * either all items are saved, or none are.
     * The "key" parameter is kept only so existing calls still compile; it is not used.
     */
    public void putListObject(String key, List<FoodDomain> list) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_ITEMS, null, null);
            for (FoodDomain food : list) {
                insertFoodItem(db, food);   // FIX: no longer closes the db mid-transaction
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    // ---------- SELECT ----------

    /** Returns every item currently in the cart. */
    public ArrayList<FoodDomain> getAllFoodItems() {
        ArrayList<FoodDomain> foodList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        // try-with-resources closes the cursor automatically
        try (Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ITEMS, null)) {
            // FIX: look columns up by name instead of hardcoded (and wrong) indices
            int idCol = cursor.getColumnIndexOrThrow(COLUMN_ITEM_ID);
            int titleCol = cursor.getColumnIndexOrThrow(COLUMN_ITEM_TITLE);
            int photoCol = cursor.getColumnIndexOrThrow(COLUMN_ITEM_PHOTO);
            int descCol = cursor.getColumnIndexOrThrow(COLUMN_ITEM_DESCRIPTION);
            int typeCol = cursor.getColumnIndexOrThrow(COLUMN_ITEM_TYPE);
            int priceCol = cursor.getColumnIndexOrThrow(COLUMN_ITEM_PRICE);
            int feeCol = cursor.getColumnIndexOrThrow(COLUMN_ITEM_FEE);
            int starCol = cursor.getColumnIndexOrThrow(COLUMN_ITEM_STAR);
            int caloriesCol = cursor.getColumnIndexOrThrow(COLUMN_ITEM_CALORIES);
            int timeCol = cursor.getColumnIndexOrThrow(COLUMN_ITEM_TIME);
            int quantityCol = cursor.getColumnIndexOrThrow(COLUMN_ITEM_QUANTITY);

            while (cursor.moveToNext()) {
                FoodDomain food = new FoodDomain(
                        cursor.getInt(idCol),
                        cursor.getString(titleCol),
                        cursor.getString(photoCol),
                        cursor.getString(descCol),
                        cursor.getString(typeCol),
                        cursor.getDouble(priceCol),
                        cursor.getDouble(feeCol),
                        cursor.getInt(starCol),
                        cursor.getDouble(caloriesCol),
                        cursor.getInt(timeCol));
                food.setIndexInCart(cursor.getInt(quantityCol));
                foodList.add(food);
            }
        }
        return foodList;
    }

    /** Number of items in the cart. */
    public int getItemCount() {
        SQLiteDatabase db = getReadableDatabase();
        try (Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_ITEMS, null)) {
            return cursor.moveToFirst() ? cursor.getInt(0) : 0;
        }
    }

    /** Total cart value (price x quantity for every item), computed by SQL. */
    public double getTotalPrice() {
        SQLiteDatabase db = getReadableDatabase();
        try (Cursor cursor = db.rawQuery(
                "SELECT COALESCE(SUM(" + COLUMN_ITEM_PRICE + " * " + COLUMN_ITEM_QUANTITY + "), 0) FROM "
                        + TABLE_ITEMS, null)) {
            return cursor.moveToFirst() ? cursor.getDouble(0) : 0;
        }
    }

    // ---------- DELETE ----------

    /** Removes one item from the cart by its row id. Uses a parameterized query ("?"). */
    public void deleteItem(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_ITEMS, COLUMN_ITEM_ID + " = ?", new String[]{String.valueOf(id)});
    }

    /** Empties the cart (e.g. after an order is placed). */
    public void clearCart() {
        getWritableDatabase().delete(TABLE_ITEMS, null, null);
    }

    /**
     * Kept so existing code that calls getListObject(...) still works.
     * Returns all items currently in the cart. The "key" parameter is not used.
     */
    public ArrayList<FoodDomain> getListObject(String key) {
        return getAllFoodItems();
    }
}
