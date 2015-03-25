package wclem12.com.agameofyou.story;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import wclem12.com.agameofyou.R;

public class StoryOptionsView extends LinearLayout {
    @InjectView(R.id.option_read) TextView read;
    @InjectView(R.id.option_about) TextView about;
    @InjectView(R.id.option_favorite) ImageView favorite;
    @InjectView(R.id.option_delete) ImageView delete;

    public Story story;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
    }

    public StoryOptionsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void bindTo(final Story story) {
        this.story = story;
    }
}