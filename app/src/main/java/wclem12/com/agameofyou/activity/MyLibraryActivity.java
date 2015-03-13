package wclem12.com.agameofyou.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.story.Story;
import wclem12.com.agameofyou.story.StoryView;
import wclem12.com.agameofyou.util.BaseAdapter;
import wclem12.com.agameofyou.util.LibraryDBHelper;
import wclem12.com.agameofyou.util.Utils;

public class MyLibraryActivity extends BaseActivity {
    public static String PACKAGE_NAME;
    public static Context CONTEXT_NAME;
    public static Story story;
    public static SharedPreferences settings;
    public static boolean isList;

    public LibraryDBHelper helper;

    private RecyclerView myLibraryView;
    private ArrayList<Story> myLibraryList;

    public static final String PREFS_NAME = "settings";

    private static final String MANIFEST_FILENAME = "story_manifest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PACKAGE_NAME = getApplicationContext().getPackageName();
        CONTEXT_NAME = getApplicationContext();

        setContentView(R.layout.activity_mylibrary);

//        loadSettings();
        loadDatabase();
    }


    private void loadDatabase() {
        helper = new LibraryDBHelper(this);

        myLibraryList = helper.getMyLibraryList();

        GridLayoutManager layoutManager = new GridLayoutManager(CONTEXT_NAME, 1);
        myLibraryView = (RecyclerView) findViewById(R.id.main_menu_list);
        myLibraryView.setLayoutManager(layoutManager);

        MyLibraryAdapter myLibraryAdapter = new MyLibraryAdapter(choiceClick, choiceLongClick);
        myLibraryView.setAdapter(myLibraryAdapter);

        isList = true;

        if (!isList) {
            layoutManager.setSpanCount(3);
        }

        helper.closeDB();
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

        setContentView(R.layout.activity_mylibrary);

        loadDatabase();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mylibrary, menu);

        if (!isList) {
            menu.findItem(R.id.action_layout).setIcon(R.drawable.ic_view_as_list);
        } else {
            menu.findItem(R.id.action_layout).setIcon(R.drawable.ic_view_as_grid);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.action_settings:
                Bundle extra = new Bundle();
                extra.putSerializable("Activity", Utils.ACTIVITY_MAIN);

                intent = new Intent(MyLibraryActivity.CONTEXT_NAME, SettingsActivity.class);
                intent.putExtra("extra", extra);
                startActivity(intent);
                return true;
            case R.id.action_layout:
                setViewButton(item);
                return true;
            case R.id.action_about:
                intent = new Intent(MyLibraryActivity.CONTEXT_NAME, AboutActivity.class);
                startActivity(intent);
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

        myLibraryView.setLayoutManager(new GridLayoutManager(CONTEXT_NAME, rows));

        isList = !isList;

        Utils.SaveSettings(Utils.ACTIVITY_MAIN, -1, -1);
    }

    @Override
    public void onBackPressed() {
        Utils.SaveSettings(Utils.ACTIVITY_MAIN, -1, -1);

        super.onBackPressed();
    }

    View.OnClickListener choiceClick = new View.OnClickListener() {
        @Override public void onClick(View v) {
            click(v, false);
        }
    };

    View.OnLongClickListener choiceLongClick = new View.OnLongClickListener() {
        @Override public boolean onLongClick(View v) {
            click(v, true);
            return true;
        }
    };

    private void click(View v, Boolean longClick) {
        final StoryView storyView = (StoryView) v;
        Story story = storyView.story;

        loadStory(story.getId(), longClick);
    }

    private void loadStory(int id, boolean longClick) {
        story = helper.getStory(id);

        Bundle extra = new Bundle();
        extra.putSerializable("Story", story);
        Intent intent;

        if(!longClick) {
            intent = new Intent(CONTEXT_NAME, TitlePageActivity.class);
        } else {
            intent = new Intent(MyLibraryActivity.CONTEXT_NAME, AboutStoryActivity.class);
        }

        intent.putExtra("extra", extra);
        startActivity(intent);
    }

    private void loadStory(int id, boolean longClick, int pageNum) {
        /*
        story = helper.getStory(id);

        Bundle extra = new Bundle();
        extra.putSerializable("Story", story);
        Intent intent;

        if(!longClick) {
            intent = new Intent(CONTEXT_NAME, TitlePageActivity.class);
        } else {
            intent = new Intent(MyLibraryActivity.CONTEXT_NAME, AboutStoryActivity.class);
        }

        intent.putExtra("extra", extra);
        startActivity(intent);
        */
    }

    class MyLibraryAdapter extends BaseAdapter<Story> {

        public MyLibraryAdapter(View.OnClickListener clickListener, View.OnLongClickListener longClickListener) {
            super(MyLibraryActivity.this, clickListener, longClickListener);
            this.items = myLibraryList;
        }

        @Override public View newView(ViewGroup container) {
            return inflater.inflate(R.layout.view_mylibrary, container, false);
        }

        @Override public void bindView(Story item, int position, View view) {
            StoryView storyView = (StoryView) view;
            storyView.bindTo(item);
        }
    }
}