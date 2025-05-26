package com.example.myapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {

    private Context context;
    private List<CategoryItem> categoryList;

    public CategoryAdapter(Context context, List<CategoryItem> list) {
        this.context = context;
        this.categoryList = list;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridItem = convertView;
        if (gridItem == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            gridItem = inflater.inflate(R.layout.item_category, parent, false);
        }

        ImageView icon = gridItem.findViewById(R.id.categoryIcon);
        TextView label = gridItem.findViewById(R.id.categoryText);

        CategoryItem item = categoryList.get(position);
        icon.setImageResource(item.getImageResId());
        label.setText(item.getName());

        return gridItem;
    }
}
