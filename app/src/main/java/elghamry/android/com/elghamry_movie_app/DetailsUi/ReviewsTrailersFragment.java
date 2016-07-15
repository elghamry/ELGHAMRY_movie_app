package elghamry.android.com.elghamry_movie_app.DetailsUi;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

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
import java.util.List;

import elghamry.android.com.elghamry_movie_app.Model.Review;
import elghamry.android.com.elghamry_movie_app.Model.Trailer;
import elghamry.android.com.elghamry_movie_app.R;

/**
 * Created by ELGHAMRY on 25/03/2016.
 */
public class ReviewsTrailersFragment extends Fragment {
    private final String BaseURL = "http://api.themoviedb.org/3/movie/";
    private final String Key = "api_key";
    ListAdapterReviews reviews_listAdapter;
    RecylerAdapterTrailers trailers_listAdapter;
    View rootView;
    RecyclerView recycler_view_trailers;
    private ArrayList<Review> ListData;
    private List<Trailer> ListTrailer;
    private String id;

    public static ReviewsTrailersFragment newInstance() {
        ReviewsTrailersFragment fragment = new ReviewsTrailersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //inflate xml

        rootView = inflater.inflate(R.layout.reviews_trailers_fragment, container, false);
        final Animation animAlpha2 = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in_center);


        ListData = new ArrayList<>();
        ListTrailer = new ArrayList<>();
        //get the id for using it to fetch trailers and reviews
        id = getArguments().getString("id");

//set recycler adapter and view
        reviews_listAdapter = new ListAdapterReviews(getActivity(), R.layout.review, ListData);
        recycler_view_trailers = (RecyclerView) rootView.findViewById(R.id.trailers_view);
        trailers_listAdapter = new RecylerAdapterTrailers(ListTrailer);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());


        recycler_view_trailers.setLayoutManager(mLayoutManager);
        recycler_view_trailers.setItemAnimator(new DefaultItemAnimator());


        recycler_view_trailers.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recycler_view_trailers.setAdapter(trailers_listAdapter);
        recycler_view_trailers.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recycler_view_trailers, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Trailer trailer = ListTrailer.get(position);
                view.startAnimation(animAlpha2);
//send implicit intent to the browser or youtube app
                String url = "http://www.youtube.com/watch?v=" + trailer.getSource();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        TextView review_hint = (TextView) rootView.findViewById(R.id.Review_hint);
        review_hint.setText("SHOW THE REVIEWS");

        LinearLayout material_btn = (LinearLayout) rootView.findViewById(R.id.material_review_button);
        material_btn.setVisibility(View.VISIBLE);
        // Log.v(LOG_TAG, fragment.getListData().get(0).getAuthor());
        //show the reviews as a dilog using dilogplus library
        material_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPlus dialog = DialogPlus.newDialog(getActivity())
                        .setAdapter(reviews_listAdapter)
                        .setExpanded(true)
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                            }
                        })
                        .setContentWidth(ViewGroup.LayoutParams.WRAP_CONTENT)  // or any custom width ie: 300
                        .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setMargin(0, 0, 0, 0)
                        .setPadding(100, 150, 100, 150)
                        .setCancelable(true)

                        .create();


                dialog.show();

            }
        });


        return rootView;
    }

    private void parseReviews(String result) {
        try {
            JSONObject result_data = new JSONObject(result);
            JSONArray posts = result_data.getJSONArray("results");

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                String author = post.getString("author");
                Review item;
                item = new Review();
                item.setAuthor(author + ": ");


                item.setContent(post.getString("content"));

                ListData.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseTrailers(String result) {
        try {
            JSONObject result_data = new JSONObject(result);
            JSONArray posts = result_data.getJSONArray("youtube");

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                String source = post.getString("source");
                Trailer item;
                item = new Trailer();
                item.setSource(source);


                item.setName(post.getString("name"));

                ListTrailer.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void update() {
        //2 fetch methods one for trialers and the second for the reviews

        AsyncHttpTask fetch = new AsyncHttpTask();

        fetch.execute("reviews");
        AsyncHttpTask fetch1 = new AsyncHttpTask();
        fetch1.execute("trailers");
    }

    @Override
    public void onStart() {
        if(isNetworkConnected()){
        update();}


        super.onStart();
        getArguments();

    }
//set the listner for recyclerview
    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    protected class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String JsonStr = null;

            Integer result = 0;
            try {
                // Create Apache HttpClient
                Uri uribuilt = Uri.parse(BaseURL).buildUpon().appendPath(id).appendPath(params[0]).appendQueryParameter(Key, "7d8a96e9c9e43c5b4682ddc331b0e568").build();
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
            if (params[0] == "reviews") {
                parseReviews(JsonStr);

                return 3;

            }
            if (params[0] == "trailers") {
                parseTrailers(JsonStr);
                // Log.d(LOG_TAG, urlConnection.toString());

                result = 1;


                // This will only happen if there was an error getting or parsing .

                return result;
            }

            result = 2;




            return result;

        }

        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Let us update UI

            if (result != 2) {
                if(result==1)
                trailers_listAdapter.setTrailerList(ListTrailer);
                else
                reviews_listAdapter.setListData(ListData);


            } else if (result == 2) {
                Toast.makeText(getActivity(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }

        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}
