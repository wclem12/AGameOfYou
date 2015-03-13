package wclem12.com.agameofyou.page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.about_app.AboutAppActivity;
import wclem12.com.agameofyou.activity.BaseActivity;
import wclem12.com.agameofyou.activity.MyLibraryActivity;
import wclem12.com.agameofyou.settings.SettingsActivity;
import wclem12.com.agameofyou.story.Story;
import wclem12.com.agameofyou.util.BaseRecyclerAdapter;
import wclem12.com.agameofyou.util.DividerItemDecoration;
import wclem12.com.agameofyou.util.Utils;

public class PageActivity extends BaseActivity {
    @InjectView(R.id.choice_list) public RecyclerView choiceListRV;

    private Story story;
    private int currentPage = 1;

    // Content keys
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

    private void loadPage(int destination){
        setContentView(R.layout.activity_story_page);
        setTitle(story.getTitle());

        ButterKnife.inject(this);

        //update current page for shared prefs
        currentPage = destination;

        //since we're dealing with indexes, subtract one
        destination--;
        Page page = story.getStoryPage(destination);

        choiceList = page.getChoiceList();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        choiceListRV.setLayoutManager(layoutManager);
        choiceListRV.setItemAnimator(new DefaultItemAnimator());
        choiceListRV.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        ChoiceRecyclerAdapter storyAdapter = new ChoiceRecyclerAdapter(choiceClick, choiceLongClick);
        choiceListRV.setAdapter(storyAdapter);

        Utils.SaveSettings(Utils.ACTIVITY_STORY, story.getId(), currentPage);
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
                intent = new Intent(MyLibraryActivity.CONTEXT_NAME, AboutAppActivity.class);
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
        int destination = Integer.valueOf(((TextView) v.findViewById(R.id.page_choice_destination)).getText().toString());

        if(destination >= 0) {
            if(destination == 0) {
                String location = ((TextView) v.findViewById(R.id.page_choice_text)).getText().toString();

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

    class ChoiceRecyclerAdapter extends BaseRecyclerAdapter<Choice> {

        public ChoiceRecyclerAdapter(View.OnClickListener clickListener, View.OnLongClickListener longClickListener) {
            super(PageActivity.this, clickListener, longClickListener);
            this.items = choiceList;
        }

        @Override public View newView(ViewGroup container) {
            return inflater.inflate(R.layout.view_story_page, container, false);
        }

        @Override public void bindView(Choice item, int position, View view) {
            ChoiceView choiceView = (ChoiceView) view;
            choiceView.bindTo(item);
        }
    }
}
