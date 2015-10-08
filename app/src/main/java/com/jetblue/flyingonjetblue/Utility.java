package com.jetblue.flyingonjetblue;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.Pair;
import android.widget.ImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by amandadasilva on 10/7/15.
 */
public class Utility {
    public static final String BASE_URL = "https://www.jetblue.com";

    //The data structure that we store loaded images into, String is the key, Bitmap is the image
    /**
     * @TODO: come up with convention around expiring image_store. Also, look into using built in storage functionality
     */
    protected static ArrayList<Pair<String,Bitmap>> image_store = new ArrayList<Pair<String,Bitmap>>();


    /**
     *     Method takes image url, checks if it has been stored. If not stored, it pulls the image from online using an image loader, then
     the ImageView with the BitMap
     * @param urlStr : the url of the image we want to use
     * @param view : the view we want to load the image into
     */
    public static void setImage(String urlStr, ImageView view){
            Pair<String, Bitmap> temp;
            Bitmap result = null;
        for(int i =0; i < image_store.size(); i++){
            temp = image_store.get(i);
            if(temp.first.equals(urlStr) ){
                result = temp.second;
                view.setImageBitmap(result);
            }
        }

        if(result == null){
            ImageLoader imgLoader = new ImageLoader(image_store);
            imgLoader.setImageView(view);
            imgLoader.execute(urlStr);
        }
    };


}
