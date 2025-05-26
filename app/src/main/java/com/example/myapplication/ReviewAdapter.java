package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private List<ReviewItem> reviewList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textRating, textNickname, textCreatedAt, textContent, textProductName;
        LinearLayout keywordContainer;

        public ViewHolder(View view) {
            super(view);
            textRating = view.findViewById(R.id.textRating);
            textNickname = view.findViewById(R.id.textNickname);
            textCreatedAt = view.findViewById(R.id.textCreatedAt);
            textContent = view.findViewById(R.id.textContent);
            textProductName = view.findViewById(R.id.textProductName);
            keywordContainer = view.findViewById(R.id.keywordContainer);
        }

        public void bindKeywords(String[] keywords) {
            keywordContainer.removeAllViews();
            for (String keyword : keywords) {
                TextView tag = new TextView(itemView.getContext());
                tag.setText(keyword);
                tag.setTextSize(12);
                tag.setPadding(16, 8, 16, 8);
                tag.setBackgroundResource(R.drawable.sales_card_border);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 0, 16, 0);
                tag.setLayoutParams(params);
                keywordContainer.addView(tag);
            }
        }
    }

    public ReviewAdapter(List<ReviewItem> reviewList) {
        this.reviewList = reviewList;
    }

    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ReviewItem item = reviewList.get(position);
        holder.textRating.setText(String.valueOf(item.rating));
        holder.textNickname.setText(item.reviewerNickname);
        holder.textCreatedAt.setText(item.createdAt);
        holder.textContent.setText(item.content);
        holder.textProductName.setText(item.productName);
        holder.bindKeywords(item.keywords);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }
}
