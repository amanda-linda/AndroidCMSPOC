package com.jetblue.flyingonjetblue;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

public class ImageLoader extends AsyncTask<String, String, Bitmap> {
    ImageView view = null;
    protected Bitmap doInBackground(String... args) {
        Bitmap bitmap = null;
        try {
            Log.d("URL", args[0]);
            bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap image) {
        if(image != null && view !=null) {
            view.setImageBitmap(image);
            Log.d("SUCCESS", image.toString());
        }
    }
    protected void setImageView(ImageView v){
        this.view = v;
    }
}
