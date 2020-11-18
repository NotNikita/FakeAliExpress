package com.example.fakealiexpress.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fakealiexpress.OnSwipeTouchListener;
import com.example.fakealiexpress.R;
import com.example.fakealiexpress.databases.DBAccess;
import com.example.fakealiexpress.databases.DBHelper;
import com.example.fakealiexpress.models.BasketModel;

import java.util.List;


public class BasketAdapter extends ArrayAdapter<BasketModel>{
    OnSwipeTouchListener onSwipeTouchListener;
    //
    private LayoutInflater inflater;
    private int layout;
    private List<BasketModel> itemList;
    private DBHelper dbHelper;

    public BasketAdapter(Context context, int resource, List<BasketModel> itemList) {
        super(context, resource, itemList);
        this.itemList = itemList;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);

        dbHelper = new DBHelper(context);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        Bitmap bitmap;

        final BasketAdapter.ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new BasketAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (BasketAdapter.ViewHolder) convertView.getTag();
        }
        final BasketModel basketModel = itemList.get(position);

        bitmap = BitmapFactory.decodeByteArray(basketModel.getImage(), 0, basketModel.getImage().length);
        viewHolder.imageView.setImageBitmap(bitmap);
        viewHolder.nameTextView.setText(basketModel.getName());
        viewHolder.priceTextView.setText(String.valueOf(basketModel.getPrice()) + "$");
        viewHolder.amountTextView.setText(String.valueOf(basketModel.getAmount()));

        viewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBAccess dbAccess = DBAccess.getInstance(getContext());
                dbAccess.open();
                dbAccess.putItemInBasket(itemList.get(position));
                dbAccess.close();
                basketModel.setAmount(basketModel.getAmount() + 1);
                viewHolder.amountTextView.setText(String.valueOf(basketModel.getAmount()));
            }
        });
        viewHolder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBAccess dbAccess = DBAccess.getInstance(getContext());
                dbAccess.open();
                dbAccess.removeItemFromBasket(itemList.get(position).getId());
                dbAccess.close();
                itemList.remove(position);
                BasketAdapter.this.notifyDataSetChanged();
            }
        });

        onSwipeTouchListener = new OnSwipeTouchListener(getContext()) {
            @Override
            public void onSwipeRight() {
                Toast.makeText(getContext(), itemList.get(position).getName() + " right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeLeft() {
                Toast.makeText(getContext(), itemList.get(position).getName() + " left", Toast.LENGTH_SHORT).show();
                DBAccess dbAccess = DBAccess.getInstance(getContext());
                dbAccess.open();
                dbAccess.removeItemFromBasket(itemList.get(position).getId());
                dbAccess.close();
                itemList.remove(position);
                BasketAdapter.this.notifyDataSetChanged();
            }
        };
        convertView.setOnTouchListener(onSwipeTouchListener);


        return convertView;
    }

    private class ViewHolder {
        final TextView nameTextView;
        final TextView amountTextView;
        final ImageView imageView;
        final TextView priceTextView;
        final ImageButton imageButton;
        final ImageButton removeButton;

        ViewHolder(View view){
            imageView = view.findViewById(R.id.basketImage);
            nameTextView = view.findViewById(R.id.basketName);
            amountTextView = view.findViewById(R.id.basketAmount);
            priceTextView = view.findViewById(R.id.basketPrice);
            imageButton = view.findViewById(R.id.basketBtn);
            removeButton = view.findViewById(R.id.removeBasketBtn);
        }
    }

}
