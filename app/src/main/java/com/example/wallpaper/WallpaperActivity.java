package com.example.wallpaper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wallpaper.Models.Photo;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.squareup.picasso.Picasso;

public class WallpaperActivity extends AppCompatActivity {

    ImageView imageViewMedium;
    ExtendedFloatingActionButton btnDownload,btnWallpaper;

    Photo photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);

        imageViewMedium = findViewById(R.id.medium_image);
        btnDownload = findViewById(R.id.fab_download);
        btnWallpaper = findViewById(R.id.fab_save);


        photo = (Photo)getIntent().getSerializableExtra("photo");


        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        });

        btnWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                WallpaperManager wallpaperManager = WallpaperManager.getInstance(WallpaperActivity.this);
                Bitmap bitmap = ((BitmapDrawable) imageViewMedium.getDrawable()).getBitmap();

                try {

                    wallpaperManager.setBitmap(bitmap);
                    Toast.makeText(WallpaperActivity.this, "Wallpaper applied!", Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(WallpaperActivity.this, "Something went wrong... Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Picasso.get().load(photo.getSrc().getPortrait()).placeholder(R.drawable.placeholder_image).into(imageViewMedium);
    }
}