package com.jetblue.flyingonjetblue;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by amandadasilva on 10/7/15.
 */
public class PageDataAdapter extends ArrayAdapter {

    Context context;

    private static LayoutInflater inflater=null;
    JSONObject[] objects;

    public PageDataAdapter(Context context, int textViewResourceId, JSONObject[] objects) {
        super(context, textViewResourceId, objects);

        this.objects = objects;
        this.context = context;

        // TODO Auto-generated constructor stub
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }




    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return objects.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return objects[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView title;
        TextView description;
        ImageView img;
    }

    private String getString(String key, JSONObject obj){
        String result = "";
        try {
            result = obj.getString(key);
        } catch (JSONException e) {
          //  e.printStackTrace();
        }
        return result;
    };

    /**
     * This is admittedly a really annoying way to do this. May should find a library to make JSON less annoying to work woth
     * @param key
     * @param obj
     * @return
     */
    private String getURLString(String key, JSONObject obj){
        String result = "";
            try {
                JSONObject newObj = obj.getJSONObject(key);
                result =  getURLString("med", newObj);
            } catch (JSONException e) {
                result = Utility.BASE_URL+getString(key,obj);
            }
            return result;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        JSONObject item = (JSONObject) getItem(position);
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView = null;

        rowView = inflater.inflate(R.layout.feature, null);
        holder.title=(TextView) rowView.findViewById(R.id.featureTitle);
        holder.title.setText(getString("title", item));

        holder.description = (TextView) rowView.findViewById(R.id.featureDescription);
        holder.description.setText(getString("description", item));

        holder.img = (ImageView) rowView.findViewById(R.id.featureImage);

        ImageLoader imgLoader = new ImageLoader();
        imgLoader.setImageView(holder.img);
        imgLoader.execute(getURLString("image", item));
        // Bitmap image = Utility.loadImageFromURL(getURLString("image", item));
       // holder.img.setImageResource(imageId[position]);
       // Log.d("IMAGE", image.toString());
       // holder.img.setImageBitmap(image);
       /*rowView.setOnClickListener(new DialogInterface.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_LONG).show();
            }
        });*/
        return rowView;
    }

}