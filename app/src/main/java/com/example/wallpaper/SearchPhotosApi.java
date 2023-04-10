package com.example.wallpaper;

import com.example.wallpaper.Models.CuratedApiResponse;
import com.example.wallpaper.Models.SearchModels;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface SearchPhotosApi {

    @Headers({
            "Accept: application/json",
            "Authorization: qouJk7ouLTqama5u6SH3dbrPfyisz3uGdE5743YiGIgXwfLEdRCm9Etx"
    })


    @GET("search")
    Call<SearchModels> getSearchedPhotos(
            @Query("query") String query,
            @Query("page") String page,
            @Query("per_page") String per_page
    );
}
