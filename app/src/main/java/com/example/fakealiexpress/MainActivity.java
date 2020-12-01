package com.example.fakealiexpress;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fakealiexpress.activity.AboutAppActivity;
import com.example.fakealiexpress.activity.BasketActivity;
import com.example.fakealiexpress.activity.ShowItemsActivity;
import com.example.fakealiexpress.adapter.CategoryAdapter;
import com.example.fakealiexpress.databases.DBAccess;
import com.example.fakealiexpress.databases.DBHelper;
import com.example.fakealiexpress.models.Category;
import com.example.fakealiexpress.services.NotificationService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "FakeAli";
    private List<Category> categories = new ArrayList();
    ListView categList;
    FloatingActionButton fab;
    //Db
    MediaPlayer mediaPlayer1;
    MediaPlayer mediaPlayer3;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate : MainActivity");
        setContentView(R.layout.activity_main);

        setMediaPlayers();
        mediaPlayer1.start();
        setCategoriesFromDb();

        // получаем элемент ListView
        categList = (ListView) findViewById(R.id.categList);
        fab = findViewById(R.id.floating_action_button);
        // создаем адаптер
        CategoryAdapter stateAdapter = new CategoryAdapter(this, R.layout.list_category, categories);
        // устанавливаем адаптер
        categList.setAdapter(stateAdapter);
        // слушатель выбора в списке

        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // получаем выбранный пункт
                Category selectedCategory = (Category)parent.getItemAtPosition(position);
                Log.d(TAG, "MainActivity: ListView category clicked");

                Intent intent = new Intent(MainActivity.this, ShowItemsActivity.class);// MainActivity.this -> getApplicationContext()
                intent.putExtra("category_id", selectedCategory.getId());
                intent.putExtra("category_name", selectedCategory.getLabel());
                startActivity(intent);
            }
        };

        categList.setOnItemClickListener(itemListener);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "MainActivity: Floating Button clicked");
                mediaPlayer3.start();
                Intent intent = new Intent(MainActivity.this, BasketActivity.class);// MainActivity.this -> getApplicationContext()
                startActivity(intent);
            }
        });

        dbHelper = new DBHelper(this);

    }

    private void setMediaPlayers() {
        mediaPlayer1 = MediaPlayer.create(this, R.raw.lasereffect);
        mediaPlayer3 = MediaPlayer.create(this, R.raw.lawandorder);

    }

    private void setCategoriesFromDb(){
        Log.d(TAG, "MainActivity: setting categories from DB");
        DBAccess dbAccess = DBAccess.getInstance(getApplicationContext());
        dbAccess.open();
        categories = dbAccess.getCategories();
        dbAccess.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // this adds items to action bar
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MainActivity: onDestroy()");
        DBAccess dbAccess = DBAccess.getInstance(getApplicationContext());
        dbAccess.open();
        dbAccess.setItemsColorsDefault();
        dbAccess.close();
        //Notification

        //Schedule Alarm Receiver in Main Activity
        startService(new Intent(this, NotificationService.class));
    }

    @Override
    protected void onStop () {
        super .onStop() ;
        Log.d(TAG, "MainActivity: onStop()");
        //startService( new Intent( this, NotificationService. class )) ;
    }


    public boolean help_clicked(MenuItem item) {
        Log.d(TAG, "MainActivity: About app clicked");
        startActivity(new Intent(this, AboutAppActivity.class));
        return super.onOptionsItemSelected(item);
    }

}