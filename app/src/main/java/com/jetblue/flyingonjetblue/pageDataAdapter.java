package com.jetblue.flyingonjetblue;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
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

    private static LayoutInflater inflater = null;
    JSONObject[] objects;

    public PageDataAdapter(Context context, int textViewResourceId, JSONObject[] objects) {
        super(context, textViewResourceId, objects);
        Log.d("CREATE", "PAGE DATA ADAPTER");
        this.objects = objects;
        this.context = context;

        // TODO Auto-generated constructor stub
        inflater = (LayoutInflater) context.
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

    public class Holder {
        TextView title;
        TextView description;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        JSONObject item = (JSONObject) getItem(position);
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView = null;

        rowView = inflater.inflate(R.layout.feature, null);
        holder.title = (TextView) rowView.findViewById(R.id.featureTitle);
        holder.title.setText(Utility.getString("title", item));

        holder.description = (TextView) rowView.findViewById(R.id.featureDescription);
        holder.description.setText(Utility.getString("description", item));
        holder.img = (ImageView) rowView.findViewById(R.id.featureImage);

        Utility.setImage(Utility.getURLString("image", item), holder.img, Globals.getInstance().getImageStore());

       GridView items = (GridView) rowView.findViewById(R.id.featureItems);

        JSONObject[] list = null;
        try {
            JSONArray objects = null;
            objects = item.getJSONArray("items");
            list = Utility.jsonArraytoList(objects);
            FeatureItemsAdapter dataAdapter = new FeatureItemsAdapter(context, R.id.featureItems, list);
            items.setAdapter(dataAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }


       /*rowView.setOnClickListener(new DialogInterface.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_LONG).show();
            }
        });*/
        return rowView;
    }
    public class FeatureItemsAdapter extends ArrayAdapter {

        Context context;

        private LayoutInflater inflater = null;
        JSONObject[] objects;

        public FeatureItemsAdapter(Context context, int textViewResourceId, JSONObject[] objects) {
            super(context, textViewResourceId, objects);
            Log.d("CREATE", "PAGE DATA ADAPTER");
            this.objects = objects;
            this.context = context;

            // TODO Auto-generated constructor stub
            inflater = (LayoutInflater) context.
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

        public class Holder {
            TextView title;
            TextView description;
            ImageView img;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            JSONObject item = (JSONObject) getItem(position);
            // TODO Auto-generated method stub
            Holder holder = new Holder();
            View rowView = null;

            rowView = inflater.inflate(R.layout.feature, null);
            holder.title = (TextView) rowView.findViewById(R.id.featureTitle);
            holder.title.setText(Utility.getString("title", item));

            holder.description = (TextView) rowView.findViewById(R.id.featureDescription);
            holder.description.setText(Utility.getString("description", item));
            holder.img = (ImageView) rowView.findViewById(R.id.featureImage);

            Utility.setImage(Utility.getURLString("image", item), holder.img, Globals.getInstance().getImageStore());



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
}