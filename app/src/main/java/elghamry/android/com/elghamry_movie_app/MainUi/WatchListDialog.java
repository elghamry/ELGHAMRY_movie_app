package elghamry.android.com.elghamry_movie_app.MainUi;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

import com.firebase.client.Firebase;

import elghamry.android.com.elghamry_movie_app.Constants;
import elghamry.android.com.elghamry_movie_app.R;

/**
 * Created by ELGHAMRY on 28/04/2016.
 */
public class WatchListDialog extends DialogFragment {
    String id_w;

    public static WatchListDialog newInstance(String id) {
        WatchListDialog ListDialogFragment = new WatchListDialog();
        Bundle bundle = new Bundle();
        //parse id to make use it as a primary key of the movie to remove it
        bundle.putString("id",id);
        ListDialogFragment.setArguments(bundle);
        return ListDialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
         id_w = getArguments().getString("id");
        final String email=getActivity().getIntent().getStringExtra("email");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomTheme_Dialog);
        builder.setMessage("Do you want to delete it from the watchlist?");
        // Get the layout inflater
        builder.setPositiveButton("Delete" ,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //firebase refrence movie location
                Firebase mref = new Firebase(Constants.FIREBASE_URL_WL).child(email).child(id_w);
                mref.removeValue();
            }
        });

        return builder.create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}

