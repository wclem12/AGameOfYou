package wclem12.com.agameofyou.about_story;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.activity.BaseActivity;
import wclem12.com.agameofyou.story.Story;
import wclem12.com.agameofyou.util.BaseRecyclerAdapter;
import wclem12.com.agameofyou.util.DividerItemDecoration;

public class AboutStoryActivity extends BaseActivity {
    @InjectView(R.id.about_story_list) public RecyclerView aboutStoryRV;
    @InjectView(R.id.toolbar) public Toolbar toolbar;

    private ArrayList<AboutStoryItem> aboutStoryItemList;
    private String[] tagList = { "Title", "Author", "Synopsis", "Create Date", "Last Edit",
                                    "Creator Username", "Genre", "Tags", "Language" };
    Story story;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extra = getIntent().getBundleExtra("extra");
        story = (Story) extra.getSerializable("Story");

        aboutStoryItemList = new ArrayList<>();

        setContentView(R.layout.activity_about_story);
        setTitle("About " + story.getTitle());

        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] dataList = { story.getTitle(), story.getAuthor(), story.getSynopsis(),
                                story.getCreateDate(), story.getLastEditDate(),
                                story.getCreatorUsername(), story.getGenre(),
                                story.getTags(), story.getLanguage() };

        for(int i = 0; i < tagList.length; i++) {
            AboutStoryItem aboutStoryItem = new AboutStoryItem(tagList[i], dataList[i]);
            aboutStoryItemList.add(aboutStoryItem);
        }

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        aboutStoryRV.setLayoutManager(layoutManager);
        aboutStoryRV.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        AboutStoryRecyclerAdapter aboutStoryAdapter = new AboutStoryRecyclerAdapter(null, null);
        aboutStoryRV.setAdapter(aboutStoryAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class AboutStoryRecyclerAdapter extends BaseRecyclerAdapter<AboutStoryItem> {

        public AboutStoryRecyclerAdapter(View.OnClickListener clickListener, View.OnLongClickListener longClickListener) {
            super(AboutStoryActivity.this, clickListener, longClickListener);
            this.items = aboutStoryItemList;
        }

        @Override public View newView(ViewGroup container) {
            return inflater.inflate(R.layout.view_about_story, container, false);
        }

        @Override public void bindView(AboutStoryItem item, int position, View view) {
            AboutStoryView aboutStoryView = (AboutStoryView) view;
            aboutStoryView.bindTo(item);
        }
    }
}
