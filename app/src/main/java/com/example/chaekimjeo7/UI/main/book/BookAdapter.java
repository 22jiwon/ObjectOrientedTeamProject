package com.example.chaekimjeo7.UI.main.book;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chaekimjeo7.Model.Book;
import com.example.chaekimjeo7.R;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> originalList;     // 전체 리스트
    private List<Book> filteredList;     // 필터링된 리스트
    private Context context;

    public BookAdapter(List<Book> bookList, Context context) {
        this.originalList = new ArrayList<>(bookList);  // 원본 리스트 복사
        this.filteredList = new ArrayList<>(bookList);  // 처음엔 전체가 필터링된 리스트
        this.context = context;
    }

    // 필터 함수: 제목 또는 교수명에 키워드 포함 시 필터링
    public void filter(String keyword, boolean byTitle) {
        filteredList.clear();

        if (keyword.isEmpty()) {
            filteredList.addAll(originalList);
        } else {
            for (Book book : originalList) {
                if (byTitle && book.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                    filteredList.add(book);
                } else if (!byTitle && book.getProfessor().toLowerCase().contains(keyword.toLowerCase())) {
                    filteredList.add(book);
                }
            }
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = filteredList.get(position);

        holder.title.setText(book.getTitle());
        holder.salePrice.setText(book.getSalePrice() + "원");
        holder.originalPrice.setText("정가 " + book.getOriginalPrice() + "원");

        String meta = "시세 " + book.getMarketPrice() + "원 · " + book.getProfessor() + " · " + book.getCategory();
        holder.metaInfo.setText(meta);

        holder.image.setImageResource(book.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            Intent intent;
            if (book.isMyPost()) {
                intent = new Intent(context, BookSellDetailActivity.class);
            } else {
                intent = new Intent(context, BookPurchaseDetailActivity.class);
            }

            intent.putExtra("title", book.getTitle());
            intent.putExtra("price", String.valueOf(book.getSalePrice()));
            intent.putExtra("officialPrice", String.valueOf(book.getOriginalPrice()));
            intent.putExtra("description", book.getConditionDescription());  // ✅ 수정된 부분
            intent.putExtra("imageUri", "");
            intent.putExtra("professor", book.getProfessor());
            intent.putExtra("category", book.getCategory());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView title, salePrice, originalPrice, metaInfo;
        ImageView image;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.bookTitle);
            salePrice = itemView.findViewById(R.id.bookSalePrice);
            originalPrice = itemView.findViewById(R.id.bookOriginalPrice);
            metaInfo = itemView.findViewById(R.id.bookMetaInfo);
            image = itemView.findViewById(R.id.bookImage);
        }
    }
}
