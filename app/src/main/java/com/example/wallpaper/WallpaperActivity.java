package com.example.wallpaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wallpaper.Models.Photo;
import com.example.wallpaper.databinding.ActivityWallpaperBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.squareup.picasso.Picasso;

public class WallpaperActivity extends AppCompatActivity{


    ActivityWallpaperBinding wallpaperBinding;
    //ExtendedFloatingActionButton btnDownload,btnWallpaper;
    Photo photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wallpaperBinding = ActivityWallpaperBinding.inflate(getLayoutInflater());
        setContentView(wallpaperBinding.getRoot());

        photo = (Photo)getIntent().getSerializableExtra("photo");

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        wallpaperBinding.bottomNavigationView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent));
        wallpaperBinding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bnv_download : download();
                    break;
                    case R.id.bnv_Set_wallpaper: setWallpaper();
                    break;
                }
                return true;
            }
        });

        Picasso.get().load(photo.getSrc().getPortrait()).placeholder(R.drawable.placeholder_image).into(wallpaperBinding.mediumImage);


//        btnDownload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                DownloadManager downloadManager = null;
//                downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
//                Uri uri = Uri.parse(photo.getSrc().getLarge());
//
//                DownloadManager.Request request = new DownloadManager.Request(uri);
//
//                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI)
//                        .setAllowedOverRoaming(false)
//                        .setTitle("Wallpaper_"+photo.getPhotographer())
//                        .setMimeType("image/jpeg")
//                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,
//                                "Wallpaper_"+photo.getPhotographer()+".jpg");
//
//                downloadManager.enqueue(request);
//
//                Toast.makeText(WallpaperActivity.this, "Image downloaded! ", Toast.LENGTH_SHORT).show();
//
//            }
//        });

//        btnWallpaper.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                WallpaperManager wallpaperManager = WallpaperManager.getInstance(WallpaperActivity.this);
//                Bitmap bitmap = ((BitmapDrawable) imageViewMedium.getDrawable()).getBitmap();
//
//                try {
//
//                    wallpaperManager.setBitmap(bitmap);
//                    Toast.makeText(WallpaperActivity.this, "Wallpaper applied!", Toast.LENGTH_SHORT).show();
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                    Toast.makeText(WallpaperActivity.this, "Something went wrong... Please try again", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

    }


    public void download(){

        DownloadManager downloadManager = null;
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(photo.getSrc().getLarge());

        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI)
                .setAllowedOverRoaming(false)
                .setTitle("Wallpaper_"+photo.getPhotographer())
                .setMimeType("image/jpeg")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,
                        "Wallpaper_"+photo.getPhotographer()+".jpg");

        downloadManager.enqueue(request);

        Toast.makeText(WallpaperActivity.this, "Image downloaded! ", Toast.LENGTH_SHORT).show();
    }


    public void setWallpaper(){
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(WallpaperActivity.this);
        Bitmap bitmap = ((BitmapDrawable) wallpaperBinding.mediumImage.getDrawable()).getBitmap();

        try {

            wallpaperManager.setBitmap(bitmap);
            Toast.makeText(WallpaperActivity.this, "Wallpaper applied!", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(WallpaperActivity.this, "Something went wrong... Please try again", Toast.LENGTH_SHORT).show();
        }
    }


}