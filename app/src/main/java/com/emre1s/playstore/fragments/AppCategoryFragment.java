package com.emre1s.playstore.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.emre1s.playstore.R;
import com.emre1s.playstore.adapters.AllCategoriesAdapter;
import com.emre1s.playstore.adapters.TopCategoryAdapter;
import com.emre1s.playstore.dagger.PageViewModelFactory;
import com.emre1s.playstore.ui.main.PageViewModel;

import javax.inject.Inject;

public class AppCategoryFragment extends Fragment {

    @Inject
    PageViewModelFactory pageViewModelFactory;

    private PageViewModel pageViewModel;

    public AppCategoryFragment() {

    }

    public static AppCategoryFragment newInstance(int position) {
        AppCategoryFragment forYouFragment = new AppCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        forYouFragment.setArguments(bundle);
        return forYouFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this,pageViewModelFactory).get(PageViewModel.class);
        int position = 1;
        if (getArguments() != null) {
            position = getArguments().getInt("position");
        }
        pageViewModel.getTabPosition().setValue(position);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_categories, container, false);

        int[] categoryIcons = new int[]{R.drawable.camera, R.drawable.star, R.drawable.music,
                R.drawable.domain, R.drawable.access_point_network, R.drawable.brush,
                R.drawable.book_open, R.drawable.forum};
        RecyclerView topCategories = view.findViewById(R.id.rv_top_categories);

        TopCategoryAdapter topCategoryAdapter = new TopCategoryAdapter(getResources().getStringArray(R.array.top_categories), categoryIcons);
        topCategories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
        topCategories.setAdapter(topCategoryAdapter);

        LinearSnapHelper pagerSnapHelper = new LinearSnapHelper();
        pagerSnapHelper.attachToRecyclerView(topCategories);

        PageViewModel pageViewModel = ViewModelProviders.of(this,pageViewModelFactory).get(PageViewModel.class);


        AllCategoriesAdapter allCategoriesAdapter = new AllCategoriesAdapter();
        RecyclerView allCategoriesRecyclerView = view.findViewById(R.id.rv_all_categories);
        allCategoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        allCategoriesRecyclerView.setAdapter(allCategoriesAdapter);

        pageViewModel.getTabPosition().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer tabPosition) {
                if (tabPosition == 0) {
                    allCategoriesAdapter.setCategories(pageViewModel.getGamesCategoryList().getCategoryList());
                } else {
                    allCategoriesAdapter.setCategories(pageViewModel.getAppCategoryList().getCategoryList());
                }
            }
        });



        return view;
    }
}
