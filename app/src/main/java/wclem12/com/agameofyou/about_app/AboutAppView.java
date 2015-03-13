package wclem12.com.agameofyou.about_app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.activity.MyLibraryActivity;
import wclem12.com.agameofyou.util.Utils;

public class AboutAppView extends LinearLayout{
    @InjectView(R.id.description) public TextView description;
    @InjectView(R.id.uri) public TextView uri;
    @InjectView(R.id.iconImage) public ImageView icon;

    public AboutAppItem aboutAppItem;

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
    }

    public AboutAppView(Context context, AttributeSet attrs) { super(context, attrs); }

    public void bindTo(final AboutAppItem aboutAppItem) {
        this.aboutAppItem = aboutAppItem;

        description.setText(aboutAppItem.getDescription());
        uri.setText(aboutAppItem.getUri());

        int color = 0;

        switch (Utils.sTheme) {
            default:
            case Utils.THEME_LIGHT:
                color = MyLibraryActivity.CONTEXT_NAME.getResources().getColor(android.R.color.primary_text_light);
                break;
            case Utils.THEME_DARK:
                color = MyLibraryActivity.CONTEXT_NAME.getResources().getColor(android.R.color.primary_text_dark);
                break;
            case Utils.THEME_SEPIA:
                color = MyLibraryActivity.CONTEXT_NAME.getResources().getColor(R.color.sepia_dark);
                break;
        }

        setTextColor(description, color);

        String iconStr = aboutAppItem.getIcon();

        if(description.getText().toString().toLowerCase().contains("version")) {
            icon.setImageResource(getImageId(iconStr));

            LinearLayout.LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER;
            description.setLayoutParams(lp);
        } else {
            icon.setImageResource(getImageId(iconStr));
        }
    }

    private void setTextColor (TextView textView, int color) {
        textView.setTextColor(color);
    }

    private int getImageId (String imageName) {
        return getResources().getIdentifier(imageName, "drawable", MyLibraryActivity.PACKAGE_NAME);
    }
}
