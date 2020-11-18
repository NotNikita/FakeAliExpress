package com.example.fakealiexpress;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fakealiexpress.activity.BasketActivity;
import com.example.fakealiexpress.activity.ShowItemsActivity;
import com.example.fakealiexpress.adapter.CategoryAdapter;
import com.example.fakealiexpress.databases.DBAccess;
import com.example.fakealiexpress.databases.DBHelper;
import com.example.fakealiexpress.models.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Category> categories = new ArrayList();
    ListView categList;
    FloatingActionButton fab;
    //Db
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

                Intent intent = new Intent(MainActivity.this, ShowItemsActivity.class);// MainActivity.this -> getApplicationContext()
                intent.putExtra("category_id", selectedCategory.getId());
                startActivity(intent);
            }
        };

        categList.setOnItemClickListener(itemListener);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BasketActivity.class);// MainActivity.this -> getApplicationContext()
                startActivity(intent);
            }
        });

        dbHelper = new DBHelper(this);

    }

    private void setCategoriesFromDb(){
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


    public boolean help_clicked(MenuItem item) {
        startActivity(new Intent(this, AboutApp.class));
        return super.onOptionsItemSelected(item);
    }

}