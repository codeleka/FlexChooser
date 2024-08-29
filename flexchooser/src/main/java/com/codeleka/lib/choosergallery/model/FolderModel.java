package com.codeleka.lib.choosergallery.model;
/***
 * This file Helper Library Created By Codeleka team to Select Media images and Video easily
 * Contact us for High Quality App development Services
 *
 *
 * Thanks for developing your App by Us .
 * Team : Codeleka
 * Website : https://codeleka.com
 */
import java.util.List;

public class FolderModel {

    String title,path;
    List<FilesModel> filesModelList;

    public FolderModel(String title, String path, List<FilesModel> filesModelList) {
        this.title = title;
        this.path = path;
        this.filesModelList = filesModelList;
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }

    public List<FilesModel> getImagesList() {
        return filesModelList;
    }
}
