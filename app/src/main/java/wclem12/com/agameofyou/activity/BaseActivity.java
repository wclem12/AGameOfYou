package wclem12.com.agameofyou.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import wclem12.com.agameofyou.util.Utils;

public abstract class BaseActivity extends ActionBarActivity {
//    @InjectView(R.id.toolbar) public Toolbar toolbar;
//    @InjectView(R.id.drawer_layout) public DrawerLayout drawerLayout;
//    @InjectView(R.id.drawer) public LinearLayout navDrawer;

//    public ArrayList<Favorite> myFavoritesList = new ArrayList<>();
//    public ArrayList<InProgress> inProgressList;

//    private android.support.v7.app.ActionBarDrawerToggle drawerToggle;

    /*
    protected View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResource(), container, false);
    }
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
/*

//        setContentView(R.layout.activity_ui);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        setupDrawer();
        RecyclerView rv = getRootRecyclerView(); if (rv != null) setupQuickReturnToolbar(rv);
        */
    }

    /*
    abstract protected int getLayoutResource();

    private void setupDrawer() {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerToggle.setToolbarNavigationClickListener(toolbarNavCallback);
        drawerLayout.setDrawerListener(drawerToggle);
    }

    private View.OnClickListener toolbarNavCallback = new View.OnClickListener() {
        @Override public void onClick(View v) {

            if (!drawerToggle.isDrawerIndicatorEnabled()) {
                onBackPressed();
            }
        }
    };


    protected RecyclerView getRootRecyclerView() { return null; }

    // http://www.reddit.com/r/androiddev/comments/2sqcth/play_stores_autohiding_toolbar_hows_it_implemented/
    public void setupQuickReturnToolbar(RecyclerView rv) {
        int actionBarHeight = Utils.getActionBarHeight(this);
        rv.setPadding(
                rv.getPaddingLeft(),
                rv.getPaddingTop() + actionBarHeight,
                rv.getPaddingRight(),
                rv.getPaddingBottom());
        if (Build.VERSION.SDK_INT >= 11) {
            rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                    if (!prefs.hidableToolbar()) return;
                    float y = Utils.clamp(-toolbar.getHeight(), toolbar.getTranslationY() - dy, 0);
                    toolbar.setTranslationY(y);
                }
            });
        } else {
            rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                    if (!prefs.hidableToolbar()) return;
//                    float y = Utils.clamp(-toolbar.getHeight(), ViewHelper.getTranslationY(toolbar) - dy, 0);
//                    ViewHelper.setTranslationY(toolbar, y);
                }
            });
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class FavoritesRecyclerAdapter extends BaseRecyclerAdapter<Favorite> {
        public FavoritesRecyclerAdapter(View.OnClickListener clickListener, View.OnLongClickListener longClickListener) {
            super(BaseActivity.this, clickListener, longClickListener);
            this.items = myFavoritesList;
        }

        @Override public View newView(ViewGroup container) {
            return inflater.inflate(R.layout.view_nav_drawer, container, false);
        }

        @Override public void bindView(Favorite item, int position, View view) {
            FavoriteView favoriteView = (FavoriteView) view;
            favoriteView.bindTo(item);
        }
    }

    @OnClick(R.id.navLibrary) void onLibrary() {
        //TODO: Goto Library activity
    }

    @OnClick(R.id.navMyBookshelf) void onMyBookshelf() {
        //TODO: Goto My Bookshelf activity
    }

    @OnClick(R.id.navSettings) void onSettings() {
        //TODO: Goto Settings activity

    }

    @OnClick(R.id.navAbout) void onAbout() {
        SpannableString s = new SpannableString(getResources().getString(R.string.about_app));
        //Linkify for links if needed later
        AlertDialog d = new AlertDialog.Builder(this)
                .setTitle(R.string.about_app_title)
                .setMessage(s)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    @OnClick(R.id.navFavorites) void onFavorites() {
        //TODO: Favorites drop down
    }

    @OnClick(R.id.navInProgress) void onInProgress() {
        //TODO: InProgress drop down
    }

    private void setupSubListsInDrawer() {
//        myFavoritesList = helper.getMyFavorites();

        if(myFavoritesList.size() < 1) {
            //hide myFavoritesList
        }

        /*
        inProgressList = helper.getInProgress();

        if(inProgressList.size() < 1) {
            //hide in progress
        }
        helper.closeDB();
        */
        /*
        favoriteBoardsAdapter = new FavoritesAdapter(this, new ArrayList<>(persistentData.getMyFavorites()));
        favoriteBoardsView.setAdapter(favoriteBoardsAdapter);
        favoriteBoardsAdapter.notifyDataSetChanged();
        persistentData.addFavoritesChangedCallback(favoritesChangedCallback);
        favoriteBoardsView.setOnItemClickListener(favoriteCallback);
        setVisibility(favoriteBoardsHeader, persistentData.getMyFavorites().size() != 0);
        *//*
    }

    private void setupInProgressInDrawer() {

    }
    */
}