package wclem12.com.agameofyou.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.about_story.AboutStoryActivity;
import wclem12.com.agameofyou.nav_drawer.NavDrawerItem;
import wclem12.com.agameofyou.nav_drawer.NavDrawerView;
import wclem12.com.agameofyou.settings.SettingsActivity;
import wclem12.com.agameofyou.story.Story;
import wclem12.com.agameofyou.story.StoryView;
import wclem12.com.agameofyou.util.BaseRecyclerAdapter;
import wclem12.com.agameofyou.util.BookshelfDBHelper;
import wclem12.com.agameofyou.util.Utils;

public class MyBookshelfActivity extends BaseActivity {
    @InjectView(R.id.toolbar) public Toolbar toolbar;
    @InjectView(R.id.navSpinner) public Spinner navSpinner;
    @InjectView(R.id.drawer_layout) public DrawerLayout drawerLayout;
    @InjectView(R.id.drawer_list) public RecyclerView navDrawerRV;
    @InjectView(R.id.my_bookshelf) public RecyclerView myBookshelfRV;

    public static String PACKAGE_NAME;
    public static Context CONTEXT_NAME;
    public static Story story;
    public static SharedPreferences settings;
    public static boolean isList;
    public static boolean taskRoot;
    public static BookshelfDBHelper helper;

    public static final String PREFS_NAME = "settings";
    public static final int SORT_BOOKSHELF = 0;
    public static final int SORT_FAVORITES = 1;
    public static final int SORT_PROGRESS = 2;

    private android.support.v7.app.ActionBarDrawerToggle drawerToggle;
    private ArrayList<Story> myBookshelfList = new ArrayList<>();
    private ArrayList<NavDrawerItem> navDrawerList = new ArrayList<>();
    private String[] navDescriptionStrings = { "Library", "My Bookshelf", "Favorites",
            "In Progress", "Settings", "About"};
    private String[] navIconStrings = { "ic_library", "ic_bookshelf", "ic_favorites",
            "ic_in_progress", "ic_settings", "ic_about"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PACKAGE_NAME = getApplicationContext().getPackageName();
        CONTEXT_NAME = getApplicationContext();

        setContentView(R.layout.activity_ui);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setupDrawer();
        RecyclerView rv = getRootRecyclerView(); if (rv != null) setupQuickReturnToolbar(rv);

//        loadSettings();

//        updateUpButtonState();

        setupSpinner();
        loadMyBookshelf(SORT_BOOKSHELF);
    }

//    use this in the BaseActivity, Override, and use whatever main RV for that view (myLibraryRv
//    for this for example)
    protected RecyclerView getRootRecyclerView() { return null; }

//    protected RecyclerView getRootRecyclerView() { return myFavoritesRV; }

    public void setupQuickReturnToolbar(RecyclerView rv) {
        int actionBarHeight = Utils.getActionBarHeight(this);

        rv.setPadding(
                rv.getPaddingLeft(),
                rv.getPaddingTop() + actionBarHeight,
                rv.getPaddingRight(),
                rv.getPaddingBottom());

        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//              if (!prefs.hidableToolbar()) return;
                float y = Utils.clamp(-toolbar.getHeight(), toolbar.getTranslationY() - dy, 0);
                toolbar.setTranslationY(y);
            }
        });
    }

    /**
     * Load shared prefs
     */
    private void loadSettings() {
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        String currentActivity = settings.getString("Activity", Utils.ACTIVITY_MAIN);

        float textSizeFloat = settings.getFloat("textsize", Utils.TEXTSIZE_MEDIUM);
        String fontStyleStr = settings.getString("fontstyle", Utils.FONTSTYLE_ROBOTO);
        Utils.sTheme = settings.getString("theme", Utils.THEME_LIGHT);
        isList = settings.getBoolean("isList", true);

        Utils.changeTextSize(textSizeFloat);
        Utils.changeFontStyle(fontStyleStr);
        Utils.onActivityCreateSetTheme(this);

        setContentView(R.layout.activity_ui);

//        loadMyLibrary();

        if (!currentActivity.equals(Utils.ACTIVITY_MAIN)) {
            int storyId = settings.getInt("Story", -1);

            if (storyId != -1) {
                if (currentActivity.equals(Utils.ACTIVITY_TITLE)) {
                    //load title page
                    loadStory(storyId, false);

                } else if (currentActivity.equals(Utils.ACTIVITY_STORY)) {
                    int currentPage = settings.getInt("Page", 1);
                    loadStory(storyId, false, currentPage);
                }
            }
        }
    }

    private View.OnClickListener toolbarNavCallback = new View.OnClickListener() {
        @Override public void onClick(View v) {

            if (!drawerToggle.isDrawerIndicatorEnabled()) {
                onBackPressed();
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_bookshelf, menu);

        if (!isList) {
            menu.findItem(R.id.action_layout).setIcon(R.drawable.ic_view_as_list);
        } else {
            menu.findItem(R.id.action_layout).setIcon(R.drawable.ic_view_as_grid);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_layout:
                setViewButton(item);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setViewButton(android.view.MenuItem menuItem) {
        int rows = 1;

        if (isList) {
            rows = 3;
            menuItem.setIcon(R.drawable.ic_view_as_list);
        } else {
            menuItem.setIcon(R.drawable.ic_view_as_grid);
        }

        myBookshelfRV.setLayoutManager(new GridLayoutManager(MyBookshelfActivity.this, rows));

        isList = !isList;

        Utils.SaveSettings(Utils.ACTIVITY_MAIN, -1, -1);
    }

    @Override
    public void onBackPressed() {
        Utils.SaveSettings(Utils.ACTIVITY_MAIN, -1, -1);

        if (drawerLayout.isDrawerOpen(navDrawerRV)) {
            drawerLayout.closeDrawers();
        } else if (isTaskRoot() || taskRoot) {
            new AlertDialog.Builder(this)
                            .setMessage("Are you sure you want to quit?")
                            .setCancelable(false)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
//                                    App.firstStart = true;
                                    finish();
                                }
                            })
                            .setNegativeButton(android.R.string.cancel, null)
                            .show();
        } else {
            super.onBackPressed();
        }

    }

    private void loadStory(int id, boolean readAbout) {
        story = helper.getStory(id);

        Bundle extra = new Bundle();
        extra.putSerializable("Story", story);
        Intent intent;

        if(!readAbout) {
            intent = new Intent(CONTEXT_NAME, TitlePageActivity.class);
        } else {
            intent = new Intent(CONTEXT_NAME, AboutStoryActivity.class);
        }

        intent.putExtra("extra", extra);
        startActivity(intent);
    }

    private void updateUpButtonState() {
        if (!taskRoot) {
            drawerToggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            drawerToggle.setDrawerIndicatorEnabled(true);
            drawerToggle.syncState();
        }
    }

    private void loadStory(int id, boolean longClick, int pageNum) {
        story = helper.getStory(id);

        Bundle extra = new Bundle();
        extra.putSerializable("Story", story);
        Intent intent;

        if(!longClick) {
            intent = new Intent(CONTEXT_NAME, TitlePageActivity.class);
        } else {
            intent = new Intent(MyBookshelfActivity.CONTEXT_NAME, AboutStoryActivity.class);
        }

        intent.putExtra("extra", extra);
        startActivity(intent);
    }

    @Override protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }



    // Navigation Drawer ///////////////////////////////////////////////////////////////////////////
    private void setupDrawer() {
        for(int i = 0; i < navDescriptionStrings.length; i++) {
            NavDrawerItem navDrawerItem = new NavDrawerItem(navDescriptionStrings[i], navIconStrings[i]);
            navDrawerList.add(navDrawerItem);
        }

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        navDrawerRV.setLayoutManager(layoutManager);
        navDrawerRV.setItemAnimator(new DefaultItemAnimator());

        NavDrawerRecyclerAdapter navDrawerRecyclerAdapter = new NavDrawerRecyclerAdapter(navDrawerClick, navDrawerLongClick);
        navDrawerRV.setAdapter(navDrawerRecyclerAdapter);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                drawerView.bringToFront();
                super.onDrawerOpened(drawerView);
            }
        };

        drawerToggle.setToolbarNavigationClickListener(toolbarNavCallback);
        drawerLayout.setDrawerListener(drawerToggle);
    }

    View.OnClickListener navDrawerClick = new View.OnClickListener() {
        @Override public void onClick(View v) {
            navClick(v);
        }
    };

    View.OnLongClickListener navDrawerLongClick = new View.OnLongClickListener() {
        @Override public boolean onLongClick(View v) {
            navClick(v);
            return true;
        }
    };

    private void navClick(View v) {
        NavDrawerView navView = (NavDrawerView) v;
        String description = navView.description.getText().toString().toLowerCase();

        if(description.contains("library")) {
            onLibrary();
        } else if (description.contains("bookshelf")) {
            onMyBookshelf(SORT_BOOKSHELF);
        } else if (description.contains("favorites")) {
            onMyBookshelf(SORT_FAVORITES);
        } else if (description.contains("progress")) {
            onMyBookshelf(SORT_PROGRESS);
        } else if (description.contains("settings")) {
            onSettings();
        } else if (description.contains("about")) {
            onAbout();
        }
    }

    private void onLibrary() {
        //TODO: Goto Library activity
    }

    private void onMyBookshelf(int viewType) {
        if(!(this instanceof  MyBookshelfActivity)) {
            Bundle extra = new Bundle();
            extra.putSerializable("Activity", Utils.ACTIVITY_MAIN);

            finish();

            Intent intent = new Intent(MyBookshelfActivity.CONTEXT_NAME, MyBookshelfActivity.class);
            intent.putExtra("extra", extra);
            startActivity(intent);

            Log.i("....", "Activity re-created");
        }

        navSpinner.setSelection(viewType);
        drawerLayout.closeDrawers();
    }

    private void onSettings() {
        Bundle extra = new Bundle();
        extra.putSerializable("Activity", Utils.ACTIVITY_MAIN);

        Intent intent = new Intent(MyBookshelfActivity.CONTEXT_NAME, SettingsActivity.class);
        intent.putExtra("extra", extra);
        startActivity(intent);
    }

    private void onAbout() {
        SpannableString s = new SpannableString(getResources().getString(R.string.about_app));
        //Linkify for links if needed later
        AlertDialog d = new AlertDialog.Builder(this)
                .setTitle(R.string.about_app_title)
                .setMessage(s)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    class NavDrawerRecyclerAdapter extends BaseRecyclerAdapter<NavDrawerItem> {
        public NavDrawerRecyclerAdapter(View.OnClickListener clickListener, View.OnLongClickListener longClickListener) {
            super(MyBookshelfActivity.this, clickListener, longClickListener);
            this.items = navDrawerList;
        }

        @Override public View newView(ViewGroup container) {
            return inflater.inflate(R.layout.view_nav_drawer, container, false);
        }

        @Override public void bindView(NavDrawerItem item, int position, View view) {
            NavDrawerView navDrawerView = (NavDrawerView) view;
            navDrawerView.bindTo(item);
        }
    }



    // Navigation Spinner //////////////////////////////////////////////////////////////////////////
    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getSupportActionBar().getThemedContext(),
                R.array.nav_spinner, R.layout.spinner_my_bookshelf);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        int startPos = 0;

        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Add functionality to nav spinner
                loadMyBookshelf(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        navSpinner.setAdapter(adapter);
        navSpinner.setSelection(startPos);
        final AdapterView.OnItemSelectedListener finalListener = listener;
        navSpinner.post(new Runnable() {
            @Override
            public void run() {
                navSpinner.setOnItemSelectedListener(finalListener);
            }
        });
    }

    private void loadMyBookshelf(int sort_type){
        helper = new BookshelfDBHelper(MyBookshelfActivity.this);

        switch(sort_type) {
            default:
            case SORT_BOOKSHELF:
                myBookshelfList = helper.getMyBookshelf(SORT_BOOKSHELF);
                break;
            case SORT_FAVORITES:
                myBookshelfList = helper.getMyBookshelf(SORT_FAVORITES);
                break;
            case SORT_PROGRESS:
                myBookshelfList = helper.getMyBookshelf(SORT_PROGRESS);
                break;
        }

        MyLibraryRecyclerAdapter myLibraryAdapter = new MyLibraryRecyclerAdapter(choiceClick, choiceLongClick);
        myBookshelfRV.setAdapter(myLibraryAdapter);

        GridLayoutManager layoutManager = new GridLayoutManager(MyBookshelfActivity.this, 1);
        myBookshelfRV.setLayoutManager(layoutManager);
        myBookshelfRV.setItemAnimator(new DefaultItemAnimator());

        isList = true;

        if (!isList) {
            layoutManager.setSpanCount(3);
        }

        helper.closeDB();
    }


    // Bookshelf RecyclerView //////////////////////////////////////////////////////////////////////
    View.OnClickListener choiceClick = new View.OnClickListener() {
        @Override public void onClick(View v) {
            final StoryView storyView = (StoryView) v;
            Story story = storyView.story;
            loadStory(story.getId(), false);
        }
    };

    View.OnLongClickListener choiceLongClick = new View.OnLongClickListener() {
        @Override public boolean onLongClick(View v) {
            final StoryView storyView = (StoryView) v;
            final Story story = storyView.story;

            final ImageView cover = (ImageView) storyView.findViewById(R.id.coverImage);
            cover.setVisibility(View.GONE);
            final TextView title = (TextView) storyView.findViewById(R.id.title);
            title.setVisibility(View.GONE);
            final TextView author = (TextView) storyView.findViewById(R.id.author);
            author.setVisibility(View.GONE);

            final LinearLayout optionsLayout = (LinearLayout) storyView.findViewById(R.id.options_layout);

            int savedBgColor = 0;
            Drawable background = storyView.getBackground();
            if(background instanceof ColorDrawable) {
                savedBgColor = ((ColorDrawable)background).getColor();
            }

            int bgColor = 0;

            switch(Utils.sTheme) {
                default:
                case Utils.THEME_LIGHT:
                    bgColor = getResources().getColor(R.color.dark_blue);
                    break;
                case Utils.THEME_DARK:
                    bgColor = getResources().getColor(R.color.dark_accent);
                    break;
                case Utils.THEME_SEPIA:
                    bgColor = getResources().getColor(R.color.sepia_med);
                    break;
            }

            optionsLayout.setBackgroundColor(bgColor);

            final View child = LayoutInflater.from(CONTEXT_NAME.getApplicationContext()).inflate(R.layout.view_my_bookshelf_options, null);
            optionsLayout.addView(child);

            ImageView read = (ImageView) child.findViewById(R.id.option_read);
            ImageView about = (ImageView) child.findViewById(R.id.option_about);
            final ImageView favorite = (ImageView) child.findViewById(R.id.option_favorite);
            ImageView delete = (ImageView) child.findViewById(R.id.option_delete);

            //first, if this is favorited, change icon
            if(story.getFavorite()) {
                favorite.setImageResource(R.drawable.ic_favorite_selected);
                helper.closeDB();
            }

            final int finalSavedBgColor = savedBgColor;
            read.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadStory(story.getId(), false);

                    cover.setVisibility(View.VISIBLE);
                    title.setVisibility(View.VISIBLE);
                    author.setVisibility(View.VISIBLE);
                    optionsLayout.removeView(child);
                    optionsLayout.setBackgroundColor(finalSavedBgColor);
                }
            });

            about.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadStory(story.getId(), true);

                    cover.setVisibility(View.VISIBLE);
                    title.setVisibility(View.VISIBLE);
                    author.setVisibility(View.VISIBLE);
                    optionsLayout.removeView(child);
                    optionsLayout.setBackgroundColor(finalSavedBgColor);
                }
            });

            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String toastStr = story.getTitle();

                    if(story.getFavorite()) {
                        toastStr += " removed from favorites.";

                        //remove from myFavoritesList
                        helper = new BookshelfDBHelper(CONTEXT_NAME);
                        story.setFavorite(false);
                        helper.updateFavorite(story);
                        helper.closeDB();

                        favorite.setImageResource(R.drawable.ic_favorites);

                        if(navSpinner.getSelectedItemPosition() == SORT_FAVORITES) {
                            loadMyBookshelf(SORT_FAVORITES);
                        }
                    } else {
                        toastStr += " added to favorites.";

                        //add to myFavoritesList
                        helper = new BookshelfDBHelper(CONTEXT_NAME);
                        story.setFavorite(true);
                        helper.updateFavorite(story);
                        helper.closeDB();

                        favorite.setImageResource(R.drawable.ic_favorite_selected);
                    }

                    Toast.makeText(MyBookshelfActivity.CONTEXT_NAME, toastStr, Toast.LENGTH_SHORT).show();

                    cover.setVisibility(View.VISIBLE);
                    title.setVisibility(View.VISIBLE);
                    author.setVisibility(View.VISIBLE);
                    optionsLayout.removeView(child);
                    optionsLayout.setBackgroundColor(finalSavedBgColor);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog dialog = new AlertDialog.Builder(MyBookshelfActivity.this)
                            .setMessage(Html.fromHtml("Are you sure you want to remove <b>"
                                    + story.getTitle()
                                    + "</b> from your bookshelf?"))
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //delete
                                    helper.deleteStory(story.getId());
                                    helper.closeDB();
                                    Toast.makeText(CONTEXT_NAME, story.getTitle() + " removed from your bookshelf.", Toast.LENGTH_SHORT).show();

                                    //refresh
                                    //there's got to be a better/classier way to to do this but
                                    // navSpinner.setSelection won't trigger since we're on the same
                                    // position
                                    loadMyBookshelf(navSpinner.getSelectedItemPosition());
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //don't delete
                                    Toast.makeText(CONTEXT_NAME, "Cancelled", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();

                    cover.setVisibility(View.VISIBLE);
                    title.setVisibility(View.VISIBLE);
                    author.setVisibility(View.VISIBLE);
                    optionsLayout.removeView(child);
                    optionsLayout.setBackgroundColor(finalSavedBgColor);
                }
            });

            return true;
        }
    };

    class MyLibraryRecyclerAdapter extends BaseRecyclerAdapter<Story> {
        public MyLibraryRecyclerAdapter(View.OnClickListener clickListener, View.OnLongClickListener longClickListener) {
            super(MyBookshelfActivity.this, clickListener, longClickListener);
            this.items = myBookshelfList;
        }

        @Override public View newView(ViewGroup container) {
            return inflater.inflate(R.layout.view_my_bookshelf, container, false);
        }

        @Override public void bindView(Story item, int position, View view) {
            StoryView storyView = (StoryView) view;
            storyView.bindTo(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        loadMyBookshelf(navSpinner.getSelectedItemPosition());
    }
}