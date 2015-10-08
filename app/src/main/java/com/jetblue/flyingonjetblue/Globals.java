package com.jetblue.flyingonjetblue;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by amandadasilva on 10/8/15.
 *
 * Singleton class to hold application data.
 *
 */

    public class Globals{
        private static Globals instance;

    // /The data structure that we store loaded images into, String is the key, Bitmap is the image
    /**
     * @TODO: come up with convention around expiring image_store. Also, look into using built in storage functionality
     */
    protected static ArrayList<Pair<String,Bitmap>> image_store = new ArrayList<Pair<String,Bitmap>>();


        // Restrict the constructor from being instantiated
        private Globals(){}


        public static synchronized Globals getInstance(){
            if(instance==null){
                instance=new Globals();
            }
            return instance;
        }

    public ArrayList<Pair<String,Bitmap>> getImageStore(){
        return image_store;
    }
    }


