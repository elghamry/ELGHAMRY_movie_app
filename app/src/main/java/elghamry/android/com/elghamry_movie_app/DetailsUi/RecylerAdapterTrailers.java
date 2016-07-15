package elghamry.android.com.elghamry_movie_app.DetailsUi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import elghamry.android.com.elghamry_movie_app.Model.Trailer;
import elghamry.android.com.elghamry_movie_app.R;

/**
 * Created by ELGHAMRY on 31/03/2016.
 */
public class RecylerAdapterTrailers extends RecyclerView.Adapter<RecylerAdapterTrailers.ViewHolder> {
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    private List<Trailer> trailerList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public ImageView image;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.trailer_no);
            image = (ImageView) view.findViewById(R.id.play);
        }
    }

    public RecylerAdapterTrailers(List<Trailer> trailerList) {
        this.trailerList = trailerList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Trailer trailer = trailerList.get(position);
        holder.title.setText(trailer.getName());


    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    public void setTrailerList(List<Trailer> trailerList){

        this.trailerList=trailerList;
        this.notifyDataSetChanged();
}


}
