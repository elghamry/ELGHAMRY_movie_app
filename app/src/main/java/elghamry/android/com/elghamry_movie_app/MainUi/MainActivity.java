package elghamry.android.com.elghamry_movie_app.MainUi;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;

import elghamry.android.com.elghamry_movie_app.Constants;
import elghamry.android.com.elghamry_movie_app.DetailsUi.DetailsActivity;
import elghamry.android.com.elghamry_movie_app.DetailsUi.DetailsInfoFragment;
import elghamry.android.com.elghamry_movie_app.DetailsUi.ReviewsTrailersFragment;
import elghamry.android.com.elghamry_movie_app.LoginUi.Login;
import elghamry.android.com.elghamry_movie_app.R;
import elghamry.android.com.elghamry_movie_app.Utils;

public class MainActivity extends AppCompatActivity implements DetailsListener {
    TabLayout tabLayout;
    SectionPagerAdapter adapter;
    ViewPager viewPager;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigation;
    TextView name_text;
    TextView email_text;
    String name, email;
    SearchView searchView;
    boolean m2pane;
    AsyncDataLoader TopratedFragment;
    AsyncDataLoader PopularFragment;
    FavouriteFragment FavFragment;
    //LinearLayout Left_pane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //link the xml
        // if ( savedInstanceState==null){
        setContentView(R.layout.activity_main);
        //get the intents from login to parse it then allover the app to refrence our firebase
        name = this.getIntent().getStringExtra("name");
        email = this.getIntent().getStringExtra("email");
        Log.d("test name", "onCreate: " + name);
        FrameLayout Frame2 = (FrameLayout) findViewById(R.id.frame_two);
        if (Frame2 == null) {
            m2pane = false;
        } else {
            m2pane = true;
        }
//intialize toprated

        TopratedFragment = AsyncDataLoader.newInstance("top_rated");

        TopratedFragment.setDetailsListener(this);
        //intialize popular
        PopularFragment = AsyncDataLoader.newInstance("popular");

       PopularFragment.setDetailsListener(this);
        FavFragment = FavouriteFragment.newInstance();
        FavFragment.setDetailsListener(this);
    initializeScreen();
}


   //drawer sync
    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);

    }
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        /* Inflate the menu; this adds items to the action bar if it is present. */
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);

         searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //exit the search after do it
                searchView.setIconified(true);
                searchView.clearFocus();

                // call the request here

                // call collapse action view on 'MenuItem'
                (menu.findItem(R.id.search)).collapseActionView();

                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item))
            return true;
        int id = item.getItemId();


    if (id == R.id.action_logout) {

        Logout();

      }

        return super.onOptionsItemSelected(item);

    }
    public void initializeScreen() {
        viewPager = (ViewPager) findViewById(R.id.pager);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        /**
         * Create SectionPagerAdapter\  with limit 3
         **/
        adapter = new SectionPagerAdapter(getSupportFragmentManager());


                viewPager.setOffscreenPageLimit(3);

        viewPager.setAdapter(adapter);

        /**
         * Setup the mTabLayout with view pager
         */
        tabLayout.setupWithViewPager(viewPager);


/// Setup  the drawer oooh :D
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(R.drawable.whitelogo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.hello_world, R.string.hello_world);
        drawerLayout.setDrawerListener(drawerToggle);

       navigation = (NavigationView) findViewById(R.id.navigation_view);
        View hView =  navigation.inflateHeaderView(R.layout.navigation_header);
         name_text = (TextView)hView.findViewById(R.id.name);
        email_text = (TextView) hView.findViewById(R.id.email);

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.navigation_item_1:
                        if(m2pane){



clearRightPane();
                            Bundle extras = getIntent().getExtras();
                            WatchListFragment WchFragment= new WatchListFragment();
                            WchFragment.setArguments(extras);

                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_two_2,WchFragment).commit();

                        }
                        else{
                        Intent intent = new Intent(MainActivity.this, WatchListActivity.class);


                        //Pass email to watchlistActivity
                        intent.
                                putExtra("email", email)
                                .putExtra("source","AsyncData");
                        // Log.v(TAG,getArguments().getString("name"));



                        startActivity(intent);}
                        //return true;

                        break;
                    case R.id.navigation_item_2:
                        Logout();
                        break;

                }


                navigation.setCheckedItem(id);
                drawerLayout.closeDrawer(navigation);
                return false;
            }
        });
//set the drawer info like username and decode the email from ,com to .com because firebase refuse .com so we save it ,com
        //and decode it when retrieving it
        name_text.setText(name);
        email_text.setText(Utils.decodeEmail(email));



    }


    public class SectionPagerAdapter extends FragmentStatePagerAdapter {

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(Object object) {

            return POSITION_NONE;
        }

        /**
         * Use positions
         *
         */
        @Override
        public Fragment getItem(int position) {


Fragment fragment=null;
            /**
             * Set fragment to different fragments depending on position in ViewPager
             */
            switch (position) {
                case 0:

                   fragment=TopratedFragment;
               break;




                case 1:
                    fragment= PopularFragment;

                    break;
                case 2:
                    fragment=FavFragment;
                    break;

                default:
                    fragment= TopratedFragment;
                    break;






                // Log.v("am here", "View getItem: "+"default = 0");

            }





            return fragment;


        }

        @Override
        public int getCount() {
            return 3;
        }

        /**
         * Set string resources as titles for each fragment by it's position
         *
         * @param position
         */
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 2:
                    return "Favourite";
                case 1:
                    // return getString(R.string.pager_title_shopping_lists);
                    return "Popular";
                case 0:
                default:
                    //return getString(R.string.pager_title_meals);
                    return "Top rated";
            }
        }
    }

    //passing email to searchActiv
    @Override
    public void startActivity(Intent intent) {
        // check if search intent
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            intent.putExtra("email", email);
        }

        super.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        //to prevent the app from close because when backpressed it calls login activty and we finsh it
        //while through the intent
    }
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


clearRightPane();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_two,DetailsInfo).
                    replace(R.id.frame_two_2,RevTraFragment)
                    .commit();

        }
        else
        {

            //calling detailsActivity
            Intent extras=new Intent(MainActivity.this, DetailsActivity.class);
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

    }//method to clear the right pane
    public void clearRightPane(){
        if(getSupportFragmentManager().findFragmentById(R.id.frame_two)!=null)
        {
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.frame_two)).commit();
        }
        if(getSupportFragmentManager().findFragmentById(R.id.frame_two_2)!=null){
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.frame_two_2)).commit();
        }


    }
    public void Logout(){

        Firebase mref=new Firebase(Constants.FIREBASE_URL);
        mref.unauth();
        Intent intent = new Intent(MainActivity.this, Login.class)
                ;
        intent.putExtra("email",Utils.decodeEmail(email));
        intent.putExtra("src","CreateActivity");
        startActivity(intent);
        finish();
    }

}

