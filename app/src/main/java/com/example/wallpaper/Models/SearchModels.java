package com.example.wallpaper.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchModels implements Serializable {

    public int total_results;
    public int page;
    public int per_page;
    public ArrayList<Photo> photos;
    public String next_page;

    public int getTotal_results() {
        return total_results;
    }

    public int getPage() {
        return page;
    }

    public int getPer_page() {
        return per_page;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public String getNext_page() {
        return next_page;
    }
}
