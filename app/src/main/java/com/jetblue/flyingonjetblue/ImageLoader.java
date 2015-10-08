package com.jetblue.flyingonjetblue;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Class to asychronously load Bitmaps from a given URL into an image store. We currently need a new asynchronous task
 * for each image load
 *
 */
public class ImageLoader extends AsyncTask<String, String, Bitmap> {
    ImageView view = null;
    private ArrayList<Pair<String,Bitmap>> store;
    private String urlStr;

    /**
     *
     * @param imageStore : Data structure where the Bitmaps are stored
     */
    public ImageLoader (ArrayList<Pair<String,Bitmap>> imageStore){

        store = imageStore;
    }

    protected Bitmap doInBackground(String... args) {
        Log.d("WARN", "DOING IN BACKGROUND");
        Bitmap bitmap = null;
        try {
            Log.d("URL", args[0]);
            urlStr = args[0];
            bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap image) {
        if(image != null){
            Pair<String, Bitmap> temp = new Pair<String, Bitmap>(urlStr, image);
            store.add(temp);
        }
        if(image != null && view !=null) {
            view.setImageBitmap(image);
            Log.d("SUCCESS", store.size()+"");
        }
    }
    protected void setImageView(ImageView v){
        this.view = v;
    }
}
