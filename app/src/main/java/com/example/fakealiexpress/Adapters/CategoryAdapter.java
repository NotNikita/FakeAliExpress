package com.example.fakealiexpress.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.solver.state.State;

import com.example.fakealiexpress.Models.CategoryListItem;
import com.example.fakealiexpress.R;

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

        View view=inflater.inflate(this.layout, parent, false);

        ImageView categoryView = (ImageView) view.findViewById(R.id.categImage);
        TextView nameView = (TextView) view.findViewById(R.id.categName);

        CategoryListItem state = states.get(position);

        categoryView.setImageResource(state.getImageResource());
        nameView.setText(state.getLabel());

        return view;
    }
}
