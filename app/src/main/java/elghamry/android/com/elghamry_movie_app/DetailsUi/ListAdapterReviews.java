package elghamry.android.com.elghamry_movie_app.DetailsUi;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import elghamry.android.com.elghamry_movie_app.Model.Review;
import elghamry.android.com.elghamry_movie_app.R;

/**
 * Created by ELGHAMRY on 25/03/2016.
 */

public class ListAdapterReviews extends ArrayAdapter<Review>{
    private ArrayList<Review> mlistData = new ArrayList<Review>();
    private Context mContext;
    private int layoutResourceId;

    public ListAdapterReviews(Context mContext, int layoutResourceId, ArrayList<Review> mlistData) {
        super(mContext, layoutResourceId, mlistData);
        this.mContext = mContext;
        this.layoutResourceId = layoutResourceId;
        this.mlistData=mlistData;

    }
    public void setListData(ArrayList<Review> mlistData) {

        this.mlistData = mlistData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =( (Activity)mContext).getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.review, null, true);
        TextView author = (TextView) listViewItem.findViewById(R.id.author);
        TextView content = (TextView) listViewItem.findViewById(R.id.content);
        Review item = mlistData.get(position);


        author.setText(item.getAuthor());
        content.setText(item.getContent());


        return listViewItem;
    }


}
