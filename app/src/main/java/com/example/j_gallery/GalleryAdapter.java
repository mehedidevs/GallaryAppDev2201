package com.example.j_gallery;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryViewHolder> {
    private Context context;
    private List<Gallery> galleryList;

    public GalleryAdapter(Context context, List<Gallery> galleryList) {
        this.context = context;
        this.galleryList = galleryList;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gallery, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {

        Gallery gallery = galleryList.get(position);
        holder.img.setImageURI(gallery.getImageUri());

        holder.itemView.setOnClickListener(v -> {

            String uriStr = String.valueOf(gallery.imageUri);

            Intent intent = new Intent(context, FullImageActivity.class);
            intent.putExtra("img", uriStr);
            context.startActivity(intent);


        });


    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }
}
