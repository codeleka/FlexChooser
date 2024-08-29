package com.codeleka.lib.choosergallery.fragments;
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
import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.codeleka.lib.choosergallery.ChooserType;
import com.codeleka.lib.choosergallery.R;
import com.codeleka.lib.choosergallery.FlexChooser;
import com.codeleka.lib.choosergallery.adapter.FilesAdapter;
import com.codeleka.lib.choosergallery.inter.SelectFile;
import com.codeleka.lib.choosergallery.model.FilesModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AllFragment extends Fragment {

    View layout;

    RecyclerView recyclerView;
    ProgressBar loader;

    SelectFile selectFile;


    Activity activity;

    public AllFragment(SelectFile selectFile, Activity activity) {
        this.selectFile = selectFile;
        this.activity = activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_gallery, container, false);

        recyclerView = layout.findViewById(R.id.recycler);
        loader = layout.findViewById(R.id.loader);

        loadData();

        return layout;
    }

    @SuppressLint("Recycle")
    private void loadData() {

        new LoadTask().execute();

    }


    @SuppressLint("StaticFieldLeak")
    class LoadTask extends AsyncTask<String, Integer, List<FilesModel>> {

        @Override
        protected void onPreExecute() {
            activity.runOnUiThread(() -> loader.setVisibility(View.VISIBLE));
        }

        @Override
        protected List<FilesModel> doInBackground(String... strings) {

            List<FilesModel> filesModelList = new ArrayList<>();

            Cursor cursor;

            if (FlexChooser.FILE_TYPE.equals(ChooserType.VIDEO)) {
                cursor = activity.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.MediaColumns.DATA, MediaStore.Video.Media.BUCKET_DISPLAY_NAME}, null, null, MediaStore.Video.Media.DATE_TAKEN + " DESC");
            } else {
                cursor = activity.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME}, null, null, MediaStore.Images.Media.DATE_TAKEN + " DESC");
            }


            if (cursor == null) {
                return filesModelList;
            }

            int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

            while (cursor.moveToNext()) {

                try {
                    File file = new File(cursor.getString(column_index_data));
                    filesModelList.add(new FilesModel(file.getName(), file.getAbsolutePath(), false));
                } catch (Exception ignored) {

                }

            }


            return filesModelList;
        }

        @Override
        protected void onPostExecute(List<FilesModel> resultList) {
            activity.runOnUiThread(() -> {
                setData(resultList);
                loader.setVisibility(View.GONE);
            });

        }
    }

    private void setData(List<FilesModel> filesModelList) {
        FilesAdapter filesAdapter = new FilesAdapter(filesModelList, selectFile);
        recyclerView.setAdapter(filesAdapter);
    }
}