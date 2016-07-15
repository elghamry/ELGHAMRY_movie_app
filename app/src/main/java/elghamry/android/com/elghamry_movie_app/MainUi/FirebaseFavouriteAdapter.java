package elghamry.android.com.elghamry_movie_app.MainUi;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

import elghamry.android.com.elghamry_movie_app.Model.movie;
import elghamry.android.com.elghamry_movie_app.R;
import elghamry.android.com.elghamry_movie_app.Utils;

/**
 * Created by ELGHAMRY on 27/04/2016.
 */
public class FirebaseFavouriteAdapter extends FirebaseListAdapter<movie>{

    /**
     * Public constructor that initializes private instance variables when adapter is created
     */
    public FirebaseFavouriteAdapter(Activity activity, Class<movie> modelClass, int modelLayout,

                                    Query ref) {
        super(activity, modelClass, modelLayout, ref);
        this.mActivity = activity;
    }

    /**
     * Protected method that populates the view attached to the adapter (list_view_active_lists)
     * with items inflated from single_active_list.xml
     * populateView also handles data changes and updates the listView accordingly
     */
    @Override
    protected void populateView(View view, movie mov) {

        /**
         * Grab the needed Imageviews and strings
         */
        ImageView image = (ImageView)view.findViewById(R.id.grid_item_image) ;
        //decode the image parsed from the server to bitmap image
       image.setImageBitmap(Utils.decodeBase64(mov.getImage()));
    }

}