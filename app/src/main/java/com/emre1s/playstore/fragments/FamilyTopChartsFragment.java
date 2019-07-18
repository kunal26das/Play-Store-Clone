package com.emre1s.playstore.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emre1s.playstore.R;
import com.emre1s.playstore.adapters.TopChartsAdapter;
import com.emre1s.playstore.api.ApiResponseCallback;
import com.emre1s.playstore.models.App;
import com.emre1s.playstore.ui.main.PageViewModel;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.List;

public class FamilyTopChartsFragment extends Fragment {


    public static final String CATEGORY_KEY = "category";
    public static final String DEFAULT_COLLECTION = "topselling_free";
    public static final String CATEGORY_FAMILY = "FAMILY";
    public static final String ITEM_LIMIT_KEY = "itemLimit";
    public static final int DEFAULT_ITEM_LIMIT = 1;
    private PageViewModel pageViewModel;
    private int limit;

    public FamilyTopChartsFragment() {
    }

    public static Fragment getInstance(String category, int limit) {
        FamilyTopChartsFragment topChartsFragment = new FamilyTopChartsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEM_LIMIT_KEY, limit);
        bundle.putString(CATEGORY_KEY, category);
        topChartsFragment.setArguments(bundle);
        return topChartsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this,null).get(PageViewModel.class);
        String category = DEFAULT_COLLECTION;
        limit = DEFAULT_ITEM_LIMIT;
        if (getArguments() != null) {
            category = getArguments().getString(CATEGORY_KEY);
            limit = getArguments().getInt(ITEM_LIMIT_KEY);
        }
        pageViewModel.getFamilyTopChartsCategory().setValue(category);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_top_charts, container, false);
        CircularProgressBar circularProgressBar = root.findViewById(R.id.pb_top_charts);
        circularProgressBar.setVisibility(View.VISIBLE);
        RecyclerView appList = root.findViewById(R.id.app_list);
        final TopChartsAdapter topChartsAdapter = new TopChartsAdapter();

        appList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        appList.setAdapter(topChartsAdapter);
        appList.setHasFixedSize(true);

        PageViewModel pageViewModel =
                ViewModelProviders.of(this, null).get(PageViewModel.class);
        pageViewModel.getFamilyTopChartsCategory().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String collection) {
                pageViewModel.makeCategoryCollectionApiCall(collection,
                        CATEGORY_FAMILY, new ApiResponseCallback() {
                    @Override
                    public void onSuccess(List<App> popularApp) {
                        Log.d("Success", popularApp.size() + "");
                        if (limit != 3) {
                            topChartsAdapter.setmList(popularApp);
                        } else {
                            topChartsAdapter.setmList(popularApp.subList(0, 3));
                        }
                        circularProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure() {
                        Log.d("onEmptyResponse",
                                "Returned empty response");
                    }
                });
            }
        });


        return root;
    }
}