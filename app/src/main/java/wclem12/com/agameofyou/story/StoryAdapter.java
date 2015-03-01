package wclem12.com.agameofyou.story;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.activity.MainMenuActivity;
import wclem12.com.agameofyou.util.Utils;

public class StoryAdapter extends SimpleAdapter {
        public StoryAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            TextView buttonText = (TextView) view.findViewById(R.id.story_page_button_text);

            switch (Utils.sTheme) {
                default:
                case Utils.THEME_LIGHT:
                    buttonText.setTextColor(MainMenuActivity.CONTEXT_NAME.getResources().getColor(android.R.color.primary_text_light));
                    break;
                case Utils.THEME_DARK:
                    buttonText.setTextColor(MainMenuActivity.CONTEXT_NAME.getResources().getColor(android.R.color.primary_text_dark));
                    break;
                case Utils.THEME_SEPIA:
                    buttonText.setTextColor(MainMenuActivity.CONTEXT_NAME.getResources().getColor(R.color.sepia_dark));
                    break;
            }

            buttonText.setTextSize(TypedValue.COMPLEX_UNIT_SP, Utils.sTextSize);

            Typeface typeface = Typeface.createFromAsset(MainMenuActivity.CONTEXT_NAME.getAssets(), "fonts/" + Utils.sFontStyle);
            buttonText.setTypeface(typeface, Typeface.BOLD);

            return view;
        }
}
