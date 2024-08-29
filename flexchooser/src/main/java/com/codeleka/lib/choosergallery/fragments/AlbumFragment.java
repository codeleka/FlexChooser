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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.codeleka.lib.choosergallery.FlexChooser;
import com.codeleka.lib.choosergallery.ChooserType;
import com.codeleka.lib.choosergallery.R;
import com.codeleka.lib.choosergallery.adapter.FolderAdapter;
import com.codeleka.lib.choosergallery.adapter.FilesAdapter;
import com.codeleka.lib.choosergallery.inter.FragBack;
import com.codeleka.lib.choosergallery.inter.SelectFile;
import com.codeleka.lib.choosergallery.model.FolderModel;
import com.codeleka.lib.choosergallery.model.FilesModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class AlbumFragment extends Fragment implements FragBack {

    View layout;


    RecyclerView recyclerView;
    ProgressBar loader;

    FolderAdapter adapter;
    FilesAdapter filesAdapter;

    SelectFile selectFile;

    Activity activity;

    public AlbumFragment(SelectFile selectFile, Activity activity) {
        this.selectFile = selectFile;
        this.activity = activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_gallery, container, false);

        recyclerView = layout.findViewById(R.id.recycler);
        loader = layout.findViewById(R.id.loader);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(layout.getContext(), 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(gridLayoutManager);

        loadData();

        return layout;
    }

    @SuppressLint("Recycle")
    private void loadData() {

        new LoadTask().execute();
    }


    private void openImages(FolderModel folderModel) {
        filesAdapter = new FilesAdapter(folderModel.getImagesList(), selectFile);
        recyclerView.setAdapter(filesAdapter);
    }

    public boolean isNewFolder(final List<String> list, String folder) {
        boolean checkMatch = list.stream().noneMatch(o -> o.contains(folder));
        return checkMatch;
    }

    @Override
    public boolean onBackPressed() {
        try {
            if (recyclerView.getAdapter() == filesAdapter) {
                recyclerView.setAdapter(adapter);
                return true;
            }
        } catch (Exception ignored) {
        }
        return false;
    }


    @SuppressLint("StaticFieldLeak")
    class LoadTask extends AsyncTask<String, Integer, List<FolderModel>> {

        @Override
        protected void onPreExecute() {
            activity.runOnUiThread(() -> loader.setVisibility(View.VISIBLE));
        }

        @Override
        protected List<FolderModel> doInBackground(String... strings) {

            List<FolderModel> folderModelList = new ArrayList<>();

            Cursor cursor;
            if (FlexChooser.FILE_TYPE.equals(ChooserType.VIDEO)) {
                cursor = activity.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.MediaColumns.DATA, MediaStore.Video.Media.BUCKET_DISPLAY_NAME}, null, null, MediaStore.Video.Media.DATE_TAKEN + " DESC");
            } else {
                cursor = activity.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME}, null, null, MediaStore.Images.Media.DATE_TAKEN + " DESC");

            }


            List<String> exception = new ArrayList<>();

            if (cursor != null) {

                while (cursor.moveToNext()) {

                    try {
                        String absolutePathOfImage = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.BUCKET_DISPLAY_NAME));
                        String absolutePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));

                        File folder = new File(absolutePathOfImage);
                        File path = new File(absolutePath);

                        if (isNewFolder(exception, folder.getName())) {
                            exception.add(folder.getName());

                            String parent_folder = path.getParent();

                            if (parent_folder != null) {

                                File[] all_files = new File(parent_folder).listFiles();

                                if (all_files != null) {

                                    List<FilesModel> filesModelList = new ArrayList<>();

                                    for (File file_path : all_files) {


                                        if (FlexChooser.FILE_TYPE.equals(ChooserType.VIDEO)) {
                                            if (file_path.getAbsolutePath().toLowerCase().endsWith(".mp4")) {
                                                FilesModel filesModel = new FilesModel(file_path.getName(), file_path.toString(), false);
                                                filesModelList.add(filesModel);
                                            }
                                        } else {
                                            if (file_path.getAbsolutePath().toLowerCase().endsWith(".jpg") || file_path.getAbsolutePath().toLowerCase().endsWith(".png")) {
                                                FilesModel filesModel = new FilesModel(file_path.getName(), file_path.toString(), false);
                                                filesModelList.add(filesModel);
                                            }
                                        }

                                    }
                                    if (filesModelList.size() > 0) {
                                        FolderModel folderModel = new FolderModel(folder.getName(), parent_folder, filesModelList);
                                        folderModelList.add(folderModel);
                                    }
                                }
                            }
                        }
                    } catch (Exception ignored) {

                    }
                }
            }

            return folderModelList;
        }

        @Override
        protected void onPostExecute(List<FolderModel> resultList) {
            activity.runOnUiThread(() -> {
                setData(resultList);
                loader.setVisibility(View.GONE);
            });

        }
    }

    private void setData(List<FolderModel> folderModelList) {
        adapter = new FolderAdapter(folderModelList, (position, folderModel) -> {

            openImages(folderModel);

        });

        recyclerView.setAdapter(adapter);
    }


}