package com.codeleka.lib.choosergallery;
/***
 * This file Helper Library Created By Codeleka team to Select Media images and Video easily
 * Contact us for High Quality App development Services
 *
 *
 * Thanks for developing your App by Us .
 * Team : Codeleka
 * Website : https://codeleka.com
 */

import android.app.Activity;
import android.content.Intent;

import com.codeleka.lib.choosergallery.inter.OnResult;

import java.util.ArrayList;
import java.util.List;

public class FlexChooser {
    public static String FILE_TYPE = ".jpg";
    public static OnResult onResult;
    public static List<String> paths = new ArrayList<>();
    public static int MAX_SELECT = 10;

    Activity activity;

    public FlexChooser(Activity activity) {
        FlexChooser.paths.clear();
        this.activity = activity;
    }

    public FlexChooser startActivity() {
        activity.startActivity(new Intent(activity, ChooserActivity.class));
        return this;
    }

    public FlexChooser getResult(OnResult onResult) {
        FlexChooser.onResult = onResult;
        return this;
    }

    public FlexChooser startActivity(OnResult onResult) {
        FlexChooser.onResult = onResult;
        activity.startActivity(new Intent(activity, ChooserActivity.class));
        return this;
    }

    public FlexChooser setLimit(int limit) {
        FlexChooser.MAX_SELECT = limit;
        return this;
    }

    public FlexChooser setType(String type) {
        FlexChooser.FILE_TYPE = type;
        return this;
    }

    public static boolean isNotExist(String path) {
        return FlexChooser.paths.stream().noneMatch(o -> o.contains(path));
    }


}
