package com.example.wallpaper;

import android.content.Context;
import android.widget.Toast;

import com.example.wallpaper.Listners.CuretedResponseListners;
import com.example.wallpaper.Listners.SearchResponseListener;
import com.example.wallpaper.Models.CuratedApiResponse;
import com.example.wallpaper.Models.SearchModels;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestManager {

    Context context;
    private final String BASE_URL = "https://api.pexels.com/v1/";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getCuratedWallpapers(CuretedResponseListners listner, String page){
        MyApi myApi = retrofit.create(MyApi.class);
        Call<CuratedApiResponse> call = myApi.getWallpapers(page,"78");
        call.enqueue(new Callback<CuratedApiResponse>() {
            @Override
            public void onResponse(Call<CuratedApiResponse> call, Response<CuratedApiResponse> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, "An Error occured", Toast.LENGTH_SHORT).show();
                    return;
                }

                listner.onFetch(response.body(), response.message());

            }

            @Override
            public void onFailure(Call<CuratedApiResponse> call, Throwable t) {

                listner.onError(t.getMessage());

            }
        });
    }

    public void getSearchWallpapers(SearchResponseListener listener, String page,String query){

        SearchPhotosApi searchPhotosApi = retrofit.create(SearchPhotosApi.class);
        Call<SearchModels> call = searchPhotosApi.getSearchedPhotos(query,page,"78");

        call.enqueue(new Callback<SearchModels>() {
            @Override
            public void onResponse(Call<SearchModels> call, Response<SearchModels> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, "An Error occured", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onFetch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<SearchModels> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });

    }
}
