package wclem12.com.agameofyou.about;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.activity.MainMenuActivity;
import wclem12.com.agameofyou.util.Utils;

public class AboutStoryAdapter extends SimpleAdapter {
    private Context mContext;

    public AboutStoryAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);

        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        TextView tagText = (TextView) view.findViewById(R.id.about_story_tag);
        TextView dataText = (TextView) view.findViewById(R.id.about_story_data);

        switch (Utils.sTheme) {
            default:
            case Utils.THEME_LIGHT:
                tagText.setTextColor(MainMenuActivity.CONTEXT_NAME.getResources().getColor(android.R.color.primary_text_light));
                dataText.setTextColor(MainMenuActivity.CONTEXT_NAME.getResources().getColor(android.R.color.primary_text_light));
                break;
            case Utils.THEME_DARK:
                tagText.setTextColor(MainMenuActivity.CONTEXT_NAME.getResources().getColor(android.R.color.primary_text_dark));
                dataText.setTextColor(MainMenuActivity.CONTEXT_NAME.getResources().getColor(android.R.color.primary_text_dark));
                break;
            case Utils.THEME_SEPIA:
                tagText.setTextColor(MainMenuActivity.CONTEXT_NAME.getResources().getColor(R.color.sepia_dark));
                dataText.setTextColor(MainMenuActivity.CONTEXT_NAME.getResources().getColor(R.color.sepia_dark));
                break;
        }

        return view;
    }
}
