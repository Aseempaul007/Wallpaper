package com.example.wallpaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wallpaper.Adapters.CuratedAdapter;
import com.example.wallpaper.Listners.CuretedResponseListners;
import com.example.wallpaper.Listners.OnRecyclerClickListner;
import com.example.wallpaper.Listners.SearchResponseListener;
import com.example.wallpaper.Models.CuratedApiResponse;
import com.example.wallpaper.Models.Photo;
import com.example.wallpaper.Models.SearchModels;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnRecyclerClickListner {

    RecyclerView recyclerViewHome;
    RequestManager manager;
    ProgressDialog dialog;
    CuratedAdapter adapter;

    ExtendedFloatingActionButton btnNext;

    ExtendedFloatingActionButton btnPrev;

    ActionBar actionBar;



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

        actionBar = getSupportActionBar();
        actionBar.setElevation(10);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar_title_layout);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


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
        recyclerViewHome.setLayoutManager(new GridLayoutManager(this,3));
        recyclerViewHome.setAdapter(adapter);


    }

    @Override
    public void onClick(Photo photo) {

        Intent i = new Intent(MainActivity.this,WallpaperActivity.class);
        i.putExtra("photo",photo);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.items,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setQueryHint(Html.fromHtml("<font color = #000000>" + "Type here to search.." + "</font>"));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                manager.getSearchWallpapers(searchResponseListener,"1",query);
                dialog.show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }






    private final SearchResponseListener searchResponseListener = new SearchResponseListener() {
        @Override
        public void onFetch(SearchModels response, String message) {
            dialog.dismiss();
            if(response.getPhotos().isEmpty()){
                Toast.makeText(MainActivity.this, "Photos not found!", Toast.LENGTH_SHORT).show();
                return;
            }
            showData(response.getPhotos());
        }

        @Override
        public void onError(String message) {
            Toast.makeText(MainActivity.this, "Photos not found!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    };
}