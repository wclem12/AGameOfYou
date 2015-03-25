package wclem12.com.agameofyou.activity;

public class LibraryActivity extends BaseActivity {
//    /*
//    @InjectView(R.id.toolbar) public Toolbar toolbar;
//    @InjectView(R.id.drawer_layout) public DrawerLayout drawerLayout;
//    @InjectView(R.id.drawer) public LinearLayout navDrawer;
//    @InjectView(R.id.navLibrary) public LinearLayout navLibrary;
//    @InjectView(R.id.navMyBookshelf) public LinearLayout navMyBookshelf;
//    @InjectView(R.id.navSettings) public LinearLayout navSettings;
//    @InjectView(R.id.navAbout) public LinearLayout navAbout;
//    @InjectView(R.id.navFavorites) public LinearLayout navFavorites;
//    @InjectView(R.id.navInProgress) public LinearLayout navInProgress;
//    @InjectView(R.id.viewpager) public ViewPager mViewPager;
//    @InjectView(R.id.sliding_tabs) public SlidingTabLayout mSlidingTabLayout;
//
//    private android.support.v7.app.ActionBarDrawerToggle drawerToggle;
//    public static String PACKAGE_NAME;
//    public static Context CONTEXT_NAME;
//    public static Story story;
//    public static SharedPreferences settings;
//    public static boolean isList;
//
//    public LibraryDBHelper helper;
//    public static final String PREFS_NAME = "settings";
//
//    public boolean taskRoot;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        PACKAGE_NAME = getApplicationContext().getPackageName();
//        CONTEXT_NAME = getApplicationContext();
//
//        setContentView(R.layout.activity_ui);
//        ButterKnife.inject(this);
//
//        setSupportActionBar(toolbar);
//        mViewPager.setAdapter(new StoryPagerAdapter(getSupportFragmentManager()));
//        mSlidingTabLayout.setDistributeEvenly(true);
//        mSlidingTabLayout.setViewPager(mViewPager);
//
//        setupDrawer();
//        RecyclerView rv = getRootRecyclerView(); if (rv != null) setupQuickReturnToolbar(rv);
//
////        loadSettings();
//
//        helper = new LibraryDBHelper(this);
////        loadMyLibrary();
//
////        updateUpButtonState();
//    }
//
//    private void setupDrawer() {
//        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawerToggle.setToolbarNavigationClickListener(toolbarNavCallback);
//        drawerLayout.setDrawerListener(drawerToggle);
//    }
//
//    //    use this in the BaseActivity, Override, and use whatever main RV for that view (myLibraryRv
////    for this for example)
//    protected RecyclerView getRootRecyclerView() { return null; }
//
////    protected RecyclerView getRootRecyclerView() { return myFavoritesRV; }
//
//    public void setupQuickReturnToolbar(RecyclerView rv) {
//        int actionBarHeight = Utils.getActionBarHeight(this);
//
//        rv.setPadding(
//                rv.getPaddingLeft(),
//                rv.getPaddingTop() + actionBarHeight,
//                rv.getPaddingRight(),
//                rv.getPaddingBottom());
//
//        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
////              if (!prefs.hidableToolbar()) return;
//                float y = Utils.clamp(-toolbar.getHeight(), toolbar.getTranslationY() - dy, 0);
//                toolbar.setTranslationY(y);
//            }
//        });
//    }
//
//    /**
//     * Load shared prefs
//     */
//    private void loadSettings() {
//        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//
//        String currentActivity = settings.getString("Activity", Utils.ACTIVITY_MAIN);
//
//        float textSizeFloat = settings.getFloat("textsize", Utils.TEXTSIZE_MEDIUM);
//        String fontStyleStr = settings.getString("fontstyle", Utils.FONTSTYLE_ROBOTO);
//        Utils.sTheme = settings.getString("theme", Utils.THEME_LIGHT);
//        isList = settings.getBoolean("isList", true);
//
//        Utils.changeTextSize(textSizeFloat);
//        Utils.changeFontStyle(fontStyleStr);
//        Utils.onActivityCreateSetTheme(this);
//
//        setContentView(R.layout.activity_ui);
//
////        loadMyLibrary();
//
//        if (!currentActivity.equals(Utils.ACTIVITY_MAIN)) {
//            int storyId = settings.getInt("Story", -1);
//
//            if (storyId != -1) {
//                if (currentActivity.equals(Utils.ACTIVITY_TITLE)) {
//                    //load title page
//                    loadStory(storyId, false);
//
//                } else if (currentActivity.equals(Utils.ACTIVITY_STORY)) {
//                    int currentPage = settings.getInt("Page", 1);
//                    loadStory(storyId, false, currentPage);
//                }
//            }
//        }
//    }
//
//    @OnClick(R.id.navLibrary) void onLibrary() {
//        //TODO: Goto Library activity
//    }
//
//    @OnClick(R.id.navMyBookshelf) void onMyBookshelf() {
//        Bundle extra = new Bundle();
//        extra.putSerializable("Activity", Utils.ACTIVITY_MAIN);
//
//        finish();
//
//        Intent intent = new Intent(MyBookshelfActivity.CONTEXT_NAME, MyBookshelfActivity.class);
//        intent.putExtra("extra", extra);
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.navSettings) void onSettings() {
//        Bundle extra = new Bundle();
//        extra.putSerializable("Activity", Utils.ACTIVITY_MAIN);
//
//        Intent intent = new Intent(MyBookshelfActivity.CONTEXT_NAME, SettingsActivity.class);
//        intent.putExtra("extra", extra);
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.navAbout) void onAbout() {
//        SpannableString s = new SpannableString(getResources().getString(R.string.about_app));
//        //Linkify for links if needed later
//        AlertDialog d = new AlertDialog.Builder(this)
//                .setTitle(R.string.about_app_title)
//                .setMessage(s)
//                .setPositiveButton(android.R.string.ok, null)
//                .show();
//    }
//
//    @OnClick(R.id.navFavorites) void onFavorites() {
//        //TODO: Favorites drop down
//    }
//
//    @OnClick(R.id.navInProgress) void onInProgress() {
//        //TODO: InProgress drop down
//    }
//
//    private View.OnClickListener toolbarNavCallback = new View.OnClickListener() {
//        @Override public void onClick(View v) {
//
//            if (!drawerToggle.isDrawerIndicatorEnabled()) {
//                onBackPressed();
//            }
//        }
//    };
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_my_bookshelf, menu);
//
//        if (!isList) {
//            menu.findItem(R.id.action_layout).setIcon(R.drawable.ic_view_as_list);
//        } else {
//            menu.findItem(R.id.action_layout).setIcon(R.drawable.ic_view_as_grid);
//        }
//
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(android.view.MenuItem item) {
//        // Pass the event to ActionBarDrawerToggle, if it returns
//        // true, then it has handled the app icon touch event
//        if (drawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//
//        switch (item.getItemId()) {
//            case R.id.action_layout:
//                setViewButton(item);
//                return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void setViewButton(android.view.MenuItem menuItem) {
//        int rows = 1;
//
//        if (isList) {
//            rows = 3;
//            menuItem.setIcon(R.drawable.ic_view_as_list);
//        } else {
//            menuItem.setIcon(R.drawable.ic_view_as_grid);
//        }
//
////        myFavoritesRV.setLayoutManager(new GridLayoutManager(CONTEXT_NAME, rows));
//
//        isList = !isList;
//
//        Utils.SaveSettings(Utils.ACTIVITY_MAIN, -1, -1);
//    }
//
//    @Override
//    public void onBackPressed() {
//        Utils.SaveSettings(Utils.ACTIVITY_MAIN, -1, -1);
//
//        if (drawerLayout.isDrawerOpen(navDrawer)) {
//            drawerLayout.closeDrawers();
//        } else if (isTaskRoot() || taskRoot) {
//            new AlertDialog.Builder(this)
//                    .setMessage("Are you sure you want to quit?")
//                    .setCancelable(false)
//                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
////                                    App.firstStart = true;
//                            finish();
//                        }
//                    })
//                    .setNegativeButton(android.R.string.cancel, null)
//                    .show();
//        } else {
//            super.onBackPressed();
//        }
//
//    }
//
//
//    View.OnClickListener choiceClick = new View.OnClickListener() {
//        @Override public void onClick(View v) {
//            final StoryView storyView = (StoryView) v;
//            Story story = storyView.story;
//            loadStory(story.getId(), false);
//        }
//    };
//
//    View.OnLongClickListener choiceLongClick = new View.OnLongClickListener() {
//        @Override public boolean onLongClick(View v) {
//            final StoryView storyView = (StoryView) v;
//            final Story story = storyView.story;
//
//            final ImageView cover = (ImageView) storyView.findViewById(R.id.coverImage);
//            cover.setVisibility(View.GONE);
//            final TextView title = (TextView) storyView.findViewById(R.id.title);
//            title.setVisibility(View.GONE);
//            final TextView author = (TextView) storyView.findViewById(R.id.author);
//            author.setVisibility(View.GONE);
//
//            final LinearLayout optionsLayout = (LinearLayout) storyView.findViewById(R.id.options_layout);
//
//            int savedBgColor = 0;
//            Drawable background = storyView.getBackground();
//            if(background instanceof ColorDrawable) {
//                savedBgColor = ((ColorDrawable)background).getColor();
//            }
//
//            int bgColor = 0;
//
//            switch(Utils.sTheme) {
//                default:
//                case Utils.THEME_LIGHT:
//                    bgColor = getResources().getColor(R.color.dark_blue);
//                    break;
//                case Utils.THEME_DARK:
//                    bgColor = getResources().getColor(R.color.dark_accent);
//                    break;
//                case Utils.THEME_SEPIA:
//                    bgColor = getResources().getColor(R.color.sepia_med);
//                    break;
//            }
//
//            optionsLayout.setBackgroundColor(bgColor);
//
//            final View child = LayoutInflater.from(getApplicationContext()).inflate(R.layout.view_my_bookshelf_options, null);
//            optionsLayout.addView(child);
//
//            ImageView read = (ImageView) child.findViewById(R.id.option_read);
//            ImageView about = (ImageView) child.findViewById(R.id.option_about);
//            final ImageView favorite = (ImageView) child.findViewById(R.id.option_favorite);
//            ImageView delete = (ImageView) child.findViewById(R.id.option_delete);
//
//            //first, if this is favorited, change icon
//            if(helper.isFavorited(story.getId())) {
//                favorite.setImageResource(R.drawable.ic_favorite_selected);
//                helper.closeDB();
//            }
//
//            final int finalSavedBgColor = savedBgColor;
//            read.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    Toast.makeText(MyBookshelfActivity.CONTEXT_NAME, "Read", Toast.LENGTH_SHORT).show();
//                    loadStory(story.getId(), false);
//
//
//                    cover.setVisibility(View.VISIBLE);
//                    title.setVisibility(View.VISIBLE);
//                    author.setVisibility(View.VISIBLE);
//                    optionsLayout.removeView(child);
//                    optionsLayout.setBackgroundColor(finalSavedBgColor);
//                }
//            });
//
//            about.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    Toast.makeText(MyBookshelfActivity.CONTEXT_NAME, "About", Toast.LENGTH_SHORT).show();
//                    loadStory(story.getId(), true);
//
//
//                    cover.setVisibility(View.VISIBLE);
//                    title.setVisibility(View.VISIBLE);
//                    author.setVisibility(View.VISIBLE);
//                    optionsLayout.removeView(child);
//                    optionsLayout.setBackgroundColor(finalSavedBgColor);
//                }
//            });
//
//            favorite.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(helper.isFavorited(story.getId())) {
//                        //remove from myFavoritesList
//                        helper = new LibraryDBHelper(CONTEXT_NAME);
//                        helper.removeFavorite(story.getId());
//                        helper.closeDB();
//
//                        favorite.setImageResource(R.drawable.ic_favorites);
//                    } else {
//                        //add to myFavoritesList
//                        helper = new LibraryDBHelper(CONTEXT_NAME);
//                        helper.updateFavorite(story.getId());
//                        helper.closeDB();
//
//                        favorite.setImageResource(R.drawable.ic_favorite_selected);
//                    }
//
//                    cover.setVisibility(View.VISIBLE);
//                    title.setVisibility(View.VISIBLE);
//                    author.setVisibility(View.VISIBLE);
//                    optionsLayout.removeView(child);
//                    optionsLayout.setBackgroundColor(finalSavedBgColor);
//                }
//            });
//
//            delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AlertDialog dialog = new AlertDialog.Builder(LibraryActivity.this)
//                            .setMessage(Html.fromHtml("Are you sure you want to remove <b>"
//                                    + story.getTitle()
//                                    + "</b> from your bookshelf?"))
//                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //delete
//                                    Toast.makeText(LibraryActivity.this, "Story Removed", Toast.LENGTH_SHORT).show();
//                                }
//                            })
//                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //don't delete
//                                    Toast.makeText(LibraryActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
//                                }
//                            })
//                            .show();
//
//                    cover.setVisibility(View.VISIBLE);
//                    title.setVisibility(View.VISIBLE);
//                    author.setVisibility(View.VISIBLE);
//                    optionsLayout.removeView(child);
//                    optionsLayout.setBackgroundColor(finalSavedBgColor);
//                }
//            });
//
//            return true;
//        }
//    };
//
//    public void LoadStory(int storyId) {
//        loadStory(storyId, false);
//    }
//
//    private void loadStory(int id, boolean readAbout) {
//        story = helper.getStory(id);
//
//        Bundle extra = new Bundle();
//        extra.putSerializable("Story", story);
//        Intent intent;
//
//        if(!readAbout) {
//            intent = new Intent(CONTEXT_NAME, TitlePageActivity.class);
//        } else {
//            intent = new Intent(CONTEXT_NAME, AboutStoryActivity.class);
//        }
//
//        intent.putExtra("extra", extra);
//        startActivity(intent);
//    }
//
//    private void updateUpButtonState() {
//        if (!taskRoot) {
//            drawerToggle.setDrawerIndicatorEnabled(false);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        } else {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//            drawerToggle.setDrawerIndicatorEnabled(true);
//            drawerToggle.syncState();
//        }
//    }
//
//    private void loadStory(int id, boolean longClick, int pageNum) {
//        /*
//        story = helper.getStory(id);
//
//        Bundle extra = new Bundle();
//        extra.putSerializable("Story", story);
//        Intent intent;
//
//        if(!longClick) {
//            intent = new Intent(CONTEXT_NAME, TitlePageActivity.class);
//        } else {
//            intent = new Intent(MyLibraryActivity.CONTEXT_NAME, AboutStoryActivity.class);
//        }
//
//        intent.putExtra("extra", extra);
//        startActivity(intent);
//        */
//    }
//
//    /*
//    class NavDrawerRecyclerAdapter extends BaseRecyclerAdapter<NavDrawerItem> {
//        public NavDrawerRecyclerAdapter(View.OnClickListener clickListener, View.OnLongClickListener longClickListener) {
//            super(MyBookshelfActivity.this, clickListener, longClickListener);
//            this.items = navDrawerList;
//        }
//
//        @Override public View newView(ViewGroup container) {
//            return inflater.inflate(R.layout.nav_drawer, container, false);
//        }
//
//        @Override public void bindView(NavDrawerItem item, int position, View view) {
//            NavDrawerView navDrawerView = (NavDrawerView) view;
//            navDrawerView.bindTo(item);
//        }
//    }
//    */
//
//    @Override protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        drawerToggle.syncState();
//    }
//
//    @Override public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        drawerToggle.onConfigurationChanged(newConfig);
//    }
//
//    class StoryPagerAdapter extends FragmentPagerAdapter {
//
//        int numOfPages = 3;
//        String[] tabs = { "My Library", "Favorites", "In Progress" };
//        private List<Fragment> fragments;
//
//        public StoryPagerAdapter(FragmentManager fragmentManager) {
//            super(fragmentManager);
//            this.fragments = new ArrayList<>();
//            fragments.add(new MyBookshelfFragment());
//            fragments.add(new MyFavoritesFragment());
//            fragments.add(new InProgressFragment());
//        }
//
//        // Return total number of pages
//        @Override
//        public int getCount() {
//            return numOfPages;
//        }
//
//        // Return the page title for the top indicator
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return tabs[position];
//        }
//
//        // Returns the fragment to display for that page
//        @Override
//        public Fragment getItem(int position) {
//            return fragments.get(position);
//        }
//
//        @Override
//        public int getItemPosition(Object object) {
//            return POSITION_NONE;
//        }
//    }
//
}
