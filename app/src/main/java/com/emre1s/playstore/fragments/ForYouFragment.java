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
import com.emre1s.playstore.adapters.ForYouAdapter;
import com.emre1s.playstore.api.AppByCategoryCallback;
import com.emre1s.playstore.api.AppByCategoryFactory;
import com.emre1s.playstore.listeners.OnAppClickedListener;
import com.emre1s.playstore.models.AppByCategoryApiResponse;
import com.emre1s.playstore.ui.main.PageViewModel;

public class ForYouFragment extends Fragment implements OnAppClickedListener {

    public ForYouFragment() {

    }

    public static ForYouFragment newInstance() {
        ForYouFragment forYouFragment = new ForYouFragment();
        return forYouFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_for_you, container, false);
        RecyclerView forYouRecycler = view.findViewById(R.id.rv_for_you);
        PageViewModel pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        ForYouAdapter forYouAdapter = new ForYouAdapter(getContext(),pageViewModel, pageViewModel.getAllCategories());

        forYouRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        forYouRecycler.setAdapter(forYouAdapter);
        Log.d("Emre1s", "ForYouFragment was called");

        pageViewModel.getReceivedAppLiveData().observe(this, new Observer<AppByCategoryApiResponse>() {
            @Override
            public void onChanged(AppByCategoryApiResponse appByCategoryApiResponse) {
                Log.d("Emre1s", "App received: " + appByCategoryApiResponse.getTitle());
            }
        });
        return view;
    }

    @Override
    public void showAppDetail(AppByCategoryApiResponse appByCategoryApiResponse) {

    }
}
