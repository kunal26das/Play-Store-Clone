package com.emre1s.playstore.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.emre1s.playstore.R;
import com.emre1s.playstore.api.RetrofitApiFactory;
import com.emre1s.playstore.api.SearchResponseCallback;
import com.emre1s.playstore.apps_installed_list.InstalledAppsActivity;
import com.emre1s.playstore.listeners.OnShowAllClickedListener;
import com.emre1s.playstore.models.CategoryList;
import com.emre1s.playstore.models.TabList;
import com.emre1s.playstore.ui.main.PageViewModel;
import com.emre1s.playstore.ui.main.SectionsPagerAdapter;
import com.facebook.stetho.Stetho;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnShowAllClickedListener {


    private FloatingSearchView searchView;
    private static final String TAG = MainActivity.class.getSimpleName();
    private PageViewModel pageViewModel;

    //private BottomSheetBehavior bottomSheetBehavior;

    public static void hideSoftKeyboard(Activity activity) {
        final InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            if (activity.getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);

        searchView = findViewById(R.id.floating_search_view);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        initializeCategories(inputStreamToString(getResources().openRawResource(R.raw.apps)),
                inputStreamToString(getResources().openRawResource(R.raw.family)),
                inputStreamToString(getResources().openRawResource(R.raw.games)),
                inputStreamToString(getResources().openRawResource(R.raw.apps_top_categories)));


        String myJson = inputStreamToString(getResources().openRawResource(R.raw.apps));
        Log.d(TAG, myJson);

        String familyJson = inputStreamToString(getResources().openRawResource(R.raw.family));
        Log.d(TAG, familyJson);

        String gamesJson = inputStreamToString(getResources().openRawResource(R.raw.games));
        Log.d(TAG, gamesJson);

        CategoryList appsCategory = new Gson().fromJson(myJson, CategoryList.class);
        CategoryList familyCategory = new Gson().fromJson(familyJson, CategoryList.class);
        CategoryList gamesCategory = new Gson().fromJson(gamesJson, CategoryList.class);
        RetrofitApiFactory.setAppCategories(appsCategory);
        RetrofitApiFactory.setFamilyCategories(familyCategory);
        RetrofitApiFactory.setGameCategories(gamesCategory);

        for (CategoryList.Category category : appsCategory.getCategoryList()) {
            Log.d(TAG, " App Category Name: " + category.getName() + " ID: " + category.getId());
        }

        for (CategoryList.Category category : familyCategory.getCategoryList()) {
            Log.d(TAG, "Family Category name: " + category.getName() + category.getId());
        }

        for (CategoryList.Category category : gamesCategory.getCategoryList()) {
            Log.d(TAG, "Game Category name: " + category.getName() + category.getId());
        }

        SectionsPagerAdapter sectionsPagerAdapter = new
        SectionsPagerAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setOffscreenPageLimit(5);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabs.getTabAt(1).select();

        AppBarLayout appBarLayout = findViewById(R.id.appBar);
        View searchViewBackground = findViewById(R.id.searchBarBackground);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                searchView.setTranslationY(verticalOffset);
            }
        });
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: {
                        appBarLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        searchViewBackground.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Window window = getWindow();
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
                        }
                        break;
                    }
                    case 1: {
                        appBarLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        searchViewBackground.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Window window = getWindow();
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
                        }
                        break;
                    }
                    case 2: {
                        appBarLayout.setBackgroundColor(getResources().getColor(R.color.colorMovies));
                        searchViewBackground.setBackgroundColor(getResources().getColor(R.color.colorMovies));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Window window = getWindow();
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(getResources().getColor(R.color.colorMovies));
                        }
                        break;
                    }
                    case 3: {
                        appBarLayout.setBackgroundColor(getResources().getColor(R.color.colorBooks));
                        searchViewBackground.setBackgroundColor(getResources().getColor(R.color.colorBooks));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Window window = getWindow();
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(getResources().getColor(R.color.colorBooks));
                        }
                        break;
                    }
                    case 4: {
                        appBarLayout.setBackgroundColor(getResources().getColor(R.color.colorMusic));
                        searchViewBackground.setBackgroundColor(getResources().getColor(R.color.colorMusic));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Window window = getWindow();
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(getResources().getColor(R.color.colorMusic));
                        }
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        searchView.setZ(15);

        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {
                Log.d(MainActivity.class.getSimpleName(), "SearchTextChanged" + "Old query: " + oldQuery +
                        "New query: " + newQuery);

                if (oldQuery.equals("") && newQuery.equals("")) {
                    searchView.clearSuggestions();
                } else {
                    searchView.showProgress();
                    Observable.just(newQuery)
                            .debounce(400, TimeUnit.MILLISECONDS)
                            .subscribe(new Observer<String>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(String s) {
                                    pageViewModel.makeSearchSuggestionApiCall(s, new SearchResponseCallback() {
                                        @Override
                                        public void onSuccess(List<String> suggestions) {
                                            List<SearchSuggestion> searchSuggestions = new ArrayList<>();
                                            for (int i = 0; i < suggestions.size(); i++) {
                                                Log.d(MainActivity.class.getSimpleName(), "Suggestion: " + suggestions.get(i));
                                                SearchSuggestion searchSuggestion = new com.emre1s.playstore.models.SearchSuggestion(suggestions.get(i));
                                                searchSuggestions.add(searchSuggestion);
                                            }
                                            searchView.hideProgress();
                                            searchView.swapSuggestions(searchSuggestions);
                                        }

                                        @Override
                                        public void onFailure() {

                                        }
                                    });
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }
            }
        });


        searchView.attachNavigationDrawerToMenuButton(drawer);

        searchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(View suggestionView, ImageView leftIcon, TextView textView, SearchSuggestion item, int itemPosition) {

            }
        });

        searchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                Toast.makeText(MainActivity.this, "Check done", Toast.LENGTH_SHORT).show();
                promptSpeechInput();
            }
        });

        searchView.setShowMoveUpSuggestion(true);

        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("query", searchSuggestion.getBody());
                startActivity(intent);
            }

            @Override
            public void onSearchAction(String currentQuery) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("query", currentQuery);
                startActivity(intent);
            }
        });

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void initializeCategories(String myJson, String familyJson, String gamesJson, String topAppsJson) {
        CategoryList appsCategory = new Gson().fromJson(myJson, CategoryList.class);
        CategoryList familyCategory = new Gson().fromJson(familyJson, CategoryList.class);
        CategoryList gamesCategory = new Gson().fromJson(gamesJson, CategoryList.class);
        CategoryList topAppCategories = new Gson().fromJson(topAppsJson, CategoryList.class);

        TabList gamesAndAppsTab = new Gson().fromJson(inputStreamToString(getResources().openRawResource(R.raw.games_apps_tabs)), TabList.class);
        TabList movieTabs = new Gson().fromJson(inputStreamToString(getResources().openRawResource(R.raw.movie_tabs)), TabList.class);
        TabList bookTabs = new Gson().fromJson(inputStreamToString(getResources().openRawResource(R.raw.book_tabs)), TabList.class);
        TabList musicTabs = new Gson().fromJson(inputStreamToString(getResources().openRawResource(R.raw.music_tabs)), TabList.class);

        RetrofitApiFactory.setGamesAndAppsTabList(gamesAndAppsTab);
        RetrofitApiFactory.setMovieTabList(movieTabs);
        RetrofitApiFactory.setBookTabList(bookTabs);
        RetrofitApiFactory.setMusicTabList(musicTabs);

        RetrofitApiFactory.setAppCategories(appsCategory);
        RetrofitApiFactory.setFamilyCategories(familyCategory);
        RetrofitApiFactory.setGameCategories(gamesCategory);
        RetrofitApiFactory.setAppsTopCategoryList(topAppCategories);

    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Say something");
        try {
            startActivityForResult(intent, 100);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support speech input",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            if (resultCode == RESULT_OK && null != data) {

                ArrayList<String> result = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                searchView.setSearchText(result.get(0));
            }
        }
    }

    public String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            String json = new String(bytes);
            return json;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void onShowAllClicked() {
        Intent intent = new Intent(this, TopCharts.class);
        startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_my_apps_and_games) {
            Intent intent = new Intent(this, InstalledAppsActivity.class);
            startActivity(intent);
        }

        /*if (id == R.id.nav_home) {
        } else if (id == R.id.nav_gallery) {
        } else if (id == R.id.nav_slideshow) {
        } else if (id == R.id.nav_tools) {
        } else if (id == R.id.nav_share) {
        } else if (id == R.id.nav_send) {
        }*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openSearchView(View view) {
        searchView.setZ(15);
        searchView.setVisibility(View.VISIBLE);
        searchView.bringToFront();
        searchView.setSearchFocused(true);
    }
}