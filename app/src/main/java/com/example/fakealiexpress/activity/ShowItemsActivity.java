package com.example.fakealiexpress.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.fakealiexpress.R;
import com.example.fakealiexpress.adapter.ItemsAdapter2;
import com.example.fakealiexpress.databases.DBAccess;
import com.example.fakealiexpress.databases.DBHelper;
import com.example.fakealiexpress.models.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ShowItemsActivity extends Activity{

    public static int CATEGORY_ID = 1;
    private List<Item> itemsInCategory = new ArrayList();
    ListView itemsList;
    FloatingActionButton fab;
    //Db
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items);
        itemsList = findViewById(R.id.itemList);
        fab = findViewById(R.id.floating_action_button);
        CATEGORY_ID = getIntent().getExtras().getInt("category_id");

        setItemsFromDB();

        ItemsAdapter2 itemsAdapter2 = new ItemsAdapter2(this, R.layout.list_item, itemsInCategory);
        itemsList.setAdapter(itemsAdapter2);

        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // получаем выбранный пункт
                Item selectedItem= (Item) parent.getItemAtPosition(position);

                Intent intent = new Intent(ShowItemsActivity.this, ItemInfoActivity.class);
                intent.putExtra("item_id", selectedItem.getId());
                startActivity(intent);
            }
        };

        itemsList.setOnItemClickListener(itemListener);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        dbHelper = new DBHelper(this);
    }

    private void setItemsFromDB(){
        DBAccess dbAccess = DBAccess.getInstance(getApplicationContext());
        dbAccess.open();
        itemsInCategory = dbAccess.getItems(CATEGORY_ID);
        dbAccess.close();
    }

}
