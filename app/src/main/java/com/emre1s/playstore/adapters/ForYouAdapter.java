package com.emre1s.playstore.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.emre1s.playstore.R;
import com.emre1s.playstore.api.ApiResponseCallback;
import com.emre1s.playstore.listeners.OnCategoryChanged;
import com.emre1s.playstore.models.App;
import com.emre1s.playstore.models.CategoryList;
import com.emre1s.playstore.ui.main.PageViewModel;

import java.util.ArrayList;
import java.util.List;

public class ForYouAdapter extends RecyclerView.Adapter<ForYouAdapter.ViewHolder> {

    private Context context;

    private List<CategoryList.Category> categoryList;
    private PageViewModel pageViewModel;
    private OnCategoryChanged onCategoryChanged;

    public ForYouAdapter(Context context, PageViewModel pageViewModel, OnCategoryChanged onCategoryChanged) {
        this.context = context;
        this.pageViewModel = pageViewModel;
        categoryList = new ArrayList<>();
        this.onCategoryChanged = onCategoryChanged;
    }

    public void setCategoryNames(CategoryList categoryList) {
        this.categoryList = categoryList.getCategoryList();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_app_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.categoryName.setText(categoryList.get(position).getName());
        holder.categoryDetailsContainer.setOnClickListener(v -> {
            Log.d(ForYouAdapter.class.getSimpleName(), "clicked: " + categoryList.get(position).getId());
            onCategoryChanged.changeCategory(categoryList.get(position));
        });
        pageViewModel.makeCategoryApiCall(categoryList.get(position).getId(), new ApiResponseCallback() {
            @Override
            public void onSuccess(List<App> popularApp) {
                holder.container.setVisibility(View.VISIBLE);
                holder.appCardAdapter.setAppByCategoryApiResponse(popularApp);
            }

            @Override
            public void onFailure() {
                Log.d("Emre1s", "Data retrieval failure");
            }
        });
        //pageViewModel.getAppCategory().setValue(categoryNames[position]);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView container;
        ConstraintLayout categoryDetailsContainer;
        TextView categoryName;
        RecyclerView categoryApps;
        AppCardAdapter appCardAdapter = new AppCardAdapter(pageViewModel);
        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_name);
            categoryApps = itemView.findViewById(R.id.rv_app_cards);
            container = itemView.findViewById(R.id.cv_apps);
            categoryDetailsContainer = itemView.findViewById(R.id.category_details_container);
            categoryApps.setLayoutManager(new LinearLayoutManager(context,
                    LinearLayoutManager.HORIZONTAL, false));
            categoryApps.setAdapter(appCardAdapter);
            linearSnapHelper.attachToRecyclerView(categoryApps);
        }
    }
}
