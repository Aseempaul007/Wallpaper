package com.example.wallpaper.Listners;

import com.example.wallpaper.Models.SearchModels;

public interface SearchResponseListener {

    void onFetch(SearchModels response, String message);

    void onError(String message);
}
