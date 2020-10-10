package com.example.fakealiexpress.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fakealiexpress.Adapters.CategoryAdapter;
import com.example.fakealiexpress.Models.CategoryListItem;
import com.example.fakealiexpress.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<CategoryListItem> categories = new ArrayList();

    ListView categList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // начальная инициализация списка
        setInitialData();
        // получаем элемент ListView
        categList = (ListView) findViewById(R.id.categList);
        // создаем адаптер
        CategoryAdapter stateAdapter = new CategoryAdapter(this, R.layout.list_item, categories);
        // устанавливаем адаптер
        categList.setAdapter(stateAdapter);
        // слушатель выбора в списке
        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // получаем выбранный пункт
                CategoryListItem selectedState = (CategoryListItem)parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Был выбран пункт " + selectedState.getLabel(),
                        Toast.LENGTH_SHORT).show();
            }
        };
        categList.setOnItemClickListener(itemListener);
    }

    private void setInitialData(){

        categories.add(new CategoryListItem ("Одежда", R.drawable.shirt));
        categories.add(new CategoryListItem ("Мобильные телефоны", R.drawable.smartphone));
        categories.add(new CategoryListItem ("Автомобили", R.drawable.car));
        categories.add(new CategoryListItem ("Украшения", R.drawable.jewelery));
        categories.add(new CategoryListItem ("Наручные часы", R.drawable.watch));

        categories.add(new CategoryListItem ("Компьютерная техника", R.drawable.desktop));
        categories.add(new CategoryListItem ("Обувь", R.drawable.shoes));
        categories.add(new CategoryListItem ("Дом и сад", R.drawable.house));
        categories.add(new CategoryListItem ("Электроника", R.drawable.electronic));
        categories.add(new CategoryListItem ("Красота и здоровье", R.drawable.beauty));

        categories.add(new CategoryListItem ("Спорт и развлечения", R.drawable.sport));
        categories.add(new CategoryListItem ("Хобби", R.drawable.hobby));
        categories.add(new CategoryListItem ("Продукты", R.drawable.products));
        categories.add(new CategoryListItem ("Бытовая техника", R.drawable.fridge));
        categories.add(new CategoryListItem ("Инструменты", R.drawable.instruments));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

}