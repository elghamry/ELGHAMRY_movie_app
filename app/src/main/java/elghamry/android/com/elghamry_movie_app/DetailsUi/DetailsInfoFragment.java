package elghamry.android.com.elghamry_movie_app.DetailsUi;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

import elghamry.android.com.elghamry_movie_app.Constants;
import elghamry.android.com.elghamry_movie_app.Model.WatchListItem;
import elghamry.android.com.elghamry_movie_app.Model.movie;
import elghamry.android.com.elghamry_movie_app.R;
import elghamry.android.com.elghamry_movie_app.Utils;

/**
 * Created by ELGHAMRY on 25/03/2016.
 */
public class DetailsInfoFragment extends Fragment {
    String id;
    Button btnWatch;
    Boolean Flag = false;
    Boolean FalgW = false;
    Firebase mref_email;
    Firebase mref_email_watch;
    private String myBase64Image = null;
    private ImageView imageView;
    private LinearLayout linearDetails;

    public DetailsInfoFragment() {
    }

    public static DetailsInfoFragment newInstance() {
        DetailsInfoFragment fragment = new DetailsInfoFragment();
        Bundle args = new Bundle();
        // args.putString("type", type);
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
        // Log.d("test name ", "onCreateView: "+getActivity().getIntent().getStringExtra("name"));
        String email = getArguments().getString("email");
//refrence to firebase favourite url
        Firebase mref = new Firebase(Constants.FIREBASE_URL_FAV);


        View rootView = inflater.inflate(R.layout.details_fragment, container, false);


//get information from src
        final String title = getArguments().getString("title");
        final String image = getArguments().getString("image");
        // Log.d("test id", "onCreateView: " + image);
        final String overview = getArguments().getString("overview");
        final String release_date = getArguments().getString("release_date");
        final String vote_average = getArguments().getString("vote_average");
        final String src = (getArguments().getString("source"));

        id = getArguments().getString("id");

//get information from src
        //  Log.d("test id", "onCreateView: " + id);

//set information to ui
        TextView titleTextView = (TextView) rootView.findViewById(R.id.title);
        imageView = (ImageView) rootView.findViewById(R.id.grid_detail_image);
        TextView overviewTextView = (TextView) rootView.findViewById(R.id.overview);
        TextView release_dateTextView = (TextView) rootView.findViewById(R.id.release_date);
        TextView vote_averageTextView = (TextView) rootView.findViewById(R.id.vote_average);
        titleTextView.setText(Html.fromHtml(title));
        overviewTextView.setText("Overview:\n" + overview);
        release_dateTextView.setText("Date: " + release_date);
        vote_averageTextView.setText("Rate: " + vote_average);
        linearDetails = (LinearLayout) rootView.findViewById(R.id.details);
        final ImageView favourite_flag = (ImageView) rootView.findViewById(R.id.favourite_flag_image);
        //set information to ui
//refrence to firebase watch_list url
        if (email != null) {
            mref_email_watch = new Firebase(Constants.FIREBASE_URL_WL).child(email).child(id);
        }
        if (email != null) {
            mref_email = mref.child(email).child(id);
        }
        // Flag = false;
       /* try {
            FavouriteDbHelper mDbHelper = new FavouriteDbHelper(getActivity());

            SQLiteDatabase db = mDbHelper.getReadableDatabase();
            Cursor cursor = db.query(FavouriteContract.FavouriteEntry.TABLE_NAME,
                    new String[] {FavouriteContract.FavouriteEntry.COLUMN_NAME_ID},
                    FavouriteContract.FavouriteEntry.COLUMN_NAME_ID+" = ?",
                    new String[] {id},
                    null, null,null);
            if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
                //cursor is empty
                Flag = false;

            }
            else
            {
                Flag=true;
               favourite_flag.setImageResource(R.drawable.ic_remove_red_eye_yellow_18dp);
            }

            cursor.close();
            db.close();
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(getActivity(), "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
*/


       /* favourite_flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Flag==false)
                {
                    FavouriteDbHelper mDbHelper = new FavouriteDbHelper(getActivity());
                    // Gets the data repository in write mode
                     SQLiteDatabase db = mDbHelper.getWritableDatabase();
                    // Create a new map of values, where column names are the keys
                    ContentValues values = new ContentValues();
                    values.put(FavouriteContract.FavouriteEntry.COLUMN_NAME_ID, id);
                    values.put(FavouriteContract.FavouriteEntry.COLUMN_NAME_TITLE, title);
                    // Insert the new row, returning the primary key value of the new row
                    long newRowId;
                    newRowId = db.insert(
                            FavouriteContract.FavouriteEntry.TABLE_NAME
                            ,null,
                            values);

                    db.close();
                    favourite_flag.setImageResource(R.drawable.ic_remove_red_eye_yellow_18dp);
                }
                else
                {
                    FavouriteDbHelper mDbHelper = new FavouriteDbHelper(getActivity());
                    // Gets the data repository in write mode
                    SQLiteDatabase db = mDbHelper.getWritableDatabase();
                    db.delete(FavouriteContract.FavouriteEntry.TABLE_NAME,
                            FavouriteContract.FavouriteEntry.COLUMN_NAME_ID+" = ?",
                            new String[] {id});

                    db.close();
                    favourite_flag.setImageResource(R.drawable.ic_launcher);
                }









            }
        });*/

//check movie is exit in favourites or not to draw the ui according that
        mref_email.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    favourite_flag.setImageResource(R.drawable.ic_favorite_accent_36dp);
                    Flag = true;
                } else
                    favourite_flag.setImageResource(R.drawable.ic_favorite_black_36dp);


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        //handling if the Favourite_img clicked !! :D
        favourite_flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movie curr_movie = null;

                try {
                    assert src != null;
                    if (src.equals("AsyncData"))
                        curr_movie = new movie(release_date, id, convertImageHigh(image), overview, title, vote_average);
                    else
                        curr_movie = new movie(release_date, id, image, overview, title, vote_average);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (!Flag) {
                    mref_email.setValue(curr_movie);
                    favourite_flag.setImageResource(R.drawable.ic_favorite_accent_36dp);
                    Flag = true;
                } else
                ///remove here
                {
                    mref_email.removeValue();
                    Flag = false;
                    favourite_flag.setImageResource(R.drawable.ic_favorite_black_36dp);
                }

            }
        });
        btnWatch = (Button) rootView.findViewById(R.id.btn_watch_list);
//check is the movie exist in the watchlist
        mref_email_watch.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    btnWatch.setText("added to watchlist");
                    FalgW = true;
                } else {
                    FalgW = false;
                    btnWatch.setText("add to watchlist");
                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        //determine what should watch_btn do
        btnWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WatchListItem item = null;
                try {
                    assert src != null;
                    if (src.equals("AsyncData"))
                        item = new WatchListItem(title, convertImageHigh(image), release_date, id);
                    else
                        item = new WatchListItem(title, image, release_date, id);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!FalgW) {
                    mref_email_watch.setValue(item);
                    btnWatch.setText("added to watchlist");
                    FalgW = true;

                } else


                {
                    mref_email_watch.removeValue();
                    FalgW = false;
                    btnWatch.setText("add to watchlist");


                }

            }
        });

//if the activity is called from AsyncData loader we will depend on the url of the image to appear it
        assert src != null;
        if (src.equals("AsyncData"))

            Picasso.with(getActivity()).load(image).into(imageView);
        else
            //else we will decode the string that coming from the server to set the image
            imageView.setImageBitmap(Utils.decodeBase64(image));


        return rootView;
    }

    //high resulation converter
    String convertImageHigh(String url) throws IOException {


        Picasso.with(getActivity()).load(url).into(new Target() {

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                myBase64Image = Utils.encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);
            }

            @Override
            public void onBitmapFailed(final Drawable errorDrawable) {
                Log.d("TAG", "FAILED");
            }

            @Override
            public void onPrepareLoad(final Drawable placeHolderDrawable) {
                Log.d("TAG", "Prepare Load");
            }
        });


        return myBase64Image;
    }
    //low resulation conerter

    String convertImageLow(String url) throws IOException {


        Picasso.with(getActivity()).load(url).into(new Target() {

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                myBase64Image = Utils.encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 60);
            }

            @Override
            public void onBitmapFailed(final Drawable errorDrawable) {
                Log.d("TAG", "FAILED");
            }

            @Override
            public void onPrepareLoad(final Drawable placeHolderDrawable) {
                Log.d("TAG", "Prepare Load");
            }
        });

        return myBase64Image;
    }

    //we need to handle the size of the imageview after rotation
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            LinearLayout.LayoutParams childParam1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            childParam1.weight = 1;
            LinearLayout.LayoutParams childParam2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            childParam2.weight = 2;

            imageView.setLayoutParams(childParam1);
            linearDetails.setLayoutParams(childParam2);



        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            LinearLayout.LayoutParams childParam1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            childParam1.weight = 2;
            LinearLayout.LayoutParams childParam2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            childParam2.weight = 1.5f;

            imageView.setLayoutParams(childParam1);
            linearDetails.setLayoutParams(childParam2);
        }

    }
}
