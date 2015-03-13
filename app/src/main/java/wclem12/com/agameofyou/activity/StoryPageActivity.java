package wclem12.com.agameofyou.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.story.Story;
import wclem12.com.agameofyou.story.Page;
import wclem12.com.agameofyou.story.Choice;
import wclem12.com.agameofyou.util.BaseAdapter;
import wclem12.com.agameofyou.util.DividerItemDecoration;
import wclem12.com.agameofyou.util.Utils;

public class StoryPageActivity extends BaseActivity {
    private Story story;
    private int currentPage = 1;

    // Content keys
    final String KEY_BUTTON_TEXT = MyLibraryActivity.CONTEXT_NAME.getString(R.string.button_text_tag);
    final String KEY_BUTTON_DESTINATION = MyLibraryActivity.CONTEXT_NAME.getString(R.string.button_destination_tag);
    final int REQUEST_CODE = 12345;

    private ArrayList<Choice> choiceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extra = getIntent().getBundleExtra("extra");
        story = (Story) extra.getSerializable("Story");
        int page = (Integer) extra.getSerializable("Page");

        loadPage(page);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_story_page, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                Bundle extra = new Bundle();
                extra.putSerializable("Activity", Utils.ACTIVITY_STORY);

                Intent intent = new Intent(MyLibraryActivity.CONTEXT_NAME, SettingsActivity.class);
                intent.putExtra("extra", extra);
                startActivityForResult(intent, REQUEST_CODE);
                return true;
            case R.id.action_restart:
                restart();
                return true;
            case R.id.action_main_menu:
                mainMenu();
                return true;
            case R.id.action_about:
                intent = new Intent(MyLibraryActivity.CONTEXT_NAME, AboutActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void restart() {
        //load page 1
        loadPage(1);
        //update saved settings
    }

    private void mainMenu() {
        finish();
        finish();
        Utils.SaveSettings(Utils.ACTIVITY_MAIN, -1, -1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            doRefresh();
        }
    }

    private void doRefresh() {
        finish();
        startActivity(getIntent());
    }

    private void loadPage(int destination){
        setContentView(R.layout.activity_story_page);

        setTitle(story.getTitle());

        //update current page for shared prefs
        currentPage = destination;

        //since we're dealing with indexes, subtract one
        destination--;
        Page page = story.getStoryPage(destination);

        choiceList = page.getChoiceList();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.story_page_list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        StoryPageAdapter storyAdapter = new StoryPageAdapter(choiceClick, choiceLongClick);
        recyclerView.setAdapter(storyAdapter);

        Utils.SaveSettings(Utils.ACTIVITY_STORY, story.getId(), currentPage);
    }

    View.OnClickListener choiceClick = new View.OnClickListener() {
        @Override public void onClick(View v) {
            click(v);
        }
    };

    View.OnLongClickListener choiceLongClick = new View.OnLongClickListener() {
        @Override public boolean onLongClick(View v) {
            click(v);

            return true;
        }
    };

    private void click(View v) {
        int destination = Integer.valueOf(((TextView) v.findViewById(R.id.story_page_button_destination)).getText().toString());

        if(destination >= 0) {
            if(destination == 0) {
                String location = ((TextView) v.findViewById(R.id.story_page_button_text)).getText().toString();

                if(location.equals("Main Menu")) {
                    mainMenu();
                } else if (location.equals("Restart Story")) {
                    restart();
                }

            } else {
                loadPage(destination);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Utils.SaveSettings("activity_mylibrary", -1, -1);

        super.onBackPressed();
    }

    class StoryPageAdapter extends BaseAdapter<Choice> {

        public StoryPageAdapter(View.OnClickListener clickListener, View.OnLongClickListener longClickListener) {
            super(StoryPageActivity.this, clickListener, longClickListener);
            this.items = choiceList;
        }

        @Override public View newView(ViewGroup container) {
            return inflater.inflate(R.layout.view_story_page, container, false);
        }

        @Override public void bindView(Choice choice, int position, View view) {
            TextView choiceText = (TextView) view.findViewById(R.id.story_page_button_text);
            TextView choiceDest = (TextView) view.findViewById(R.id.story_page_button_destination);

            choiceText.setText(choice.getText());
            choiceDest.setText(String.valueOf(choice.getDestination()));

            choiceText.setTextSize(TypedValue.COMPLEX_UNIT_SP, Utils.sTextSize);

            Typeface typeface = Typeface.createFromAsset(MyLibraryActivity.CONTEXT_NAME.getAssets(), "fonts/" + Utils.sFontStyle);

            if(choiceDest.getText().equals("-1")) {
                choiceText.setTypeface(typeface);
                choiceText.setGravity(Gravity.NO_GRAVITY);
            } else {
                choiceText.setTypeface(typeface, Typeface.BOLD);
                choiceText.setGravity(Gravity.CENTER);
            }
        }
    }
}
