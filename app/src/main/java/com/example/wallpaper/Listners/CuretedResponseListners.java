package com.example.wallpaper.Listners;

import com.example.wallpaper.Models.CuratedApiResponse;

public interface CuretedResponseListners {

    public void onFetch(CuratedApiResponse response,String message);
    void onError(String message);

}
