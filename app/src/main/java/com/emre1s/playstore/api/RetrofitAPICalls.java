package com.emre1s.playstore.api;

import com.emre1s.playstore.app_details.AppDetails;
import com.emre1s.playstore.models.App;
import com.emre1s.playstore.models.Permission;
import com.emre1s.playstore.models.Review;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitAPICalls {

    String QUERY_APP_PACKAGE_ID = "id";

    String ADDRESS_APPS_DETAILS = "apps/details";

    @GET(ADDRESS_APPS_DETAILS)
    Single<AppDetails> getAppDetails(@Query(QUERY_APP_PACKAGE_ID) String appPackageId);

    @GET(" ")
    Single<List<App>> getAppsByCategoryResponse(
            @Query("category") String category);

    @GET(" ")
    Single<List<App>> getAppsByCollectionResponse(
            @Query("collection") String collection);

    @GET("apps/similar/")
    Single<List<App>> getSimilarApps(
            @Query("id") String packageName);

    @GET("suggestions/")
    Single<List<String>> getSearchSuggestions(
            @Query("q") String keyword);

    @GET(" ")
    Single<List<App>> getAppsByCollectionCategory(
            @Query("collection") String collection,
            @Query("category") String category
    );

    @GET("search/")
    Single<List<App>> getSearchResults(
            @Query("q") String query);

    @GET("apps/permissions/")
    Observable<List<Permission>> getAppPermissions(
            @Query("id") String packageName);

    @GET("apps/reviews/")
    Single<List<Review>> getReviews(
            @Query("id") String id
    );
}