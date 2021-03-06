package com.example.fakealiexpress.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.fakealiexpress.models.BasketModel;
import com.example.fakealiexpress.models.Category;
import com.example.fakealiexpress.models.Item;
import com.example.fakealiexpress.models.ItemFullModel;

import java.util.ArrayList;
import java.util.List;

public class DBAccess {
    private static String TAG = "FakeAli";

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
        Log.d(TAG, "DataBase : get Categories from DB");
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
        Log.d(TAG, "DataBase : get Items from DB");
        List<Item> items = new ArrayList();
        c = db.rawQuery("SELECT * FROM ITEM_TABLE WHERE id_category = " + CATEGORY_ID + ';', new String[] {});
        if(c.moveToFirst()){
            do{
                items.add(new Item(
                        c.getInt(0),    //id
                        c.getString(1), //name
                        c.getBlob(2),   //image
                        c.getInt(3),    //price
                        c.getInt(4),     //id_category
                        (c.getInt( 5) == 1) //isViewed
                ));

            }
            while(c.moveToNext());
        }
        c.close();
        return items;
    }

    public List<BasketModel> getItemsFromBacket(){
        Log.d(TAG, "DataBase : get Basket items from DB");
        List<BasketModel> items = new ArrayList();
        c = db.rawQuery("SELECT * FROM BASKET_TABLE;", new String[] {});
        if(c.moveToFirst()){
            do{
                items.add(new BasketModel(
                        c.getInt(0),    //_id
                        c.getString(1), //name
                        c.getInt(2),    //amount
                        c.getBlob(3),   //image
                        c.getInt(4),    //price
                        c.getInt(5)     //id_item
                ));

            }
            while(c.moveToNext());
        }
        c.close();
        return items;
    }

    public Item getItem(int ITEM_ID) {
        Log.d(TAG, "DataBase : get Item with id="+ITEM_ID+" from DB");
        Item item = null;
        c = db.rawQuery("SELECT * FROM ITEM_TABLE WHERE _id = "+ ITEM_ID +';', new String[] {});
        if(c.moveToFirst()){
            do{
                item = new Item(
                        c.getInt(0),
                        c.getString(1),
                        c.getBlob(2),
                        c.getInt(3),
                        c.getInt(4),
                        (c.getInt( 5) == 1) //isViewed
                );

            }
            while(c.moveToNext());
        }

        c.close();
        return item;
    }

    public void putItemInBasket(Item item, int amount){
        //узнаем есть ли уже элемент в корзине
        c = db.rawQuery("SELECT COUNT(*) FROM BASKET_TABLE WHERE id_item = "+ item.getId() +";", new String[] {});
        int itemExist = 0;
        if(c.moveToFirst()){
            do{
                itemExist = c.getInt(0);
            }
            while(c.moveToNext());
        }
        if (itemExist == 0) // Значит такого предмета еще нет в таблице
        {
            Log.d(TAG, "DataBase : There isnt any items with id=" + item.getId());
            ContentValues newValues = new ContentValues();
            // Задайте значения для каждой строки.
            newValues.put("name", item.getName());
            newValues.put("amount", amount);
            newValues.put("image", item.getImage());
            newValues.put("price", item.getPrice());
            newValues.put("id_item", item.getId());
            db.insert("BASKET_TABLE", null, newValues);
        }
        else if (itemExist == 1) //товар уже находится в корзине
        {
            Log.d(TAG, "Basket was read successfully. There is some items with id=" + item.getId());
            db.execSQL("UPDATE BASKET_TABLE SET amount  = " + amount + " where id_item = "+ item.getId() +";");
        }
        Log.d(TAG, "Basket was read successfully. Item with id=" + item.getId() + " was putted");
    }

    public void putItemInBasket(BasketModel item){
        int newAmount = item.getAmount() + 1;
        db.execSQL("UPDATE BASKET_TABLE SET amount  = " + newAmount + " where id_item = "+ item.getId_item() +";");
        Log.d(TAG, "Basket was read successfully. Item with id=" + item.getId() + " was putted");
    }

    public void removeItemFromBasket(int id) {
        db.execSQL("DELETE FROM BASKET_TABLE WHERE _id = " + id + ";");
        Log.d(TAG, "Item with id = " + id + " was removed from basket");
    }

    public void itemWasViewedChangeColor(int ITEM_ID) {
        db.execSQL("UPDATE ITEM_TABLE SET isViewed  = " + 1 + " where _id = "+ ITEM_ID +";");
        Log.d(TAG, "Color was set to RED. Item with id=" + ITEM_ID);
    }

    public void setItemsColorsDefault() {
        db.execSQL("UPDATE ITEM_TABLE SET isViewed  = " + 0 + " where isViewed = "+ 1 +";");
        Log.d(TAG, "All colors was set to default BLACK.");
    }

    public List<ItemFullModel> getCategoryPlusItems(){
        Log.d(TAG, "DataBase : getting PriceList from DB");
        List<ItemFullModel> items = new ArrayList();
        ItemFullModel item = null;
        c = db.rawQuery("SELECT name, image, price, categ_name\n" +
                "FROM ITEM_TABLE\n" +
                "    [INNER] JOIN CATEG_TABLE\n" +
                "    ON CATEG_TABLE._id = id_category;", new String[] {});
        if(c.moveToFirst()){
            do{
                items.add( new ItemFullModel(
                        c.getString(0),
                        c.getBlob(1),
                        c.getInt(2),
                        c.getString(3)
                ));

            }
            while(c.moveToNext());
        }

        c.close();
        return items;
    }
}
