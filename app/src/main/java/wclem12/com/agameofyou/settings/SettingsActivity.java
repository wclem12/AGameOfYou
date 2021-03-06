package wclem12.com.agameofyou.settings;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.activity.BaseActivity;
import wclem12.com.agameofyou.activity.MyBookshelfActivity;
import wclem12.com.agameofyou.util.CustomTextView;
import wclem12.com.agameofyou.util.Utils;

public class SettingsActivity extends BaseActivity {
    @InjectView(R.id.toolbar) public Toolbar toolbar;
    @InjectView(R.id.textSizeSpinner) public Spinner textSizeSpinner;
    @InjectView(R.id.fontStyleSpinner) public Spinner fontStyleSpinner;
    @InjectView(R.id.themeSpinner) public Spinner themeSpinner;


    private String callingActivity = "";
    private float textSizeFloat;
    private String fontStyleStr;
    private String themeStr;

    private String[] textSizeValues = { "Small", "Medium", "Large" };
    private String[] fontStyleValues = { "Caviar Dreams", "Droid Serif", "Exo", "Libre Baskerville",
            "Quicksand", "Raleway", "Roboto", "Source Sans Pro", "Titillium"};
    private String[] themeValues = { "Light", "Dark", "Sepia"};

    private final Activity activity = this;
    private int textSizePos = 1;
    private int fontStylePos = 6;
    private int themePos = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get calling activity
        Bundle extra = getIntent().getBundleExtra("extra");
        callingActivity = extra.getString("Activity");

        //get sharedprefs
        SharedPreferences settings = getSharedPreferences(MyBookshelfActivity.PREFS_NAME, Context.MODE_PRIVATE);

        textSizePos = settings.getInt("textSizePos", 1);
        fontStylePos = settings.getInt("fontStylePos", 6);
        themePos = settings.getInt("themePos", 0);

        initializeSpinners();
    }

    private void initializeSpinners() {
        //for now, assign arbitrary values to spinners
        //TextSize = 0, FontStyle = 1, ThemeSpinner = 2

        for(int i = 0; i <=2; i++) {
            setupSpinner(i);
        }
    }


    private void setupSpinner(int spinInt) {
        final Spinner spinner;
        int layoutId, startPos = 0;
        String[] values;
        BaseSpinnerAdapter adapter;
        AdapterView.OnItemSelectedListener listener = null;

        switch(spinInt) {
            default:
            case 0:
                spinner = textSizeSpinner;
                layoutId = R.layout.settings_textsize;
                startPos = textSizePos;
                values = textSizeValues;
                adapter = new TextSizeSpinnerAdapter(this, layoutId, values);
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //Add functionality to font size
                        switch (position) {
                            case 0:
                                textSizeFloat = Utils.TEXTSIZE_SMALL; //small
                                break;
                            default:
                            case 1:
                                textSizeFloat = Utils.TEXTSIZE_MEDIUM; //medium
                                break;
                            case 2:
                                textSizeFloat = Utils.TEXTSIZE_LARGE; //large
                                break;
                        }

                        Utils.changeTextSize(textSizeFloat);
                        textSizePos = position;

                        saveSettings();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                };
                break;
            case 1:
                spinner = fontStyleSpinner;
                layoutId = R.layout.settings_fontstyle;
                startPos = fontStylePos;
                values = fontStyleValues;
                adapter = new FontStyleSpinnerAdapter(this, layoutId, values);
                listener = new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //Add functionality to font style
                        switch (position) {
                            case 0:
                                fontStyleStr = Utils.FONTSTYLE_CAVIAR_DREAMS; //"Caviar Dreams"
                                break;
                            case 1:
                                fontStyleStr = Utils.FONTSTYLE_DROID_SERIF; //"Droid Serif"
                                break;
                            case 2:
                                fontStyleStr = Utils.FONTSTYLE_EXO; //"Exo"
                                break;
                            case 3:
                                fontStyleStr = Utils.FONTSTYLE_LIBRE_BASKERVILLE; //"Libre Baskerville"
                                break;
                            case 4:
                                fontStyleStr = Utils.FONTSTYLE_QUICKSAND; //"Quicksand"
                                break;
                            case 5:
                                fontStyleStr = Utils.FONTSTYLE_RALEWAY; //"Raleway"
                                break;
                            default:
                            case 6:
                                fontStyleStr = Utils.FONTSTYLE_ROBOTO; //"Roboto"
                                break;
                            case 7:
                                fontStyleStr = Utils.FONTSTYLE_SOURCE_SANS_PRO; //"Source Sans Pro"
                                break;
                            case 8:
                                fontStyleStr = Utils.FONTSTYLE_TITILLIUM; //"Titillium"
                                break;
                        }

                        Utils.changeFontStyle(fontStyleStr);
                        fontStylePos = position;

                        saveSettings();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                };
                break;
            case 2:
                spinner = themeSpinner;
                layoutId = R.layout.settings_theme;
                startPos = themePos;
                values = themeValues;
                adapter = new ThemeSpinnerAdapter(this, layoutId, values);
                listener = new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //Add functionality to app theme
                        switch (position) {
                            default:
                            case 0:
                                themeStr = Utils.THEME_LIGHT; //Light
                                break;
                            case 1:
                                themeStr = Utils.THEME_DARK; //Dark
                                break;
                            case 2:
                                themeStr = Utils.THEME_SEPIA; //Sepia
                                break;
                        }

                        Utils.changeToTheme(activity, themeStr);
                        themePos = position;

                        saveSettings();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                };
                break;
        }


        spinner.setAdapter(adapter);
        spinner.setSelection(startPos);
        final AdapterView.OnItemSelectedListener finalListener = listener;
        spinner.post(new Runnable() {
            @Override
            public void run() {
                spinner.setOnItemSelectedListener(finalListener);
            }
        });
    }

    public class TextSizeSpinnerAdapter extends BaseSpinnerAdapter {
        public TextSizeSpinnerAdapter(Context context, int txtViewResourceId, String[] objects) {
            super(context, txtViewResourceId, objects);
        }

       @Override
        public View getCustomView(int position, View convertView, ViewGroup parent) {
            View view = convertView;

            if(view == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.settings_textsize, parent, false);
            }

            TextView textSizeView = (TextView) view.findViewById(R.id.textsize_item);

            String size = textSizeValues[position];
            float textSize;

            switch (size) {
                default:
                case "Small":
                    textSize = 14;
                    break;
                case "Medium":
                    textSize = 18;
                    break;
                case "Large":
                    textSize = 22;
                    break;
            }

            textSizeView.setText(size);
            textSizeView.setTextSize(textSize);

            return view;
        }
    }

    public class FontStyleSpinnerAdapter extends BaseSpinnerAdapter {
        public FontStyleSpinnerAdapter(Context context, int txtViewResourceId, String[] objects) {
            super(context, txtViewResourceId, objects);
        }

        @Override
        public View getCustomView(int position, View convertView, ViewGroup parent) {
            View view = convertView;

            if(view == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.settings_fontstyle, parent, false);
            }

            TextView fontStyleText = (TextView) view.findViewById(R.id.fontstyle_item);

            String style = fontStyleValues[position];
            String fontStyle;

            switch (style) {
                case "Caviar Dreams":
                    fontStyle = Utils.FONTSTYLE_CAVIAR_DREAMS;
                    break;
                case "Droid Serif":
                    fontStyle = Utils.FONTSTYLE_DROID_SERIF;
                    break;
                case "Exo":
                    fontStyle = Utils.FONTSTYLE_EXO;
                    break;
                case "Libre Baskerville":
                    fontStyle = Utils.FONTSTYLE_LIBRE_BASKERVILLE;
                    break;
                case "Quicksand":
                    fontStyle = Utils.FONTSTYLE_QUICKSAND;
                    break;
                case "Raleway":
                    fontStyle = Utils.FONTSTYLE_RALEWAY;
                    break;
                default:
                case "Roboto":
                    fontStyle = Utils.FONTSTYLE_ROBOTO;
                    break;
                case "Source Sans Pro":
                    fontStyle = Utils.FONTSTYLE_SOURCE_SANS_PRO;
                    break;
                case "Titillium":
                    fontStyle = Utils.FONTSTYLE_TITILLIUM;
                    break;
            }

            Typeface typeface = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/" + fontStyle);
            fontStyleText.setText(fontStyleValues[position]);
            fontStyleText.setTypeface(typeface);

            return view;
        }
    }

    public class ThemeSpinnerAdapter extends BaseSpinnerAdapter {
        public ThemeSpinnerAdapter(Context context, int txtViewResourceId, String[] objects) {
            super(context, txtViewResourceId, objects);
        }

        @Override
        public View getCustomView(int position, View convertView, ViewGroup parent) {
            View view = convertView;

            if(view == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.settings_theme, parent, false);
            }

            TextView themeText = (TextView) view.findViewById(R.id.theme_item);
            TextView themeDisplayText = (TextView) view.findViewById(R.id.theme_display);
            LinearLayout themeDisplayLayout = (LinearLayout) view.findViewById(R.id.theme_display_layout);

            String theme = themeValues[position];

            int backgroundColor;
            int textColor;

            switch (theme) {
                default:
                case "Light":
                    backgroundColor = view.getResources().getColor(R.color.light_background);
                    textColor = view.getResources().getColor(R.color.light_text);
                    break;
                case "Dark":
                    backgroundColor = view.getResources().getColor(R.color.dark_background);
                    textColor = view.getResources().getColor(R.color.dark_text);
                    break;
                case "Sepia":
                    backgroundColor = view.getResources().getColor(R.color.sepia_light);
                    textColor = view.getResources().getColor(R.color.sepia_dark);
                    break;
            }

            themeText.setText(themeValues[position]);
            themeDisplayLayout.setBackgroundColor(backgroundColor);
            themeDisplayText.setTextColor(textColor);
            themeDisplayText.setBackgroundColor(backgroundColor);

            return view;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(callingActivity.equals(Utils.ACTIVITY_STORY)){
            TextView storyPageButtonDestination = (TextView) activity.findViewById(R.id.page_choice_destination);
            CustomTextView storyPageButtonText = (CustomTextView) activity.findViewById(R.id.page_choice_text);

            Utils.ChangeStoryText(this, storyPageButtonDestination, storyPageButtonText);
        }

        super.onBackPressed();
    }

    @Override
    public void onStop() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        super.onStop();
    }

    private void saveSettings() {
        SharedPreferences settings = getSharedPreferences(MyBookshelfActivity.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat("textsize", Utils.sTextSize);
        editor.putInt("textSizePos", textSizePos);

        editor.putString("fontstyle", Utils.sFontStyle);
        editor.putInt("fontStylePos", fontStylePos);

        editor.putString("theme", Utils.sTheme);
        editor.putInt("themePos", themePos);

        // Commit edits
        editor.apply();
    }
}
