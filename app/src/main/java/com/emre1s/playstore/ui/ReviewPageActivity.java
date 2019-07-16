package com.emre1s.playstore.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.emre1s.playstore.R;
import com.emre1s.playstore.adapters.ReviewAdapter;
import com.emre1s.playstore.api.ReviewResponseCallback;
import com.emre1s.playstore.models.Review;
import com.emre1s.playstore.ui.main.PageViewModel;

import java.util.List;

public class ReviewPageActivity extends AppCompatActivity implements ReviewResponseCallback {
    private ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_page);
        PageViewModel pageViewModel= ViewModelProviders.of(this).get(PageViewModel.class);

        RecyclerView reviewRecyclerView = findViewById(R.id.review_rv);
        reviewAdapter=new ReviewAdapter();

        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewRecyclerView.setAdapter(reviewAdapter);

        Intent intent = getIntent();
        String id = "";
        if (intent.getExtras() != null) {
            id = intent.getStringExtra("id");
            pageViewModel.makeReviewsApiCall(id, this);
        }

    }

    @Override
    public void onSuccess(List<Review> reviews) {
        reviewAdapter.setReviewList(reviews);
    }

    @Override
    public void onFailure() {

    }
}
