package wclem12.com.agameofyou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.story.Story;
import wclem12.com.agameofyou.util.Utils;

public class TitlePageActivity extends BaseActivity {
    private Story story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //pass this onto StoryPageActivity
        Bundle extra = getIntent().getBundleExtra("extra");
        story = (Story) extra.getSerializable("Story");

        loadTitlePage();

        Utils.SaveSettings(Utils.ACTIVITY_TITLE, story.getId(), -1);
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
        Bundle extra = new Bundle();
        Intent intent;

        switch (item.getItemId()) {
            case R.id.action_settings:
                extra.putSerializable("Activity", Utils.ACTIVITY_TITLE);
                intent = new Intent(MainMenuActivity.CONTEXT_NAME, SettingsActivity.class);
                intent.putExtra("extra", extra);
                startActivity(intent);
                return true;
            case R.id.action_main_menu:
                finish();
                Utils.SaveSettings(Utils.ACTIVITY_MAIN, null, -1);
                return true;
            case R.id.action_story_about:
                extra.putSerializable("Story", story);

                intent = new Intent(MainMenuActivity.CONTEXT_NAME, AboutStoryActivity.class);
                intent.putExtra("extra", extra);

                startActivity(intent);
                return true;
            case R.id.action_about:
                intent = new Intent(MainMenuActivity.CONTEXT_NAME, AboutActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadTitlePage() {
        setContentView(R.layout.title_page);
        setTitle(story.getTitle());

        //Load data
        //title text
        TextView title = (TextView) findViewById(R.id.title_page_title);
        title.setText(story.getTitle());

        //author text
        TextView author = (TextView) findViewById(R.id.title_page_author);
        author.setText("By: " + story.getAuthor());

        //cover image
        ImageView cover = (ImageView) findViewById(R.id.coverImageLarge);
        String coverStr = story.getCover();
        cover.setImageResource(getResources().getIdentifier(coverStr, "drawable", MainMenuActivity.PACKAGE_NAME));

        //click listener for Begin button loads the story
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

    @Override
    public void onBackPressed() {
        Utils.SaveSettings(Utils.ACTIVITY_MAIN, null, -1);

        super.onBackPressed();
    }
}
