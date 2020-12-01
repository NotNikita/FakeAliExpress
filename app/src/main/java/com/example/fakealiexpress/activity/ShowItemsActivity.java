package com.example.fakealiexpress.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.fakealiexpress.R;
import com.example.fakealiexpress.adapter.ItemsAdapter;
import com.example.fakealiexpress.databases.DBAccess;
import com.example.fakealiexpress.databases.DBHelper;
import com.example.fakealiexpress.models.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ShowItemsActivity extends Activity{

    private static final String TAG = "FakeAli";
    private static String CATEGORY_NAME = "Category";

    public static int CATEGORY_ID = 1;
    private List<Item> itemsInCategory = new ArrayList();
    ListView itemsList;
    FloatingActionButton fab;
    //Db
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "ShowItemsActivity: onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items);

        itemsList = findViewById(R.id.itemList);
        fab = findViewById(R.id.floating_action_button);
        CATEGORY_ID = getIntent().getExtras().getInt("category_id");
        CATEGORY_NAME = getIntent().getExtras().getString("category_name");
        setItemsFromDB();

        ItemsAdapter itemsAdapter = new ItemsAdapter(this, R.layout.list_item, itemsInCategory);
        itemsList.setAdapter(itemsAdapter);

        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // получаем выбранный пункт
                Item selectedItem= (Item) parent.getItemAtPosition(position);
                Log.d(TAG, "ShowItemsActivity: ListView item with name= " + selectedItem.getName() +" clicked");

                Intent intent = new Intent(ShowItemsActivity.this, ItemInfoActivity.class);
                intent.putExtra("item_id", selectedItem.getId());
                intent.putExtra("category_id", CATEGORY_ID);
                startActivity(intent);
            }
        };

        itemsList.setOnItemClickListener(itemListener);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "ShowItemsActivity: Floating Button clicked");
                final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.lawandorder);
                mediaPlayer.start();
                Intent intent = new Intent(ShowItemsActivity.this, BasketActivity.class);// MainActivity.this -> getApplicationContext()
                startActivity(intent);
            }
        });

        dbHelper = new DBHelper(this);
    }


    private void setItemsFromDB(){
        Log.d(TAG, "ShowItemsActivity: setting items from DB");
        DBAccess dbAccess = DBAccess.getInstance(getApplicationContext());
        dbAccess.open();
        itemsInCategory = dbAccess.getItems(CATEGORY_ID);
        dbAccess.close();
    }

}
