package com.example.chaekimjeo7.UI.main.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.chaekimjeo7.Model.CategoryItem;
import com.example.chaekimjeo7.R;
import com.example.chaekimjeo7.UI.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class CategoryEtcFragment extends Fragment {

    public CategoryEtcFragment() {
        // 기본 생성자
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category_etc, container, false);
        GridView gridView = view.findViewById(R.id.categoryEtcGrid);

        List<CategoryItem> categoryList = new ArrayList<>();
        categoryList.add(new CategoryItem("교양과목", R.drawable.ic_liberal_11, "etc"));
        categoryList.add(new CategoryItem("자격증", R.drawable.ic_certificate_12, "etc"));

        CategoryAdapter adapter = new CategoryAdapter(getContext(), categoryList);
        gridView.setAdapter(adapter);

        // ✅ 클릭 시 MainActivity의 onCategoryClicked() 호출
        gridView.setOnItemClickListener((parent, view1, position, id) -> {
            CategoryItem selected = categoryList.get(position);
            ((MainActivity) requireActivity()).onCategoryClicked(selected.getName());
        });

        return view;
    }
}
