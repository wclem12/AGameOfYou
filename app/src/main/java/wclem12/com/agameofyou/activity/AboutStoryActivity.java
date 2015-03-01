package wclem12.com.agameofyou.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.about.AboutStoryAdapter;
import wclem12.com.agameofyou.story.Story;

public class AboutStoryActivity extends BaseActivity{
    final String KEY_TAG = MainMenuActivity.CONTEXT_NAME.getString(R.string.about_story_page_tag);
    final String KEY_DATA = MainMenuActivity.CONTEXT_NAME.getString(R.string.about_story_page_data);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Add up navigation to this fragment
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extra = getIntent().getBundleExtra("extra");
        Story story = (Story) extra.getSerializable("Story");

        ArrayList<HashMap<String, String>> detailsList = new ArrayList<HashMap<String, String>>();

        setContentView(R.layout.about_story_page);
        setTitle("About " + story.getTitle());

        detailsList.add(addToDataList("Title", story.getTitle())); //title
        detailsList.add(addToDataList("Author", story.getAuthor())); //author
        detailsList.add(addToDataList("Create Date", story.getCreateDate())); //create_date
        detailsList.add(addToDataList("Last Edit", story.getLastEditDate())); //last_edit_date
        detailsList.add(addToDataList("Creator Username", story.getCreatorUsername())); //creator_username
        detailsList.add(addToDataList("Genre", story.getGenre())); //genre
        detailsList.add(addToDataList("Tags", story.getTags())); //tags

        ListAdapter listAdapter = new AboutStoryAdapter(MainMenuActivity.CONTEXT_NAME, detailsList, R.layout.about_story_item, new String[] {KEY_TAG, KEY_DATA}, new int[] {R.id.about_story_tag, R.id.about_story_data});

        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(listAdapter);
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

}
