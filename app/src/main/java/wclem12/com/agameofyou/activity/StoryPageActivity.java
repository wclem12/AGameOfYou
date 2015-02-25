package wclem12.com.agameofyou.activity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.activity.fragment.SettingsFragment;
import wclem12.com.agameofyou.story.PageButton;
import wclem12.com.agameofyou.story.Story;
import wclem12.com.agameofyou.story.StoryAdapter;
import wclem12.com.agameofyou.story.StoryPage;

public class StoryPageActivity extends BaseActivity {
    private Story story;
    private int currentPage = 1;

    // Content keys
    final String KEY_BUTTON_TEXT = MainMenuActivity.CONTEXT_NAME.getString(R.string.button_text_tag);
    final String KEY_BUTTON_DESTINATION = MainMenuActivity.CONTEXT_NAME.getString(R.string.button_destination_tag);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
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
                Bundle bundle = new Bundle();
                bundle.putString("activity", "story");

                SettingsFragment settingsFragment = new SettingsFragment();
                settingsFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(android.R.id.content, settingsFragment);
                fragmentTransaction.addToBackStack("settings");
                fragmentTransaction.commit();
                return true;
            case R.id.action_restart:
                //load page 1
                loadPage(1);
                //update saved settings
                return true;
            case R.id.action_main_menu:
                //save settings
                finish();
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadPage(int destination){
        setContentView(R.layout.story_page);

        setTitle(story.getTitle());

        //update current page for shared prefs
        currentPage = destination;

        //since we're dealing with indexes, subtract one
        destination--;
        StoryPage storyPage = story.getStoryPage(destination);

        //buttons created call this function
        ArrayList<HashMap<String, String>> buttons = new ArrayList<HashMap<String, String>>();

        ArrayList<PageButton> buttonList = storyPage.getButtonList();
        for(int i = 0; i < buttonList.size(); i++) {
            HashMap<String, String> buttonMap = new HashMap<String, String>();

            PageButton pb = buttonList.get(i);

            buttonMap.put(KEY_BUTTON_TEXT, pb.getText());
            buttonMap.put(KEY_BUTTON_DESTINATION, String.valueOf(pb.getDestination()));

            buttons.add(buttonMap);
        }

        View header = getLayoutInflater().inflate(R.layout.story_page_header, null);

        ListAdapter adapter = new StoryAdapter(MainMenuActivity.CONTEXT_NAME, buttons, R.layout.story_page_button_item, new String[]{KEY_BUTTON_TEXT, KEY_BUTTON_DESTINATION}, new int[]{R.id.story_page_button_text, R.id.story_page_button_destination});
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.addHeaderView(header,null,false);

        TextView text = (TextView) findViewById(R.id.story_page_text);
        text.setText(storyPage.getText());

        listView.setAdapter(adapter);

        //for buttons, id is page_next_button from story_page_button_itembutton_item.xml
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int destination = Integer.valueOf(((TextView) view.findViewById(R.id.story_page_button_destination)).getText().toString());
                loadPage(destination);
            }
        });

        saveSettings();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        saveSettings();

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        saveSettings();

        super.onStop();
    }

    private void saveSettings() {
        Log.i("....", "Story activity saved");

        SharedPreferences settings = getSharedPreferences(MainMenuActivity.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("activity", "story_page");
        editor.putString("story", story.getUniqueID());
        editor.putInt("page", currentPage);

        // Commit edits
        editor.commit();
    }
}
