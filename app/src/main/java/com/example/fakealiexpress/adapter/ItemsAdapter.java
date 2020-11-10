package com.example.fakealiexpress.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fakealiexpress.R;
import com.example.fakealiexpress.models.Item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {
    private List<Item> itemList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public ItemsAdapter(OnItemClickListener _onItemClickListener){
        this.onItemClickListener = _onItemClickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_category, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bind(itemList.get(position));
    }

    public void setItems(Collection<Item> users) {
        itemList.addAll(users);
        notifyDataSetChanged();
    }

    public void clearItems() {
        itemList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        final TextView nameTextView;
        final ImageView imageView;
        final TextView priceTextView;
        Bitmap bitmap;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.itemImage);
            nameTextView = itemView.findViewById(R.id.itemName);
            priceTextView = itemView.findViewById(R.id.itemPrice);
        }

        public void bind(Item item) {
            bitmap = BitmapFactory.decodeByteArray(item.getImage(), 0, item.getImage().length);
            nameTextView.setText(item.getName());
            priceTextView.setText(item.getPrice());
            imageView.setImageBitmap(bitmap);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

}
