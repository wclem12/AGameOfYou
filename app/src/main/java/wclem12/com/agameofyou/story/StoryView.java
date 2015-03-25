package wclem12.com.agameofyou.story;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.activity.MyBookshelfActivity;

public class StoryView extends LinearLayout {
    @InjectView(R.id.coverImage) ImageView cover;
    @InjectView(R.id.title) TextView title;
    @InjectView(R.id.author) TextView author;
    @InjectView(R.id.progress) TextView progress;

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

        //calculate progress
        String percent;
        if(story.getCurrentPage() > 1) {
            percent = Float.toString((story.getCurrentPage() * 100.0f) / story.getPageCount());
        } else {
            percent = "0";
        }

        progress.setText(percent + "%");

        //retrieve cover filename and set cover
        String coverStr = story.getCover();
        cover.setImageResource(getImageId(coverStr));
    }

    private int getImageId (String imageName) {
        return getResources().getIdentifier(imageName, "drawable", MyBookshelfActivity.PACKAGE_NAME);
    }
}
