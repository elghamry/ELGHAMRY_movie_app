package elghamry.android.com.elghamry_movie_app.MainUi;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.firebase.client.Firebase;

import elghamry.android.com.elghamry_movie_app.Constants;
import elghamry.android.com.elghamry_movie_app.Model.movie;
import elghamry.android.com.elghamry_movie_app.R;

/**
 * Created by ELGHAMRY on 27/04/2016.
 */
public class FavouriteFragment extends Fragment{
    GridView mGridView;
    DetailsListener mListener;
    FirebaseFavouriteAdapter mAdapter;
    int position;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_gridview, container, false);
        String email=getActivity().getIntent().getStringExtra("email");


        mGridView = (GridView) rootView.findViewById(R.id.gridView);
        //firebase refrence

        Firebase mref=new Firebase(Constants.FIREBASE_URL_FAV);

        mAdapter = new FirebaseFavouriteAdapter(getActivity(), movie.class,R.layout.grid_item_layout,mref.child(email));
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                movie selectedMovie = mAdapter.getItem(position);
                if (selectedMovie != null) {

                  //  Intent intent = new Intent(getActivity(), DetailsActivity.class);


                    //Pass data to DetailsActivity
                    mListener.setDetails(selectedMovie.getTitle()
                            , selectedMovie.getDate()
                            , selectedMovie.getVote()
                            , selectedMovie.getImage()
//determine from where the detailsActivity called
                            , selectedMovie.getOverview(),selectedMovie.getId(),"Favourite");





                }
            }
        });
        return rootView;

    }

    public static FavouriteFragment newInstance() {
        Bundle args = new Bundle();

        FavouriteFragment fragment = new FavouriteFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
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
