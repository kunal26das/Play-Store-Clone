package com.emre1s.playstore.app_details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emre1s.playstore.R;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class ScreenshotsAdapter extends RecyclerView.Adapter<ScreenshotsAdapter.ScreenshotHolder> {

    private LayoutInflater mLayoutInflater;
    private List<String> mScreenshots = Collections.emptyList();

    public ScreenshotsAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setScreenshots(List<String> screenshots) {
        mScreenshots = screenshots;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ScreenshotHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScreenshotHolder(mLayoutInflater.inflate(R.layout.item_screenshot, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScreenshotHolder screenshotHolder, int position) {
        screenshotHolder.updateScreenshot(mScreenshots.get(position));
        if (position == 0) {
            screenshotHolder.appScreenshot.setPadding(44, 0, 4, 0);
        } else if (position == mScreenshots.size() - 1) {
            screenshotHolder.appScreenshot.setPadding(4, 0, 44, 0);
        } else {
            screenshotHolder.appScreenshot.setPadding(4, 0, 4, 0);
        }
    }

    @Override
    public int getItemCount() {
        return mScreenshots.size();
    }

    class ScreenshotHolder extends RecyclerView.ViewHolder {

        ImageView appScreenshot;

        public ScreenshotHolder(@NonNull View view) {
            super(view);
            appScreenshot = view.findViewById(R.id.iv_app_screenshot);
        }

        public void updateScreenshot(String url) {
            Picasso.get().load(url).into(appScreenshot);
        }
    }
}