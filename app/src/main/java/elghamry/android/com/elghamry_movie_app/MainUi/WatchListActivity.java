package elghamry.android.com.elghamry_movie_app.MainUi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import elghamry.android.com.elghamry_movie_app.R;

/**
 * Created by ELGHAMRY on 28/04/2016.
 */
public class WatchListActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_list);

        Bundle extras=getIntent().getExtras();
        if (savedInstanceState == null) {
            WatchListFragment WLFragment= new WatchListFragment();
            WLFragment.setArguments(extras);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.watch_list_container, WLFragment)


                    .commit();
           // Log.d("test watchlist", "onCreateView: ");
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(R.drawable.whitelogo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }}
}
