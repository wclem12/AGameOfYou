package wclem12.com.agameofyou.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.widget.TextView;

import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.activity.MyBookshelfActivity;

public class Utils {
    public static String sTheme = "Theme.Light";

    public final static String THEME_LIGHT = "Theme.Light";
    public final static String THEME_DARK = "Theme.Dark";
    public final static String THEME_SEPIA = "Theme.Sepia";

    public static float sTextSize = 18;

    public final static float TEXTSIZE_SMALL = 14;
    public final static float TEXTSIZE_MEDIUM = 18;
    public final static float TEXTSIZE_LARGE = 22;

    public static String sFontStyle = "Roboto-Regular.ttf";
    public static String sFontStyleBold = "Roboto-Bold.ttf";

    public final static String FONTSTYLE_CAVIAR_DREAMS = "CaviarDreams-Regular.ttf";
    public final static String FONTSTYLE_DROID_SERIF = "DroidSerif-Regular.ttf";
    public final static String FONTSTYLE_EXO = "Exo-Regular.otf";
    public final static String FONTSTYLE_LIBRE_BASKERVILLE = "LibreBaskerville-Regular.otf";
    public final static String FONTSTYLE_QUICKSAND = "Quicksand-Regular.otf";
    public final static String FONTSTYLE_RALEWAY = "Raleway-Regular.ttf";
    public final static String FONTSTYLE_ROBOTO = "Roboto-Regular.ttf";
    public final static String FONTSTYLE_SOURCE_SANS_PRO = "SourceSansPro-Regular.otf";
    public final static String FONTSTYLE_TITILLIUM = "Titillium-Regular.otf";

    public final static String FONTSTYLEBOLD_CAVIAR_DREAMS = "CaviarDreams-Bold.ttf";
    public final static String FONTSTYLEBOLD_DROID_SERIF = "DroidSerif-Bold.ttf";
    public final static String FONTSTYLEBOLD_EXO = "Exo-Bold.otf";
    public final static String FONTSTYLEBOLD_LIBRE_BASKERVILLE = "LibreBaskerville-Bold.otf";
    public final static String FONTSTYLEBOLD_QUICKSAND = "Quicksand-Bold.otf";
    public final static String FONTSTYLEBOLD_RALEWAY = "Raleway-Bold.ttf";
    public final static String FONTSTYLEBOLD_ROBOTO = "Roboto-Bold.ttf";
    public final static String FONTSTYLEBOLD_SOURCE_SANS_PRO = "SourceSansPro-Bold.otf";
    public final static String FONTSTYLEBOLD_TITILLIUM = "Titillium-Bold.otf";

    public final static String ACTIVITY_MAIN = "activity_ui";
    public final static String ACTIVITY_TITLE = "activitiy_title_page";
    public final static String ACTIVITY_STORY = "activity_story_page";

    public static void changeTextSize(float textSize) {
        sTextSize = textSize;
    }

    public static void changeFontStyle(String fontStyle) {
        switch (fontStyle) {
            default:
            case FONTSTYLE_CAVIAR_DREAMS:
                sFontStyleBold = FONTSTYLEBOLD_CAVIAR_DREAMS;
                break;
            case FONTSTYLE_DROID_SERIF:
                sFontStyleBold = FONTSTYLEBOLD_DROID_SERIF;
                break;
            case FONTSTYLE_EXO:
                sFontStyleBold = FONTSTYLEBOLD_EXO;
                break;
            case FONTSTYLE_LIBRE_BASKERVILLE:
                sFontStyleBold = FONTSTYLEBOLD_LIBRE_BASKERVILLE;
                break;
            case FONTSTYLE_QUICKSAND:
                sFontStyleBold = FONTSTYLEBOLD_QUICKSAND;
                break;
            case FONTSTYLE_RALEWAY:
                sFontStyleBold = FONTSTYLEBOLD_RALEWAY;
                break;
            case FONTSTYLE_ROBOTO:
                sFontStyleBold = FONTSTYLEBOLD_ROBOTO;
                break;
            case FONTSTYLE_SOURCE_SANS_PRO:
                sFontStyleBold = FONTSTYLEBOLD_SOURCE_SANS_PRO;
                break;
            case FONTSTYLE_TITILLIUM:
                sFontStyleBold = FONTSTYLEBOLD_TITILLIUM;
                break;
        }

        sFontStyle = fontStyle;
    }

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

    public static void SaveSettings(String activityName, int storyId, int pageNumber) {
        SharedPreferences settings = MyBookshelfActivity.CONTEXT_NAME.getSharedPreferences(MyBookshelfActivity.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString("Activity", activityName);

        if (activityName.equals(ACTIVITY_MAIN)) {
            editor.putBoolean("isList", MyBookshelfActivity.isList);
        }

        if(storyId > 0) {
            editor.putInt("Story", storyId);
        }

        if(pageNumber != -1) {
            editor.putInt("Page", pageNumber);
        }

        // Commit edits
        editor.apply();
    }

    public static void ChangeStoryText(Activity activity, TextView choiceDestination, CustomTextView choiceText) {
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/" + sFontStyle);

        if(choiceText != null && choiceDestination != null) {
            //change textsize to match preferences
            choiceText.setTextSize(sTextSize);

            //change typeface to match preferences
            if(choiceDestination.getText().equals("-1")) {
                choiceText.setTypeface(typeface);
            } else {
                choiceText.setTypeface(typeface, Typeface.BOLD);
            }
        }
    }

    public static int getActionBarHeight(Context context) {
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return -1;
    }

    public static int clamp(double min, double val, double max) {
        return (int) Math.max(min, Math.min(max, val));
    }

    public static void UpdateProgress(int storyId, int currentPage) {
        BookshelfDBHelper helper = new BookshelfDBHelper(MyBookshelfActivity.CONTEXT_NAME);

        helper.updateProgress(storyId, currentPage);
        helper.closeDB();
    }
}
