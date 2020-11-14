package com.example.fakealiexpress.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fakealiexpress.models.Category;
import com.example.fakealiexpress.models.Item;

import java.util.ArrayList;
import java.util.List;

public class DBAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DBAccess instance;
    Cursor c = null;

    private DBAccess(Context context){
        this.openHelper = new DBHelper(context);
    }

    public static DBAccess getInstance(Context context){
        if (instance == null){
            instance = new DBAccess(context);
        }
        return instance;
    }

    public void open(){
        this.db = openHelper.getWritableDatabase();
    }

    public void close(){
        if (db != null){
            this.db.close();
        }
    }

    public List<Category> getCategories(){
        List<Category> categories = new ArrayList<>();
        c = db.rawQuery("SELECT * FROM CATEG_TABLE;", new String[]{});

        if(c.moveToFirst()){
            do{
                categories.add(new Category(
                        c.getInt(0),
                        c.getString(1),
                        c.getBlob(2)
                ));

            }
            while(c.moveToNext());
        }

        c.close();
        return categories;
    }

    public List<Item> getItems(int CATEGORY_ID) {
        List<Item> items = new ArrayList();
        c = db.rawQuery("SELECT * FROM ITEM_TABLE WHERE id_category = " + CATEGORY_ID + ';', new String[] {});
        if(c.moveToFirst()){
            do{
                items.add(new Item(
                        c.getInt(0),    //id
                        c.getString(1), //name
                        c.getBlob(2),   //image
                        c.getInt(3), //price
                        c.getInt(4)     //id_category
                ));

            }
            while(c.moveToNext());
        }
        c.close();
        return items;
    }

    public Item getItem(int ITEM_ID) {
        Item item = null;
        c = db.rawQuery("SELECT * FROM ITEM_TABLE WHERE _id = "+ ITEM_ID +';', new String[] {});
        if(c.moveToFirst()){
            do{
                item = new Item(
                        c.getInt(0),
                        c.getString(1),
                        c.getBlob(2),
                        c.getInt(3),
                        c.getInt(4)
                );

            }
            while(c.moveToNext());
        }

        c.close();
        return item;
    }

    public void putItemInBasket(int ITEM_ID){
        // Создайте новую строку со значениями для вставки.
        ContentValues newValues = new ContentValues();
        // Задайте значения для каждой строки.
        newValues.put("amount", 1);
        newValues.put("id_item", ITEM_ID);
        db.insert("BASKET_TABLE", null, newValues);
    }
}
