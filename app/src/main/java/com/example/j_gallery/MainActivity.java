package com.example.j_gallery;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.example.j_gallery.databinding.ActivityMainBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    List<Gallery> galleryList;
    GalleryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        requestAllPermission();
    }

    private void requestAllPermission() {

        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        getData();
                      }
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                      }
                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                      }
                }).check();

    }

    private void getData() {

        galleryList = new ArrayList<>();

        String[] projection = {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATE_MODIFIED,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        };

        Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;


        Cursor cursor = getContentResolver().query(contentUri,projection,null,null, null);

        if (cursor !=null && !cursor.equals("")){
            cursor.moveToPosition(0);

            while (true){

                long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                double size = cursor.getDouble(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                long date = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED));

                Uri imageUri = ContentUris.withAppendedId(contentUri,id);


                Gallery gallery = new Gallery(id,date,size,name,imageUri);
                galleryList.add(gallery);

                if (!cursor.isLast()){
                    cursor.moveToNext();
                }else {
                    break;
                }
            }

            adapter = new GalleryAdapter(MainActivity.this,galleryList);
            binding.recycler.setAdapter(adapter);
        }

    }
}