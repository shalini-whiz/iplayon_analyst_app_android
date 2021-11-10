package in .iplayon.analyst.activity;


import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import in .iplayon.analyst.R;
import in .iplayon.analyst.fragment.AnalyzeAnalytics;
import in .iplayon.analyst.fragment.BlankFragment;
import in .iplayon.analyst.fragment.ProfileSettingsFragment;
import in .iplayon.analyst.fragment.RecordAnalytics;
import in .iplayon.analyst.fragment.ReviewAnalytics;
import in .iplayon.analyst.util.CircleForm;
import in .iplayon.analyst.util.Constants;
import in .iplayon.analyst.util.ExecuteURLTask;
import in .iplayon.analyst.util.SessionManager;
import in .iplayon.analyst.util.Util;


public class HomeActivity extends AppCompatActivity implements
        BlankFragment.OnFragmentInteractionListener,
        ProfileSettingsFragment.OnFragmentInteractionListener,
        ReviewAnalytics.OnFragmentInteractionListener,
        RecordAnalytics.OnFragmentInteractionListener,
        AnalyzeAnalytics.OnFragmentInteractionListener {

    public SessionManager mSession;
    public Util mUtil;
    private NavigationView navigationView;
    private static DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile, menu_close;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    ImageView actionBarHome;
    TextView actionBarTitle;
    public static ActionBarDrawerToggle actionBarDrawerToggle;
    // urls to load navigation header background image
    // and profile image

    // index to identify current nav menu item
    public static int navItemIndex = 1;
    public static int backgroundNavItemIndex = 1;

    // tags used to attach the fragments
    private static final String TAG_PROFILE = "My Profile";
    private static final String TAG_LOGOUT = "Logout";
    private static final String TAG_ANALYTICS = "Analytics";
    private static final String TAG_REVIEW_ANALYTICS = "Review Analytics";
    private static final String TAG_ANALYZE_ANALYTICS = "Analyze Analytics";

    public static String CURRENT_TAG = TAG_ANALYTICS;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    AlertDialog mAlertDialog;

    ProgressBar progressBar;
    LinearLayout progressBarLayout;
    ExecuteURLTask asyncTask;
    View customToastLayout;

    private BroadcastReceiver receiver;
    Boolean resumeStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_home1);
        mSession = new SessionManager(getApplicationContext());
        mUtil = new Util(getApplicationContext());
        mUtil.setStatusBar(HomeActivity.this);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false); // disable the button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false); // remove the left caret
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setElevation(0);
        }

        toolbar.setPadding(0, 0, 0, 0);
        toolbar.setContentInsetsAbsolute(0, 0);

        asyncTask = new ExecuteURLTask(getApplicationContext(), HomeActivity.this, false, "");

        customToastLayout = getLayoutInflater().inflate(R.layout.custom_toast, null);

        progressBar = new ProgressBar(HomeActivity.this);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.progressBarColor), android.graphics.PorterDuff.Mode.MULTIPLY);
        progressBarLayout = new LinearLayout(HomeActivity.this);
        progressBarLayout.setGravity(Gravity.CENTER);
        addContentView(progressBarLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        progressBarLayout.removeAllViews();
        progressBarLayout.addView(progressBar);
        progressBar.setVisibility(View.GONE);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        actionBarHome = (ImageView) findViewById(R.id.actionBarHome);
        actionBarTitle = (TextView) findViewById(R.id.actionBarTitle);
        actionBarHome.setVisibility(View.VISIBLE);


        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);
        menu_close = (ImageView) navHeader.findViewById(R.id.menu_close);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);


        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false); // disable the button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false); // remove the left caret
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

        if (savedInstanceState == null) {
            navItemIndex = 0;
            backgroundNavItemIndex = 0;
            CURRENT_TAG = TAG_ANALYTICS;
            loadHomeFragment();
        }
    }



    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {
        // name, website
        txtName.setText(mSession.ypGetUserName());

        txtWebsite.setText("www.iplayon.in");
        // Loading profile image
        Glide.with(this).load(R.drawable.app_logo)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleForm(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);


    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {

        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            //toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        //toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();

        drawer.post(new Runnable() {
            @Override
            public void run() {
                if (actionBarDrawerToggle != null)
                    actionBarDrawerToggle.syncState();
            }
        });

       /*
       getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
           @Override
           public void onBackStackChanged() {
               int stackHeight = getSupportFragmentManager().getBackStackEntryCount();
               if (stackHeight > 0) { // if we have something on the stack (doesn't include the current shown fragment)
                   getSupportActionBar().setHomeButtonEnabled(true);
                   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
               } else {
                   getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                   getSupportActionBar().setHomeButtonEnabled(false);
               }
           }

       });*/

        menu_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawers();
                    navItemIndex = backgroundNavItemIndex;

                    Runnable mPendingRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Fragment fragment = getHomeFragment();
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                                    android.R.anim.fade_out);
                            fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                            fragmentTransaction.commitAllowingStateLoss();
                        }
                    };

                    // If mPendingRunnable is not null, then add to the message queue
                    if (mPendingRunnable != null) {
                        mHandler.post(mPendingRunnable);
                    }

                    getSupportActionBar().setTitle(activityTitles[navItemIndex]);
                    actionBarTitle.setText(activityTitles[navItemIndex]);

                    return;
                }

            }
        });

        actionBarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawers();;
                } else {
                    drawer.openDrawer(Gravity.START);
                }

            }
        });
    }


    //on click listerner - each menu item
    private Fragment getHomeFragment() {
        switch (navItemIndex) {

            case 0:
                if (asyncTask != null)
                    asyncTask.cancel(true);
                if (mUtil.isOnline()) {
                    Bundle bundle = new Bundle();
                    RecordAnalytics reportFragment = new RecordAnalytics();
                    reportFragment.setArguments(bundle);
                    return reportFragment;
                } else {
                    String textMessage = getResources().getString(R.string.noInternet);
                    mUtil.toastMessage(textMessage, getApplicationContext());
                    return new BlankFragment();
                }

            case 1:
                if (asyncTask != null)
                    asyncTask.cancel(true);
                if (mUtil.isOnline()) {
                    Bundle bundle = new Bundle();
                    ReviewAnalytics reportFragment = new ReviewAnalytics();
                    reportFragment.setArguments(bundle);
                    return reportFragment;
                } else {
                    String textMessage = getResources().getString(R.string.noInternet);
                    mUtil.toastMessage(textMessage, getApplicationContext());
                    return new BlankFragment();
                }


            case 2:
                if (asyncTask != null)
                    asyncTask.cancel(true);
                if (mUtil.isOnline()) {
                    Bundle bundle = new Bundle();
                    AnalyzeAnalytics reportFragment = new AnalyzeAnalytics();
                    reportFragment.setArguments(bundle);
                    return reportFragment;
                } else {
                    String textMessage = getResources().getString(R.string.noInternet);
                    mUtil.toastMessage(textMessage, getApplicationContext());
                    return new BlankFragment();
                }

            case 3:
                if (asyncTask != null)
                    asyncTask.cancel(true);

                if (mUtil.isOnline()) {
                    Bundle bundle = new Bundle();
                    ProfileSettingsFragment reportFragment = new ProfileSettingsFragment();
                    bundle.putString("key1","value1");
                    bundle.putString("key2","value2");
                    reportFragment.setArguments(bundle);
                    return reportFragment;
                } else {
                    String textMessage = getResources().getString(R.string.noInternet);
                    mUtil.toastMessage(textMessage, getApplicationContext());
                    return new BlankFragment();
                }


            default:
                return new BlankFragment();
        }
    }


    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
        actionBarTitle.setText(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;

                    case R.id.nav_analytics:
                        navItemIndex = 0;
                        backgroundNavItemIndex = 0;
                        CURRENT_TAG = TAG_ANALYTICS;
                        //asyncTask.onCancelled();
           /* asyncTask.cancel(true);
            if(mSession.ypGetUserSport().equalsIgnoreCase("QvHXDftiwsnc8gyfJ"))
            {
                if (mUtil.isOnline()) {
                    String report = "Analytics";
                    String urlResponse = "";
                    String params = "getStrokesData?caller=" + Constants.caller + "&apiKey=" + Constants.apiKey + "&userId=" + mSession.ypGetUserID();
                    String requestType = "GET";
                    String methodType = "Analytics";
                    try {
                        asyncTask = new ExecuteURLTask(getApplicationContext(), methodType);
                        String result = asyncTask.execute(params, "", requestType).get();
                        if (result != null) {
                            JSONParser jsonParser = new JSONParser();
                            JSONObject jsonObj = null;
                            try {
                                jsonObj = (JSONObject) jsonParser.parse(result);
                                if (jsonObj.containsKey("message"))
                                {
                                    if (jsonObj.get("message").toString().equalsIgnoreCase("Access forbidden")) {
                                        //Toast.makeText(getApplicationContext(),jsonObj.get("message").toString() , Toast.LENGTH_SHORT).show();
                                        mUtil.toastMessage(jsonObj.get("message").toString(), getApplicationContext());

                                    }
                                } else {
                                    Intent lIntent = new Intent(HomeActivity.this, SequenceActivity.class);
                                    lIntent.putExtra("report", methodType);
                                    //lIntent.putExtra("response",result);
                                    SequenceActivity.response = result;
                                    startActivity(lIntent);
                                    return true;

                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                                //Toast.makeText(getApplicationContext(), "Could not share sequence",Toast.LENGTH_SHORT).show();
                                mUtil.toastMessage("Could not share sequence", getApplicationContext());


                            }
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (CancellationException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Toast.makeText(getApplicationContext(),"No Internet", Toast.LENGTH_SHORT).show();
                    String textMessage = getResources().getString(R.string.noInternet);
                    mUtil.toastMessage(textMessage, getApplicationContext());
                }
            }
            else {
                mUtil.toastMessage("Analytics restricted to Table Tennis",getApplicationContext());
            }*/
                        break;


                    case R.id.nav_review_analytics:
                        navItemIndex = 1;
                        backgroundNavItemIndex = 1;
                        CURRENT_TAG = TAG_REVIEW_ANALYTICS;
                        break;

                    case R.id.nav_analyze_analytics:
                        navItemIndex = 2;
                        backgroundNavItemIndex = 2;
                        CURRENT_TAG = TAG_ANALYZE_ANALYTICS;
                        break;

                    case R.id.nav_profile:
                        navItemIndex = 3;
                        backgroundNavItemIndex = 3;
                        CURRENT_TAG = TAG_PROFILE;
                        break;

                    case R.id.nav_logout:
                        //navItemIndex = 2;
                        // backgroundNavItemIndex = 2;


                        AlertDialog.Builder profileDialog = new AlertDialog.Builder(HomeActivity.this, R.style.AlertDialogTheme);
                        LayoutInflater inflater = getLayoutInflater();
                        profileDialog.setMessage("Are you sure you want to logout ?");
                        profileDialog.setCancelable(false);

                        profileDialog.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CURRENT_TAG = TAG_LOGOUT;
                                mSession.clearSession();
                                mUtil.ypIntentGenericView("MainActivity");
                                drawer.closeDrawers();

                            }
                        });
                        profileDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mAlertDialog.cancel();
                            }
                        });

                        mAlertDialog = profileDialog.create();

                        mAlertDialog.show();


                        return true;


                }

                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);

            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
    }



    public void onBackPressed() {

        mUtil.ypOnBackPressed();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        if (navItemIndex == 0) {
            //getMenuInflater().inflate(R.menu.menu, menu);
        }

        // when fragment is notifications, load the menu created for notifications
        //if (navItemIndex == 3) {
        // getMenuInflater().inflate(R.menu.notifications, menu);
        // }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false); // disable the button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false); // remove the left caret
            getSupportActionBar().setDisplayShowHomeEnabled(false);

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    // show or hide the fab
    private void toggleFab() {
        if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public static void syncDrawerToggle() {
        actionBarDrawerToggle.syncState();


    };
}