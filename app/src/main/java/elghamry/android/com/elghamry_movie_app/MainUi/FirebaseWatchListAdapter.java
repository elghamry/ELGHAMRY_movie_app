package elghamry.android.com.elghamry_movie_app.MainUi;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

import elghamry.android.com.elghamry_movie_app.Model.WatchListItem;
import elghamry.android.com.elghamry_movie_app.R;
import elghamry.android.com.elghamry_movie_app.Utils;

/**
 * Created by ELGHAMRY on 28/04/2016.
 */
public class FirebaseWatchListAdapter extends FirebaseListAdapter<WatchListItem> {

/**
 * Public constructor that initializes private instance variables when adapter is created
 */
public FirebaseWatchListAdapter(Activity activity, Class<WatchListItem> modelClass, int modelLayout,

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
protected void populateView(View view, WatchListItem item) {

        ImageView image = (ImageView)view.findViewById(R.id.w_img) ;
    TextView text1=(TextView)view.findViewById(R.id.w_title);
    TextView text2=(TextView)view.findViewById(R.id.w_date);

        image.setImageBitmap(Utils.decodeBase64(item.getImg()));
    text1.setText(item.getTitle());
    text2.setText(item.getDate());
        }

        }