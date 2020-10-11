package com.example.fakealiexpress;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<CategoryListItem> {

    private LayoutInflater inflater;
    private int layout;
    private List<CategoryListItem> states;

    public CategoryAdapter(Context context, int resource, List<CategoryListItem> states) {
        super(context, resource, states);
        this.states = states;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        com.example.fakealiexpress.CategoryAdapter.ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new com.example.fakealiexpress.CategoryAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (com.example.fakealiexpress.CategoryAdapter.ViewHolder) convertView.getTag();
        }
        com.example.fakealiexpress.CategoryListItem state = states.get(position);

        viewHolder.imageView.setImageResource(state.getImageResource());
        viewHolder.nameView.setText(state.getLabel());

        return convertView;
    }

    private class ViewHolder {
        final ImageView imageView;
        final TextView nameView;

        ViewHolder(View view){
            imageView = view.findViewById(R.id.categImage);
            nameView = view.findViewById(R.id.categName);
        }
    }
}
