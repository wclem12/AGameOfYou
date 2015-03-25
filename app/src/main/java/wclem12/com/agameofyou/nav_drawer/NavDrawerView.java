package wclem12.com.agameofyou.nav_drawer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.activity.MyBookshelfActivity;

public class NavDrawerView extends LinearLayout {
    @InjectView(R.id.iconImage) public ImageView icon;
    @InjectView(R.id.description) public TextView description;

    public NavDrawerItem navDrawerItem;

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
    }

    public NavDrawerView(Context context, AttributeSet attrs) { super(context, attrs); }

    public void bindTo(final NavDrawerItem navDrawerItem) {
        this.navDrawerItem = navDrawerItem;

        description.setText(navDrawerItem.getDescription());

        String iconStr = navDrawerItem.getIcon();
        icon.setImageResource(getImageId(iconStr));
    }

    private int getImageId (String imageName) {
        return getResources().getIdentifier(imageName, "drawable", MyBookshelfActivity.PACKAGE_NAME);
    }
}
