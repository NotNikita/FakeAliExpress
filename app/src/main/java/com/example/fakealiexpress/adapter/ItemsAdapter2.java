package com.example.fakealiexpress.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fakealiexpress.R;
import com.example.fakealiexpress.models.Item;

import java.util.List;


public class ItemsAdapter2 extends ArrayAdapter<Item> {
    private LayoutInflater inflater;
    private int layout;
    private List<Item> itemList;

    public ItemsAdapter2(Context context, int resource, List<Item> itemList) {
        super(context, resource, itemList);
        this.itemList = itemList;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Bitmap bitmap;

        ItemsAdapter2.ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ItemsAdapter2.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ItemsAdapter2.ViewHolder) convertView.getTag();
        }
        Item state = itemList.get(position);

        bitmap = BitmapFactory.decodeByteArray(state.getImage(), 0, state.getImage().length);
        viewHolder.imageView.setImageBitmap(bitmap);
        viewHolder.nameTextView.setText(state.getName());
        viewHolder.priceTextView.setText(String.valueOf(state.getPrice()) + "$");

        return convertView;
    }

    private class ViewHolder {
        final TextView nameTextView;
        final ImageView imageView;
        final TextView priceTextView;

        ViewHolder(View view){
            imageView = view.findViewById(R.id.itemImage);
            nameTextView = view.findViewById(R.id.itemName);
            priceTextView = view.findViewById(R.id.itemPrice);
        }
    }


}
