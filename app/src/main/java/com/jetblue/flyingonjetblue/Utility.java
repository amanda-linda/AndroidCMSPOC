package com.jetblue.flyingonjetblue;

import android.app.Application;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by amandadasilva on 10/7/15.
 */


public class Utility {
    public static final String BASE_URL = "https://www.jetblue.com";
    /**
     *     Method takes image url, checks if it has been stored. If not stored, it pulls the image from online using an image loader, then
     the ImageView with the BitMap
     * @param urlStr : the url of the image we want to use
     * @param view : the view we want to load the image into
     */
    public static void setImage(String urlStr, ImageView view, ArrayList<Pair<String,Bitmap>> image_store){
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
    }

    public static String getString(String key, JSONObject obj){
        String result = "";
        try {
            result = obj.getString(key);
        } catch (JSONException e) {
            //  e.printStackTrace();
        }
        return result;
    };

    /**
     * This semi-recursion is admittedly a really annoying way to do this. The problem is that sometimes we have:
     *
     * "image" : "image url"
     *
     * other times we have
     *
     * "image" : {
     *     "small":
     *     "med" :...
     * }
     *
     * May should find a library to make JSON less annoying to work with.
     * @param key : JSON key to look for URL at
     * @param obj : JSON object to look for key in
     * @return URL String or empty string
     */
    public static String getURLString(String key, JSONObject obj){
        String result = "";
        try {
            JSONObject newObj = obj.getJSONObject(key);
            result =  getURLString("med", newObj);
        } catch (JSONException e) {
            result = Utility.BASE_URL+getString(key,obj);
        }
        return result;

    }

    public static JSONObject[] jsonArraytoList(JSONArray array) throws JSONException {
        JSONObject[] object = new JSONObject[array.length()];

        for (int i=0; i<array.length(); i++) {
            object[i]= array.getJSONObject(i);
        }
        return object;
    }


}
