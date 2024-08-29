package com.codeleka.lib.choosergallery.adapter;
/***
 * This file Helper Library Created By Codeleka team to Select Media images and Video easily
 * Contact us for High Quality App development Services
 *
 *
 * Thanks for developing your App by Us .
 * Team : Codeleka
 * Website : https://codeleka.com
 */
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codeleka.lib.choosergallery.R;
import com.codeleka.lib.choosergallery.inter.AlbumClick;
import com.codeleka.lib.choosergallery.model.FolderModel;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


import java.io.File;
import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.AlbumHolder> {

    List<FolderModel> list;
    AlbumClick albumClick;

    public FolderAdapter(List<FolderModel> list, AlbumClick albumClick) {
        this.list = list;
        this.albumClick = albumClick;
    }

    @NonNull
    @Override
    public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlbumHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AlbumHolder holder, int position) {

        FolderModel folderModel = list.get(position);



        Glide.with(holder.itemView.getContext())
                .load(new File(folderModel.getImagesList().get(0).getPath()))
                .apply(new RequestOptions().override(200, 200))
                .centerCrop()
                .into(holder.cover);

        holder.title.setText(folderModel.getTitle());
        holder.summary.setText(String.valueOf(folderModel.getImagesList().size()));

        holder.itemView.setOnClickListener(v -> {
            albumClick.onClick(position, folderModel);
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class AlbumHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView title, summary;

        public AlbumHolder(@NonNull View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.item_album_cover);
            title = itemView.findViewById(R.id.item_album_title);
            summary = itemView.findViewById(R.id.item_album_summary);
        }
    }


}
