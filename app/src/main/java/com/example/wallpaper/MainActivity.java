package com.example.wallpaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.wallpaper.Adapters.CuratedAdapter;
import com.example.wallpaper.Listners.CuretedResponseListners;
import com.example.wallpaper.Listners.OnRecyclerClickListner;
import com.example.wallpaper.Models.CuratedApiResponse;
import com.example.wallpaper.Models.Photo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnRecyclerClickListner {

    RecyclerView recyclerViewHome;
    RequestManager manager;
    ProgressDialog dialog;
    CuratedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewHome = findViewById(R.id.recycler_home);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading...");

        manager = new RequestManager(MainActivity.this);
        manager.getCuratedWallpapers(listners, "1");



    }

        private final CuretedResponseListners listners = new CuretedResponseListners() {
            @Override
            public void onFetch(CuratedApiResponse response, String message) {
                if(response.getPhotos().isEmpty()){
                Toast.makeText(MainActivity.this, "No Image found!", Toast.LENGTH_SHORT).show();
                }
                showData(response.getPhotos());
            }

            @Override
            public void onError(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        };




    private void showData(ArrayList<Photo> photos) {

        adapter = new CuratedAdapter(MainActivity.this,photos,this);

        recyclerViewHome.setHasFixedSize(true);
        recyclerViewHome.setLayoutManager(new GridLayoutManager(this,2));
        recyclerViewHome.setAdapter(adapter);


    }

    @Override
    public void onClick(Photo photo) {

        Toast.makeText(MainActivity.this, photo.getPhotographer(), Toast.LENGTH_SHORT).show();
    }
}