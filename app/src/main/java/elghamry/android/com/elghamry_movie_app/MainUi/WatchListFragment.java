package elghamry.android.com.elghamry_movie_app.MainUi;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.Firebase;

import elghamry.android.com.elghamry_movie_app.Constants;
import elghamry.android.com.elghamry_movie_app.Model.WatchListItem;
import elghamry.android.com.elghamry_movie_app.R;

/**
 * Created by ELGHAMRY on 28/04/2016.
 */
public class WatchListFragment extends Fragment {
    ListView watch_listViw ;
    FirebaseWatchListAdapter mAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.watch_list_fragment, container, false);
        watch_listViw = (ListView)rootView.findViewById(R.id.watch_list);
        String email = getArguments().getString("email");
       // Log.d("test watchlist", "onCreateView: "+email);
        Firebase mref = new Firebase(Constants.FIREBASE_URL_WL).child(email);
        mAdapter= new FirebaseWatchListAdapter(getActivity(), WatchListItem.class,R.layout.watch_list_item,mref);

        watch_listViw.setAdapter(mAdapter);
        watch_listViw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WatchListItem item = mAdapter.getItem(position);
                WatchListDialog.newInstance(item.getId()).show(getActivity().getFragmentManager(),"Delete");
            }
        });


        return rootView;

    }
}
