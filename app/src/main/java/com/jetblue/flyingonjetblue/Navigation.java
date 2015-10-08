package com.jetblue.flyingonjetblue;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterViewFlipper;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.ViewAnimator;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class Navigation extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private static final String NAV_OPTIONS = "options";

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;


    private List<String> pageList;
    private JSONArray pages;

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("flying-model.json");
            Log.d("Stream","");

            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        JSONObject json = null;
        try {
            json = new JSONObject(loadJSONFromAsset());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            pageList = new ArrayList<>();

            pages =  json.getJSONArray("features");
            int count = pages.length(); // get totalCount of all jsonObjects
            for(int i=0 ; i< count; i++) {   // iterate through jsonArray

                String name = (pages.getJSONObject(i).getString("title"));
                pageList.add(name);
            }

            String[] finalList = new String[pageList.size()];
            finalList = pageList.toArray(finalList);

            // Set up the drawer.
            mNavigationDrawerFragment.setUp(
                    R.id.navigation_drawer,
                    (DrawerLayout) findViewById(R.id.drawer_layout), finalList);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        getActionBar().setIcon(android.R.color.transparent);

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        String pageJSON = "";
        try {
            pageJSON = pages.getJSONObject(position).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
Log.d("INFLATE", pageJSON);
        //Insert page data here
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter,R.anim.exit)
                .replace(R.id.container, DetailsFragment.newInstance(position + 1, pageJSON))
                .commit();
    }

    public void onSectionAttached(int number) {
        String key = pageList.get(number-1);
        mTitle = key;

    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.navigation, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailsFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_JSON = "json";

        private JSONObject data;
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static DetailsFragment newInstance(int sectionNumber, String pageJSON) {

            DetailsFragment fragment = new DetailsFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString(ARG_JSON, pageJSON);
            fragment.setArguments(args);

            return fragment;
        }

        public DetailsFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_navigation, container, false);

            //Just testing, this is actually useless
            int num =  getArguments() != null ? getArguments().getInt(ARG_SECTION_NUMBER) : -1;

            String str =  getArguments() != null ? getArguments().getString(ARG_JSON) : "";

            try {
                data = new JSONObject(str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Populate description Text box
            TextView description = (TextView) rootView.findViewById(R.id.descriptionContent);
            String text = getString("description");// Query your 'last value'
            description.setText(text);

            GridView content = (GridView) rootView.findViewById(R.id.pageContentGrid);


            JSONObject[] list = null;
            try {
                JSONArray objects = null;
                objects = data.getJSONArray("sections");
                list = Utility.jsonArraytoList(objects);

                PageDataAdapter dataAdapter = new PageDataAdapter(getActivity(), R.id.pageContentGrid,list);
                content.setAdapter(dataAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

           // content.setFlipInterval(1000);
           // content.startFlipping();
            return rootView;

        }

        private String getString(String key){
            String result = "";
            try {
                result = data.getString(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return Html.escapeHtml(result);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((Navigation) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
