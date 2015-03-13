package wclem12.com.agameofyou.about_story;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import wclem12.com.agameofyou.R;

public class AboutStoryView extends LinearLayout {
    @InjectView(R.id.about_story_tag) TextView tag;
    @InjectView(R.id.about_story_data) TextView data;

    public AboutStoryItem aboutStoryItem;

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
    }

    public AboutStoryView(Context context, AttributeSet attrs) { super(context, attrs); }

    public void bindTo(final AboutStoryItem aboutStoryItem) {
        this.aboutStoryItem = aboutStoryItem;

        //set text
        tag.setText(aboutStoryItem.getTag());
        data.setText(aboutStoryItem.getData());
    }
}
