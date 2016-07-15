package elghamry.android.com.elghamry_movie_app.MainUi;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;

import elghamry.android.com.elghamry_movie_app.DetailsUi.DetailsActivity;
import elghamry.android.com.elghamry_movie_app.DetailsUi.DetailsInfoFragment;
import elghamry.android.com.elghamry_movie_app.DetailsUi.ReviewsTrailersFragment;
import elghamry.android.com.elghamry_movie_app.R;

/**
 * Created by ELGHAMRY on 28/04/2016.
 */
public class SearchActivity extends AppCompatActivity implements DetailsListener{
    String email;
    boolean m2pane;
    //this flag handle the rotation from the mobile
    Boolean MysaveState=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(MysaveState==false){
            setContentView(R.layout.activity_search);
            email=getIntent().getStringExtra("email");


            Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
            LinearLayout Frame2 = (LinearLayout) findViewById(R.id.righ_pane_search);
            m2pane = Frame2 != null;


            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setLogo(R.drawable.whitelogo);
            getSupportActionBar().setDisplayUseLogoEnabled(true);

        }


        if(savedInstanceState==null){

            handleIntent(getIntent());
        }

    }


    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            //use the query to search
            SearchFragment SchFragment=SearchFragment.newInstance(query);
          //listen for details
            SchFragment.setDetailsListener(this);


            getSupportFragmentManager().beginTransaction()
                    .add(R.id.Search_fragment, SchFragment)


                    .commit();
        }
        MysaveState=true;
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



    @Override
    public void setDetails(String title,String Date,String Rate,String image,String Overview,String id,String src)
    {
        if(m2pane)
        {
            //calling the details fragment direct
            DetailsInfoFragment DetailsInfo = new DetailsInfoFragment();
            ReviewsTrailersFragment RevTraFragment = new ReviewsTrailersFragment();
            Bundle extras=new Bundle();
            extras.putString("title",title);
            extras.putString("release_date",Date);
            extras.putString("vote_average",Rate);
            extras.putString("image",image);
            extras.putString("overview",Overview);
            extras.putString("id",id);
            extras.putString("email",email);
            extras.putString("source",src);

            DetailsInfo.setArguments(extras);
            RevTraFragment.setArguments(extras);



            getSupportFragmentManager().beginTransaction().replace(R.id.frame_two,DetailsInfo).
                    replace(R.id.frame_two_2,RevTraFragment)
                    .commit();

        }
        else
        {
          //  Log.v("Activty rotation ", "setDetails: ");
            //calling detailsActivity
            Intent extras=new Intent(SearchActivity.this, DetailsActivity.class);
            extras.putExtra("title",title);
            extras.putExtra("release_date",Date);
            extras.putExtra("vote_average",Rate);
            extras.putExtra("image",image);
            extras.putExtra("overview",Overview);
            extras.putExtra("id",id);
            extras.putExtra("email",email);
            extras.putExtra("source",src);
            startActivity(extras);
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        MysaveState=true;
    }


    @Override
    public boolean isChangingConfigurations() {

        MysaveState=true;
        return super.isChangingConfigurations();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MysaveState=true;
    }
}

