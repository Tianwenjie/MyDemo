package com.example.tangsong.mydemo.common;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by tangsong on 8/26/14.
 */
public class Utils {

    private static final String TAG = "Utils";
    private static Toast t;

    private static void t(Context context) {
        if (t == null) {
            t = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        t.cancel();
    }

    public static void toast(Context context, String msg) {
        t(context);
        toast(context, msg, null);
    }

    public static void toast(Context context, Bitmap bm) {
        t(context);
        t = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        ImageView iv = new ImageView(context);
        iv.setImageBitmap(bm);
        t.setView(iv);
        t.show();
    }

    public static void toast(Context context, String msg, Bitmap bm) {
        t(context);
        t = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        LinearLayout parent = new LinearLayout(context);
        parent.setOrientation(LinearLayout.VERTICAL);

        ImageView iv = new ImageView(context);
        iv.setImageBitmap(bm);

        TextView tv = new TextView(context);
        tv.setText(msg);

        parent.addView(iv);
        parent.addView(tv);
        t.setView(parent);
        t.show();

    }

    public static Uri addImage(ContentResolver resolver, String title,
                               long date, Location location, int orientation,
                               File file) {

        String filePath = file.getPath();
        String filename = file.getName();
        long jpegLength = file.length();

        Log.i(TAG, "Add image:" + date);
        // Insert into MediaStore.
        ContentValues values = new ContentValues(9);
        values.put(MediaStore.Images.ImageColumns.TITLE, title);
        values.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, filename);
        values.put(MediaStore.Images.ImageColumns.DATE_TAKEN, date);
        values.put(MediaStore.Images.ImageColumns.MIME_TYPE, "image/jpeg");
        // Clockwise rotation in degrees. 0, 90, 180, or 270.
        values.put(MediaStore.Images.ImageColumns.ORIENTATION, orientation);
        values.put(MediaStore.Images.ImageColumns.DATA, filePath);
        values.put(MediaStore.Images.ImageColumns.SIZE, jpegLength);
        if (location != null) {
            values.put(MediaStore.Images.ImageColumns.LATITUDE, location.getLatitude());
            values.put(MediaStore.Images.ImageColumns.LONGITUDE, location.getLongitude());
        }


        try {
            return resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } catch (Throwable th) {
            // This can happen when the external volume is already mounted, but
            // MediaScanner has not notify MediaProvider to add that volume.
            // The picture is still safe and MediaScanner will find it and
            // insert it into MediaProvider. The only problem is that the user
            // cannot click the thumbnail to review the picture.
            Log.e(TAG, "Failed to write MediaStore" + th);
            return null;
        }
    }

    /**
     * 扫描、刷新相册
     */
    public static void scanPhotos(String filePath, Context context) {
        Intent intent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(new File(filePath));
        intent.setData(uri);
        context.sendBroadcast(intent);
    }
}
