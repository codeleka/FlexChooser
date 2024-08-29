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
public class FilesModel {

    String title,path;
    boolean selected;

    public FilesModel(String title, String path, boolean selected) {
        this.title = title;
        this.path = path;
        this.selected = selected;
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
