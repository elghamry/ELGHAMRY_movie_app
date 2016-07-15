package elghamry.android.com.elghamry_movie_app.MainUi;


import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import elghamry.android.com.elghamry_movie_app.BuildConfig;
import elghamry.android.com.elghamry_movie_app.Model.GridItem;
import elghamry.android.com.elghamry_movie_app.R;

/**
 * Created by ELGHAMRY on 22/04/2016.
 */
public class AsyncDataLoader extends Fragment {
    DetailsListener mListener;
    private static final String TAG = MainActivity.class.getSimpleName();
    //this flag for savethestate of the fragment , prevent overloading caused by onstart, and save the rotation state
    //in every tab in the tablayout
    Boolean MysaveState=false;
    private GridView  mGridView;
    private GridViewAdapter mGridAdapter;
    int position;

    private ArrayList<GridItem> mGridData = new ArrayList<GridItem>();;

    private final String BaseURL = "http://api.themoviedb.org/3/movie/";

    private final String Key = "api_key";
    View rootView;
     String type;


    public AsyncDataLoader() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    //efficient loader to parse many types of Jsons
    public static AsyncDataLoader newInstance(String type) {



        AsyncDataLoader fragment = new AsyncDataLoader();
        Bundle args = new Bundle();
        //get the type and parse it

        args.putString("type", type);
        fragment.setArguments(args);

        return fragment;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //here we get the type
        type = getArguments().getString("type");
       // Log.v(TAG, "test number called "+type);


        rootView = inflater.inflate(R.layout.fragment_gridview, container, false);


        mGridView = (GridView) rootView.findViewById(R.id.gridView);

        mGridAdapter = new GridViewAdapter(getActivity(), R.layout.grid_item_layout, mGridData);
       mGridView.setAdapter(mGridAdapter);





        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                GridItem item = (GridItem) parent.getItemAtPosition(position);




                //Pass the image title and url to DetailsActivity
                mListener.setDetails(item.getTitle()
                        , item.getRelease_date()
                        , item.getVote_average()
                        , item.getImage()

                        , item.getOverview()


               ,item.getId(),"AsyncData");

            }
        });


        return rootView;
    }


    public class Grid_async extends AsyncTask<String, Void, Integer> {

        @Override

        protected Integer doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String JsonStr = null;

            Integer result = 0;
            try {

                Uri uribuilt = Uri.parse(BaseURL).buildUpon().appendPath(params[0]).appendQueryParameter(Key, BuildConfig.Api_key).build();
                URL url = new URL(uribuilt.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                JsonStr = buffer.toString();
                urlConnection.disconnect();

            } catch (IOException e) {

                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {

                    }
                }
            }

            parseResult(JsonStr);
            result = 1;


            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Let us update UI

            if (result == 1) {

                // successful json parsing



               // Log.v(TAG, "onPostExecute: "+mGridData.get(0).getTitle());


           mGridAdapter.setGridData(mGridData);
                MysaveState=true;









            } else {
                Toast.makeText(getActivity(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }


        }
    }

//method to parse json
    private void parseResult(String result) {
        try {
            JSONObject result_data = new JSONObject(result);
            JSONArray posts = result_data.getJSONArray("results");
            GridItem item;
           // mGridData.clear();

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                String title = post.optString("title");
                item = new GridItem();
                item.setTitle(title);
                String ImageBase = "http://image.tmdb.org/t/p/w185";


                item.setImage(ImageBase + post.getString("poster_path"));
                item.setOverview(post.getString("overview"));
                item.setRelease_date(post.getString("release_date"));
                item.setVote_average(post.getString("vote_average"));
                item.setId(post.getString("id"));

                mGridData.add(item);
                Log.d(TAG, "onStart: welcome baby "+title);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //check the network
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
//prevent conflict in postexecute if we use one object from asynctask
    private void updateData() {
        if(type=="popular")
        {
            Grid_async fetch = new Grid_async();

            fetch.execute(type);
        }
        else
        {
            Grid_async fetch1 = new Grid_async();

            fetch1.execute(type);
        }




    }
    //eh lazmtk
//


    @Override
    public void onStart() {
        super.onStart();
       // mListener=null;
        //prevent calling onstart when onbackpressed from detalisActivity As on restart in Activities lifecycle ^_^
        if(MysaveState==false)
        {
            if(isNetworkConnected()){
                mGridAdapter.clear();

                updateData();
            }

        }
        else
        {

        }









    }

    @Override
    public void onStop() {
        super.onStop();

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //return to oncreateview and updatedata method need to recall :D
        MysaveState=false;
    }

    @Override
    public void onResume() {
        super.onResume();
        getArguments();


    }

    public void setDetailsListener(DetailsListener detailsListener) {
        mListener=detailsListener;
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


        position = mGridView.getFirstVisiblePosition();

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mGridView.setNumColumns(3);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            mGridView.setNumColumns(2);
        }
        //i prefere this method because the other one take time for loading :D
        mGridView.setSelection(position);



    }
}
