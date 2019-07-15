package com.emre1s.playstore.api;

import android.app.Application;

import com.emre1s.playstore.app_details.AppDetails;
import com.emre1s.playstore.dagger.AppController;
import com.emre1s.playstore.models.App;
import com.emre1s.playstore.models.CategoryList;
import com.emre1s.playstore.models.MovieGenreList;
import com.emre1s.playstore.models.TabList;

import java.util.List;

import io.reactivex.Scheduler;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
public class RetrofitApiFactory {

    private static RetrofitApiFactory retrofitApiFactory;
    private final RetrofitAPICalls retrofitAPICalls;

    private static CategoryList gameCategories;
    private static CategoryList familyCategories;
    private static CategoryList appCategories;


    private static TabList gamesAndAppsTabList;
    private static TabList movieTabList;
    private static TabList bookTabList;
    private static TabList musicTabList;

    private static MovieGenreList movieGenreList;

    @Inject
    Retrofit retrofit;
    public RetrofitApiFactory(Application application) {
        ((AppController)application).getAppComponent().inject(this);
        retrofitAPICalls = retrofit.create(RetrofitAPICalls.class);
    }

    public void appsByCategoryApiCall(final ApiResponseCallback apiResponseCallback, String category) {

        retrofitAPICalls.getAppsByCategoryResponse(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<App>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<App> apps) {
                        apiResponseCallback.onSuccess(apps);
                    }

                    @Override
                    public void onError(Throwable e) {
                        apiResponseCallback.onFailure();
                    }
                });

    }

    public void getAppDetails(DatabaseCallback databaseCallback, String appID) {
        retrofitAPICalls.getAppDetails(appID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<AppDetails>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(AppDetails appDetails) {
                        databaseCallback.onSuccess(appDetails);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

    public void appsByCollectionApiCall(final ApiResponseCallback apiResponseCallback,
                                        String collection) {


        retrofitAPICalls.getAppsByCollectionResponse(collection)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<App>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<App> apps) {
                        apiResponseCallback.onSuccess(apps);
                    }

                    @Override
                    public void onError(Throwable e) {
                        apiResponseCallback.onFailure();
                    }
                });

    }

    public void similarAppsApiCall(final ApiResponseCallback apiResponseCallback,
                                        String packageName) {


        retrofitAPICalls.getSimilarApps(packageName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<App>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<App> apps) {
                        apiResponseCallback.onSuccess(apps);
                    }

                    @Override
                    public void onError(Throwable e) {
                        apiResponseCallback.onFailure();
                    }
                });

    }

    public void searchAppsApiCall(final ApiResponseCallback apiResponseCallback,
                                  String query) {

        retrofitAPICalls.getSearchResults(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<App>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<App> apps) {
                        apiResponseCallback.onSuccess(apps);
                    }

                    @Override
                    public void onError(Throwable e) {
                        apiResponseCallback.onFailure();
                    }
                });
    }

    public static CategoryList getGameCategories() {
        return gameCategories;
    }

    public static void setGameCategories(CategoryList gameCategories) {
        RetrofitApiFactory.gameCategories = gameCategories;
    }

    public static CategoryList getFamilyCategories() {
        return familyCategories;
    }

    public static void setFamilyCategories(CategoryList familyCategories) {
        RetrofitApiFactory.familyCategories = familyCategories;
    }

    public static CategoryList getAppCategories() {
        return appCategories;
    }

    public static void setAppCategories(CategoryList appCategories) {
        RetrofitApiFactory.appCategories = appCategories;
    }

    public void appsByCollectionCategoryApiCall(String collection, String category,
                                                final ApiResponseCallback apiResponseCallback) {

        retrofitAPICalls.getAppsByCollectionCategory(collection, category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<App>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<App> apps) {
                        apiResponseCallback.onSuccess(apps);
                    }

                    @Override
                    public void onError(Throwable e) {
                        apiResponseCallback.onFailure();
                    }
                });

    }

    public void getSearchSuggestions(String keyword,
                                     final SearchResponseCallback searchResponseCallback) {

        retrofitAPICalls.getSearchSuggestions(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<String> strings) {
                        searchResponseCallback.onSuccess(strings);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public static TabList getGamesAndAppsTabList() {
        return gamesAndAppsTabList;
    }

    public static void setGamesAndAppsTabList(TabList gamesAndAppsTabList) {
        RetrofitApiFactory.gamesAndAppsTabList = gamesAndAppsTabList;
    }

    public static TabList getMovieTabList() {
        return movieTabList;
    }

    public static void setMovieTabList(TabList movieTabList) {
        RetrofitApiFactory.movieTabList = movieTabList;
    }

    public static TabList getBookTabList() {
        return bookTabList;
    }

    public static void setBookTabList(TabList bookTabList) {
        RetrofitApiFactory.bookTabList = bookTabList;
    }

    public static TabList getMusicTabList() {
        return musicTabList;
    }

    public static void setMusicTabList(TabList musicTabList) {
        RetrofitApiFactory.musicTabList = musicTabList;
    }

    public static MovieGenreList getMovieGenreList() {
        return movieGenreList;
    }

    public static void setMovieGenreList(MovieGenreList movieGenreList) {
        RetrofitApiFactory.movieGenreList = movieGenreList;
    }

}
