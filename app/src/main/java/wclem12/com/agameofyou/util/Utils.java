package wclem12.com.agameofyou.util;

import android.app.Activity;
import android.content.Intent;

import wclem12.com.agameofyou.CustomTextView;
import wclem12.com.agameofyou.R;

public class Utils {
    public static String sTheme = "Theme.Light";

    public final static String THEME_LIGHT = "Theme.Light";
    public final static String THEME_DARK = "Theme.Dark";
    public final static String THEME_SEPIA = "Theme.Sepia";

    public static float sTextSize = 18;

    public final static float TEXTSIZE_SMALL = 14;
    public final static float TEXTSIZE_MEDIUM = 18;
    public final static float TEXTSIZE_LARGE = 22;

    public static void changeToTheme(Activity activity, String theme){
        sTheme = theme;

        Intent i = activity.getBaseContext().getPackageManager().getLaunchIntentForPackage(activity.getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(i);
    }

    public static void onActivityCreateSetTheme(Activity activity) {
        switch (sTheme) {
            default:
            case THEME_LIGHT:
                activity.setTheme(R.style.Theme_Light);
                break;
            case THEME_DARK:
                activity.setTheme(R.style.Theme_Dark);
                break;
            case THEME_SEPIA:
                activity.setTheme(R.style.Theme_Sepia);
                break;
        }
    }

    public static void changeTextSize(Activity activity, float textSize, String callingActivity) {
        if (textSize == TEXTSIZE_SMALL) {
            sTextSize = TEXTSIZE_SMALL;
        } else if (textSize == TEXTSIZE_MEDIUM) {
            sTextSize = TEXTSIZE_MEDIUM;
        } else if (textSize == TEXTSIZE_LARGE) {
            sTextSize = TEXTSIZE_LARGE;
        }

        if(callingActivity.equals("story")) {
            CustomTextView storyPageButtonText = (CustomTextView) activity.findViewById(R.id.story_page_button_text);
            storyPageButtonText.setTextSize(sTextSize);

            CustomTextView storyPageText = (CustomTextView) activity.findViewById(R.id.story_page_text);
            storyPageText.setTextSize(sTextSize);
        }

    }
}
