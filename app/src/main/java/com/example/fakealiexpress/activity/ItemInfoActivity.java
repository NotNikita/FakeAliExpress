package com.example.fakealiexpress.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.fakealiexpress.R;
import com.example.fakealiexpress.databases.DBAccess;
import com.example.fakealiexpress.databases.DBHelper;
import com.example.fakealiexpress.models.Item;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ItemInfoActivity  extends AppCompatActivity {
    final static String TAG = "FakeAli";

    public static int ITEM_ID = 1;
    public static int CATEGORY_ID = 1;
    //Working with Lists
    Item itemFromDB;
    int indexOfCurrentItem;
    private List<Item> itemsInCategory = new ArrayList();
    ListIterator<Item> iterator;

    private Button prevButton;
    private Button nextButton;
    private Button addToBucketButton;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ITEM_ID = getIntent().getExtras().getInt("item_id");
        CATEGORY_ID = getIntent().getExtras().getInt("category_id");
        Log.d(TAG, "OnCreate, before setContentView");
        setContentView(R.layout.activity_item_info);
        Log.d(TAG, "OnCreate, after setContentView");
        prevButton = findViewById(R.id.btnPrevFragment);
        nextButton = findViewById(R.id.btnNextFragment);
        addToBucketButton = findViewById(R.id.btnAddToBucket);

        loadItemInfo();
        setItemsFromDB();
        //Setting data
        Fragment fragmentWithArgs = new ItemInfoFragment();
        Bundle args = new Bundle();
        args.putByteArray("ARG_ITEM_IMAGE", itemFromDB.getImage());
        args.putString("ARG_ITEM_NAME", itemFromDB.getName());
        args.putInt("ARG_ITEM_PRICE", itemFromDB.getPrice());
        args.putString("ARG_ITEM_DESC", itemFromDB.getDescription());
        fragmentWithArgs.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerView, fragmentWithArgs, "fragment")
                .commit();
        //ItemInfoFragment.newInstance(itemFromDB.getImage(), itemFromDB.getName(), itemFromDB.getPrice(), itemFromDB.getDescription())

        prevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //some database function
                ItemInfoFragment fragment = (ItemInfoFragment) getSupportFragmentManager().findFragmentByTag("fragment");
                if (fragment != null){
                    if (iterator.hasPrevious()){
                        Item prevItem = iterator.previous();
                        fragment.updateValues(prevItem);
                    }
                    else
                    {
                        Snackbar.make(v, "No previous item", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                }
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //some database function
                ItemInfoFragment fragment = (ItemInfoFragment) getSupportFragmentManager().findFragmentByTag("fragment");
                if (fragment != null){
                    if (iterator.hasNext()){
                        Item nextItem = iterator.next();
                        fragment.updateValues(nextItem);
                    }
                    else
                    {
                        Snackbar.make(v, "No next item", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                }
            }
        });
        addToBucketButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //some database function
                DBAccess dbAccess = DBAccess.getInstance(getApplicationContext());
                dbAccess.open();
                dbAccess.putItemInBasket(ITEM_ID);
                dbAccess.close();
            }
        });

        dbHelper = new DBHelper(this);
    }


    private void loadItemInfo() {

        DBAccess dbAccess = DBAccess.getInstance(getApplicationContext());
        dbAccess.open();
        itemFromDB = dbAccess.getItem(ITEM_ID);
        dbAccess.close();
        Log.d(TAG, "ItemInfoActivity.loadItemInfo : itemFromDb successfully");
    }

    private void setItemsFromDB(){
        DBAccess dbAccess = DBAccess.getInstance(getApplicationContext());
        dbAccess.open();
        itemsInCategory = dbAccess.getItems(CATEGORY_ID);
        //its index in List of current element in our category List
        for (Item item : itemsInCategory) {
            if (item.getId() == itemFromDB.getId()){
                itemFromDB = item;
                break;
            }

        }
        indexOfCurrentItem = itemsInCategory.indexOf(itemFromDB);
        //setting iterator on position of current element
        iterator = itemsInCategory.listIterator(indexOfCurrentItem);
        dbAccess.close();
        Log.d(TAG, "ItemInfoActivity.setItemsFromDB : itemsInCategory successfully");
    }

}
