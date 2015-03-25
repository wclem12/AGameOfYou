package wclem12.com.agameofyou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.about_story.AboutStoryActivity;
import wclem12.com.agameofyou.page.PageActivity;
import wclem12.com.agameofyou.story.Story;
import wclem12.com.agameofyou.util.Utils;

public class TitlePageActivity extends BaseActivity {
    @InjectView(R.id.title_page_title) public TextView title;
    @InjectView(R.id.title_page_author) public TextView author;
    @InjectView(R.id.title_page_button) public Button beginStoryBtn;
    @InjectView(R.id.coverImageLarge) public ImageView cover;
    @InjectView(R.id.toolbar) public Toolbar toolbar;


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

    private void loadTitlePage() {
        setContentView(R.layout.activitiy_title_page);
        setTitle(story.getTitle());
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Load data
        title.setText(story.getTitle());
        author.setText("By: " + story.getAuthor());

        String coverStr = story.getCover();
        cover.setImageResource(getImageId(coverStr));

        //click listener for Begin button loads the story
        beginStoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //load StoryPageActivity intent, starting with page 1
                Bundle extra = new Bundle();
                extra.putSerializable("Story", story);
                extra.putSerializable("Page", 1);

                Intent intent = new Intent(MyBookshelfActivity.CONTEXT_NAME, PageActivity.class);
                intent.putExtra("extra", extra);
                finish();
                startActivity(intent);
            }
        });
    }

    private int getImageId (String imageName) {
        return getResources().getIdentifier(imageName, "drawable", MyBookshelfActivity.PACKAGE_NAME);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_title_page, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_title_about_story:
                Bundle extra = new Bundle();
                extra.putSerializable("Story", story);

                Intent intent = new Intent(this, AboutStoryActivity.class);
                intent.putExtra("extra", extra);

                startActivity(intent);
                return true;
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Utils.SaveSettings(Utils.ACTIVITY_MAIN, -1, -1);

        super.onBackPressed();
    }
}
