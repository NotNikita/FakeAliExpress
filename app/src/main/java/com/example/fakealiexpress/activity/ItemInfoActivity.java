package com.example.fakealiexpress.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fakealiexpress.R;
import com.example.fakealiexpress.databases.DBAccess;
import com.example.fakealiexpress.databases.DBHelper;
import com.example.fakealiexpress.models.Item;

public class ItemInfoActivity  extends Activity {
    public static int ITEM_ID = 1;
    Item itemFromDB;

    private ImageView itemImageView;
    private TextView nameTextView;
    private TextView priceTextView;
    private TextView descriptionTextView;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ITEM_ID = getIntent().getExtras().getInt("item_id");

        setContentView(R.layout.activity_item_info);
        itemImageView = findViewById(R.id.item_image_view);
        nameTextView = findViewById(R.id.item_name_text_view);
        priceTextView = findViewById(R.id.item_price_text_view);
        descriptionTextView = findViewById(R.id.item_description_text_view);

        loadItemInfo();

        dbHelper = new DBHelper(this);
    }

    private void loadItemInfo() {

        DBAccess dbAccess = DBAccess.getInstance(getApplicationContext());
        dbAccess.open();
        itemFromDB = dbAccess.getItem(ITEM_ID);
        displayItemInfo();
        dbAccess.close();
    }

    private void displayItemInfo() {
        Bitmap bitmap = BitmapFactory.decodeByteArray(itemFromDB.getImage(), 0, itemFromDB.getImage().length);
        itemImageView.setImageBitmap(bitmap);
        nameTextView.setText(itemFromDB.getName());
        String s = String.valueOf(itemFromDB.getPrice()) + '$';
        priceTextView.setText(s);
        descriptionTextView.setText(itemFromDB.getDescription()); // item.getDescription()

    }
}
