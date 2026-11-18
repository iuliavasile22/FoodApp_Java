
package com.example.proiect.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.proiect.Domain.FoodDomain;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDatabase extends SQLiteOpenHelper {

    // Define the database name, version, and table/column names
    private static final String DATABASE_NAME = "shopping_cart.db";
    private static final int DATABASE_VERSION = 3;
    private static final String TABLE_ITEMS = "items";
    private static final String COLUMN_ITEM_ID = "id";
    private static final String COLUMN_ITEM_TITLE = "title";
    private static final String COLUMN_ITEM_PHOTO = "photo";
    private static final String COLUMN_ITEM_DESCRIPTION = "description";
    private static final  String COLUMN_ITEM_PRICE = "price";
    private static final String COLUMN_ITEM_FEE = "fee";
    private static final String COLUMN_ITEM_TYPE = "type";
    private static final String COLUMN_ITEM_CALORIES = "calories";
    private static final String COLUMN_ITEM_STAR = "star";
    private static final String COLUMN_ITEM_TIME = "time";

    // Constructor to create the database
    public ShoppingCartDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method to create the database table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_ITEMS + " (" +
                COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ITEM_TITLE + " TEXT, " +
                COLUMN_ITEM_PHOTO + " TEXT, " + // Store photo as string
                COLUMN_ITEM_DESCRIPTION + " TEXT, " +
                COLUMN_ITEM_TYPE +" TEXT," +
                COLUMN_ITEM_PRICE + "REAL, " +
                COLUMN_ITEM_FEE + " REAL, " +
                COLUMN_ITEM_STAR + " INTEGER ," +
                COLUMN_ITEM_CALORIES +" REAL ," +
                COLUMN_ITEM_TIME + " INTEGER)";
        db.execSQL(createTable);
    }

    // Method to upgrade the database version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }

    // Method to insert a food item into the database
    public void insertFoodItem(FoodDomain food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_TITLE, food.getTitle());
        values.put(COLUMN_ITEM_PHOTO, food.getPicFood()); // store photo as string
        values.put(COLUMN_ITEM_DESCRIPTION, food.getDescription());
        values.put(COLUMN_ITEM_TYPE, food.getType());
        values.put(COLUMN_ITEM_PRICE, food.getPrice());
        values.put(COLUMN_ITEM_FEE, food.getFee());
        values.put(COLUMN_ITEM_STAR, food.getStar());
        values.put(COLUMN_ITEM_CALORIES, food.getCalories());
        values.put(COLUMN_ITEM_TIME, food.getTime());
        db.insert(TABLE_ITEMS, null, values);
        db.close();
    }

    // Method to retrieve all food items from the database
    public ArrayList<FoodDomain> getAllFoodItems() {
        ArrayList<FoodDomain> foodList = new ArrayList<FoodDomain>();
        String selectQuery = "SELECT * FROM " + TABLE_ITEMS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
               String photo = cursor.getString(3);
                String description = cursor.getString(4);
                String type = cursor.getString(5);
                double price = cursor.getDouble(6);
                double fee = cursor.getDouble(7);
                int star = cursor.getInt(8);
                double calories = cursor.getDouble(9);
                int time = cursor.getInt(10);
                FoodDomain food = new FoodDomain(id, title, photo, description, type, price,fee,star, calories, time);
                foodList.add(food);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return foodList;
    }


    public void putListObject(String key, List<FoodDomain> list) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.beginTransaction();
            for (FoodDomain food : list) {
                insertFoodItem(food);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public ArrayList<Object> getListObject(String key) {
        ArrayList<Object> list = new ArrayList<Object>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ITEMS, new String[] { COLUMN_ITEM_TITLE, COLUMN_ITEM_DESCRIPTION, COLUMN_ITEM_FEE },
                COLUMN_ITEM_ID + "=?", new String[] { key }, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                byte[] data = cursor.getBlob(0);
                ObjectInputStream objectInputStream = null;
                try {
                    objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data));
                    Object object = objectInputStream.readObject();
                    list.add(object);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        objectInputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return list;
    }

}
