package wclem12.com.agameofyou.page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.activity.BaseActivity;
import wclem12.com.agameofyou.choice.Choice;
import wclem12.com.agameofyou.choice.ChoiceView;
import wclem12.com.agameofyou.story.Story;
import wclem12.com.agameofyou.util.BaseRecyclerAdapter;
import wclem12.com.agameofyou.util.DividerItemDecoration;
import wclem12.com.agameofyou.util.Utils;

public class PageActivity extends BaseActivity {
    @InjectView(R.id.choice_list) public RecyclerView choiceListRV;
    @InjectView(R.id.toolbar) public Toolbar toolbar;

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

        loadPage(story.getCurrentPage());
    }

    private void loadPage(int destination){
        setContentView(R.layout.activity_story_page);
        setTitle(story.getTitle());

        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //updateProgress current page for shared prefs
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
        updateProgress();
    }

    private void updateProgress() {
        Utils.UpdateProgress(story.getId(), currentPage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_restart:
                restart();
                return true;
            case android.R.id.home:
                mainMenu();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void restart() {
        //load page 1
        loadPage(1);
        //updateProgress saved settings
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

                if(location.equals(getResources().getString(R.string.main_menu))) {
                    mainMenu();
                } else if (location.equals(getResources().getString(R.string.restart))) {
                    restart();
                }

            } else {
                loadPage(destination);
            }
        }
    }

    @Override
    protected void onStop() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        super.onStop();
    }

    @Override
    public void onBackPressed() {
        Utils.SaveSettings("activity_ui", -1, -1);

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
