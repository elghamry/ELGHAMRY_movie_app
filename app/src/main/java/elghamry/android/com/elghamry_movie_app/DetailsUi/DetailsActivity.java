
package elghamry.android.com.elghamry_movie_app.DetailsUi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import elghamry.android.com.elghamry_movie_app.R;

/**
 * Created by ELGHAMRY on 18/03/2016.
 */

public class DetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Bundle extras = getIntent().getExtras();
        if (savedInstanceState == null) {
            DetailsInfoFragment DetailInfo = new DetailsInfoFragment();
            DetailInfo.setArguments(extras);
            ReviewsTrailersFragment RevTraFragment=new ReviewsTrailersFragment();
            RevTraFragment.setArguments(extras);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, DetailInfo)
                    .add(R.id.reviews_container, RevTraFragment)

                    .commit();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.drawable.whitelogo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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