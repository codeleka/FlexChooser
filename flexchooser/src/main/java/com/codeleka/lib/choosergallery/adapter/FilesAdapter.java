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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codeleka.lib.choosergallery.R;
import com.codeleka.lib.choosergallery.FlexChooser;
import com.codeleka.lib.choosergallery.inter.SelectFile;
import com.codeleka.lib.choosergallery.model.FilesModel;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.ImageHolder> {

    List<FilesModel> list;
    SelectFile selectFile;

    public FilesAdapter(List<FilesModel> list, SelectFile selectFile) {
        this.list = list;
        this.selectFile = selectFile;
    }


    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_images, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {

        FilesModel filesModel = list.get(position);

        Glide.with(holder.itemView.getContext())
                .load(new File(filesModel.getPath()))
                .apply(new RequestOptions().override(200, 200))
                .centerCrop()
                .into(holder.cover);


        if (FlexChooser.paths.size() > 0 && !FlexChooser.isNotExist(filesModel.getPath())) {
            holder.check.setChecked(true);
            filesModel.setSelected(true);
        }


        holder.itemView.setOnClickListener(v -> {

            if (holder.check.isChecked()) {
                holder.check.setChecked(false);
                filesModel.setSelected(false);
            } else {
                if (FlexChooser.paths.size() >= FlexChooser.MAX_SELECT) {
                    filesModel.setSelected(false);
                    Toast.makeText(holder.itemView.getContext(), "Can't select more than " + FlexChooser.MAX_SELECT + " images", Toast.LENGTH_SHORT).show();
                } else {
                    holder.check.setChecked(true);
                    filesModel.setSelected(true);
                }
            }
            selectFile.onSelect(position, filesModel);

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<String> getSelected() {
        List<String> checkList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSelected()) {
                checkList.add(list.get(i).getPath());
            }
        }
        return checkList;
    }

    public static class ImageHolder extends RecyclerView.ViewHolder {

        ImageView cover;
        CheckBox check;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);

            cover = itemView.findViewById(R.id.item_all_cover);
            check = itemView.findViewById(R.id.item_all_check);

        }
    }
}
