package wclem12.com.agameofyou.activity;

import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.activity.fragment.SettingsFragment;
import wclem12.com.agameofyou.story.Story;

public class TitlePageActivity extends BaseActivity {
    private Story story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //pass this onto StoryPageActivity
        Intent intent = getIntent();
        Bundle extra = getIntent().getBundleExtra("extra");
        story = (Story) extra.getSerializable("Story");

        loadTitlePage();

//        saveSettings();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_title_page, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                Bundle bundle = new Bundle();
                bundle.putString("activity", "title");

                SettingsFragment settingsFragment = new SettingsFragment();
                settingsFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(android.R.id.content, settingsFragment);
                fragmentTransaction.addToBackStack("settings");
                fragmentTransaction.commit();
                return true;
            case R.id.action_main_menu:
                finish();

                //Make main_menu be the current activity
                SharedPreferences settings = getSharedPreferences(MainMenuActivity.PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("activity", "main_menu");

                // Commit edits
                editor.commit();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadTitlePage() {
        setContentView(R.layout.title_page);
        setTitle(story.getTitle());

        //TODO: Add tags and unique id R.id's
        LinearLayout layout = (LinearLayout) findViewById(R.id.title_layout);
        String imageFile = story.getCover();
        layout.setBackgroundResource(getResources().getIdentifier(imageFile, "drawable", getPackageName()));

        TextView title = (TextView) findViewById(R.id.title_page_title);
        title.setText(story.getTitle());

        TextView author = (TextView) findViewById(R.id.title_page_author);
        author.setText("By: " + story.getAuthor());

        //establish background color of rectangle
        setRectangleBackground();

        /*
        TextView create_date = (TextView) findViewById(R.id.title_page_create_date);
        create_date.setText("Created: " + story.getCreateDate());

        TextView last_edit_date = (TextView) findViewById(R.id.title_page_last_edit_date);
        last_edit_date.setText("Last Updated: " + story.getLastEditDate());

        TextView creator_username = (TextView) findViewById(R.id.title_page_creator_username);
        creator_username.setText("Created By: " + story.getCreatorUsername());

        TextView genre = (TextView) findViewById(R.id.title_page_genre);
        genre.setText(story.getGenre());
        */

        TextView creator_username = (TextView) findViewById(R.id.title_page_creator_username);
        String edit_date =  story.getLastEditDate();
        String year = edit_date.substring(0, 4);
        creator_username.setText("© " + year + " " + story.getCreatorUsername());

        Button beginStoryBtn = (Button) findViewById(R.id.title_page_button);
        beginStoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //load StoryPageActivity intent, starting with page 1
                Bundle extra = new Bundle();
                extra.putSerializable("Story", story);
                extra.putSerializable("Page", 1);

                Intent intent = new Intent(MainMenuActivity.CONTEXT_NAME, StoryPageActivity.class);
                intent.putExtra("extra", extra);
                finish();
                startActivity(intent);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setRectangleBackground() {
        LinearLayout rectangleBackground = (LinearLayout) findViewById(R.id.title_layout_rectangle);
        TypedValue a = new TypedValue();
        getTheme().resolveAttribute(android.R.attr.windowBackground, a, true);

        if (a.type >= TypedValue.TYPE_FIRST_COLOR_INT && a.type <= TypedValue.TYPE_LAST_COLOR_INT) {
            // windowBackground is a color
            int color = a.data;
            rectangleBackground.setBackgroundColor(color);
        } else {
            // windowBackground is not a color, probably a drawable
            Drawable d = MainMenuActivity.CONTEXT_NAME.getResources().getDrawable(a.resourceId);
            rectangleBackground.setBackground(d);
        }

        Drawable background = rectangleBackground.getBackground();
        background.setAlpha(166); //255 max, so 65% is 166
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void saveSettings() {
        SharedPreferences settings = getSharedPreferences(MainMenuActivity.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("activity", "title_page");
        editor.putString("story", story.getUniqueID());

        // Commit edits
        editor.commit();
    }
}
