package com.example.wallpaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.wallpaper.Adapters.CuratedAdapter;
import com.example.wallpaper.Listners.CuretedResponseListners;
import com.example.wallpaper.Listners.OnRecyclerClickListner;
import com.example.wallpaper.Models.CuratedApiResponse;
import com.example.wallpaper.Models.Photo;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnRecyclerClickListner {

    RecyclerView recyclerViewHome;
    RequestManager manager;
    ProgressDialog dialog;
    CuratedAdapter adapter;

    ExtendedFloatingActionButton btnNext;

    ExtendedFloatingActionButton btnPrev;

    int currentPage=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewHome = findViewById(R.id.recycler_home);
        btnNext = findViewById(R.id.fab_next);
        btnPrev = findViewById(R.id.fab_prev);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading...");

        manager = new RequestManager(MainActivity.this);
        manager.getCuratedWallpapers(listners, Integer.toString(currentPage));


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nextPage = String.valueOf(currentPage+1);
                manager.getCuratedWallpapers(listners, nextPage);
                dialog.show();
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentPage>1){
                    dialog.show();
                    String prevPage = String.valueOf(currentPage-1);
                    manager.getCuratedWallpapers(listners, prevPage);
                }else{
                    Toast.makeText(MainActivity.this, "You are already on the first page", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

        private final CuretedResponseListners listners = new CuretedResponseListners() {
            @Override
            public void onFetch(CuratedApiResponse response, String message) {
                dialog.cancel();
                if(response.getPhotos().isEmpty()){
                Toast.makeText(MainActivity.this, "No Image found!", Toast.LENGTH_SHORT).show();
                return;
                }
                currentPage = response.getPage();
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

        Intent i = new Intent(MainActivity.this,WallpaperActivity.class);
        i.putExtra("photo",photo);
        startActivity(i);
    }


}