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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;

public class FileHelper {

    public static String FOLDER = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString() + "/.chikuai_apps_vfunny/";


    public static void checkFolder() {
        File dir = new File(FOLDER);
        boolean isDirectoryCreated = dir.exists();
        if (!isDirectoryCreated) {
            isDirectoryCreated = dir.mkdirs();
        }
        if (isDirectoryCreated) {
            Log.d("Folder", "Already Created");
        }
    }

    public static void scanFile(Activity activity,String path) {
        MediaScannerConnection.scanFile(
                activity,
                new String[]{path},
                new String[]{"*/*"},
                new MediaScannerConnection.MediaScannerConnectionClient() {
                    public void onMediaScannerConnected() {
                    }

                    public void onScanCompleted(String path, Uri uri1) {
                        Log.d("path: ", path);
                    }
                });
    }


    public static File saveBitmapToFile(File file, String fileId, int quality) {
        try {

            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;

            FileInputStream inputStream = new FileInputStream(file);
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);

            // here i override the original image file

            checkFolder();

            File new_folder = new File(FOLDER);
            if (!new_folder.exists()) {
                new_folder.mkdirs();
            }

            String imagePath = new_folder + "/TEMP_" + fileId + ".jpg";

            File file1 = new File(imagePath);

            file1.createNewFile();

            FileOutputStream outputStream = new FileOutputStream(file1);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);

            inputStream.close();
            outputStream.close();

            return file1;

        } catch (Exception e) {
            Log.d("cccccccccccccc", "saveBitmapToFile: " + e.toString());
            return null;
        }
    }

    public static String getFileSizeWithType(long size) {

        DecimalFormat df = new DecimalFormat("0.00");
        float sizeKb = 1024.0f;
        float sizeMb = sizeKb * sizeKb;
        float sizeGb = sizeMb * sizeKb;
        float sizeTerra = sizeGb * sizeKb;

        if (size < sizeMb)
            return df.format(size / sizeKb) + " Kb";
        else if (size < sizeGb)
            return df.format(size / sizeMb) + " Mb";
        else if (size < sizeTerra)
            return df.format(size / sizeGb) + " Gb";

        return "";
    }

}
