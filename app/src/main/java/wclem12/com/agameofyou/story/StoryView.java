package wclem12.com.agameofyou.story;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.activity.MyLibraryActivity;

public class StoryView extends LinearLayout {
    @InjectView(R.id.title) TextView title;
    @InjectView(R.id.author) TextView author;
    @InjectView(R.id.coverImage) ImageView cover;

    public Story story;

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
    }

    public StoryView(Context context, AttributeSet attrs) { super(context, attrs); }

    public void bindTo(final Story story) {
        this.story = story;

        //set text
        title.setText(story.getTitle());
        author.setText(story.getAuthor());

        //retrieve cover filename and set cover
        String coverStr = story.getCover();
        cover.setImageResource(getImageId(coverStr));
    }

    private int getImageId (String imageName) {
        return getResources().getIdentifier(imageName, "drawable", MyLibraryActivity.PACKAGE_NAME);
    }
}
