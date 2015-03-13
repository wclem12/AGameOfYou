package wclem12.com.agameofyou.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.about.AboutStoryItem;
import wclem12.com.agameofyou.story.Story;
import wclem12.com.agameofyou.util.BaseAdapter;
import wclem12.com.agameofyou.util.DividerItemDecoration;

public class AboutStoryActivity extends BaseActivity{
    final String KEY_TAG = MyLibraryActivity.CONTEXT_NAME.getString(R.string.about_story_page_tag);
    final String KEY_DATA = MyLibraryActivity.CONTEXT_NAME.getString(R.string.about_story_page_data);

    private ArrayList<AboutStoryItem> aboutStoryItemList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Add up navigation to this fragment
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extra = getIntent().getBundleExtra("extra");
        Story story = (Story) extra.getSerializable("Story");

        aboutStoryItemList = new ArrayList<>();

        setContentView(R.layout.activity_about_story);
        setTitle("About " + story.getTitle());

        //title
        AboutStoryItem aboutStoryItem = new AboutStoryItem();
        aboutStoryItem.setTag("Title");
        aboutStoryItem.setData(story.getTitle());
        aboutStoryItemList.add(aboutStoryItem);

        //author
        aboutStoryItem = new AboutStoryItem();
        aboutStoryItem.setTag("Author");
        aboutStoryItem.setData(story.getAuthor());
        aboutStoryItemList.add(aboutStoryItem);

        //create_date
        aboutStoryItem = new AboutStoryItem();
        aboutStoryItem.setTag("Create Date");
        aboutStoryItem.setData(story.getCreateDate());
        aboutStoryItemList.add(aboutStoryItem);

        //last_edit_date
        aboutStoryItem = new AboutStoryItem();
        aboutStoryItem.setTag("Last Edit");
        aboutStoryItem.setData(story.getLastEditDate());
        aboutStoryItemList.add(aboutStoryItem);

        //creator_username
        aboutStoryItem = new AboutStoryItem();
        aboutStoryItem.setTag("Creator Username");
        aboutStoryItem.setData(story.getCreatorUsername());
        aboutStoryItemList.add(aboutStoryItem);

        //genre
        aboutStoryItem = new AboutStoryItem();
        aboutStoryItem.setTag("Genre");
        aboutStoryItem.setData(story.getGenre());
        aboutStoryItemList.add(aboutStoryItem);

        //tags
        aboutStoryItem = new AboutStoryItem();
        aboutStoryItem.setTag("Tags");
        aboutStoryItem.setData(story.getTags());
        aboutStoryItemList.add(aboutStoryItem);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.about_story_list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        AboutStoryAdapter aboutStoryAdapter = new AboutStoryAdapter(null, null);
        recyclerView.setAdapter(aboutStoryAdapter);

    }

    private HashMap<String, String> addToDataList(String tag, String data) {
        HashMap<String, String> detailsMap = new HashMap<String, String>();
        detailsMap.put(KEY_TAG, tag);
        detailsMap.put(KEY_DATA, data);

        return detailsMap;
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

    @Override
    public void onStop() {
        getActionBar().setDisplayHomeAsUpEnabled(false);

        super.onStop();
    }

    class AboutStoryAdapter extends BaseAdapter<AboutStoryItem> {

        public AboutStoryAdapter(View.OnClickListener clickListener, View.OnLongClickListener longClickListener) {
            super(AboutStoryActivity.this, clickListener, longClickListener);
            this.items = aboutStoryItemList;
        }

        @Override public View newView(ViewGroup container) {
            return inflater.inflate(R.layout.view_about_story, container, false);
        }

        @Override public void bindView(AboutStoryItem aboutStoryItem, int position, View view) {
            TextView tagText = (TextView) view.findViewById(R.id.about_story_tag);
            TextView dataText = (TextView) view.findViewById(R.id.about_story_data);

            tagText.setText(aboutStoryItem.getTag());
            dataText.setText(aboutStoryItem.getData());
        }
    }
}
