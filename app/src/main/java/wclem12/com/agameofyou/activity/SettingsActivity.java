package wclem12.com.agameofyou.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.util.Utils;

public class SettingsActivity extends BaseActivity {
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

        //Add up navigation to this fragment
        getActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.settings_page);

        //get calling activity
        Bundle extra = getIntent().getBundleExtra("extra");
        callingActivity = extra.getString("Activity");

        //get sharedprefs
        SharedPreferences settings = getSharedPreferences(MainMenuActivity.PREFS_NAME, Context.MODE_PRIVATE);

        textSizePos = settings.getInt("textSizePos", 1);
        fontStylePos = settings.getInt("fontStylePos", 6);
        themePos = settings.getInt("themePos", 0);

        initializeSpinners();
    }

    private void initializeSpinners() {
        initializeTextSizeSpinner();
        initializeFontStyleSpinner();
        initializeThemeSpinner();
    }

    private void initializeTextSizeSpinner() {
        final Spinner textSizeSpinner = (Spinner) findViewById(R.id.textSizeSpinner);
        textSizeSpinner.setAdapter(new TextSizeSpinnerAdapter(this, R.layout.settings_textsize, textSizeValues));
        textSizeSpinner.setSelection(textSizePos); //init to medium
        textSizeSpinner.post(new Runnable() {
            @Override
            public void run() {
                textSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

                        Utils.changeTextSize(activity, textSizeFloat, callingActivity);
                        textSizePos = position;

                        saveSettings();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });

    }

    private void initializeFontStyleSpinner() {
        final Spinner fontStyleSpinner = (Spinner) findViewById(R.id.fontStyleSpinner);
        fontStyleSpinner.setAdapter(new FontStyleSpinnerAdapter(this, R.layout.settings_textsize, fontStyleValues));
        fontStyleSpinner.setSelection(fontStylePos);
        fontStyleSpinner.post(new Runnable() {
            @Override
            public void run() {
                fontStyleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

                        Utils.changeFontStyle(activity, fontStyleStr, callingActivity);
                        fontStylePos = position;

                        saveSettings();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });

    }

    private void initializeThemeSpinner() {
        final Spinner themeSpinner = (Spinner) findViewById(R.id.themeSpinner);
        themeSpinner.setAdapter(new ThemeSpinnerAdapter(this, R.layout.settings_textsize, themeValues));
        themeSpinner.setSelection(themePos);
        themeSpinner.post(new Runnable() {
            @Override
            public void run() {
                themeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                });
            }
        });
    }

    public class TextSizeSpinnerAdapter extends ArrayAdapter<String> {
        public TextSizeSpinnerAdapter(Context context, int txtViewResourceId, String[] objects) {
            super(context, txtViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

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

    public class FontStyleSpinnerAdapter extends ArrayAdapter<String> {
        public FontStyleSpinnerAdapter(Context context, int txtViewResourceId, String[] objects) {
            super(context, txtViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

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

    public class ThemeSpinnerAdapter extends ArrayAdapter<String> {
        public ThemeSpinnerAdapter(Context context, int txtViewResourceId, String[] objects) {
            super(context, txtViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

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
    public void onStop() {
        getActionBar().setDisplayHomeAsUpEnabled(false);

        super.onStop();
    }

    private void saveSettings() {
        SharedPreferences settings = getSharedPreferences(MainMenuActivity.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat("textsize", Utils.sTextSize);
        editor.putInt("textSizePos", textSizePos);

        editor.putString("fontstyle", Utils.sFontStyle);
        editor.putInt("fontStylePos", fontStylePos);

        editor.putString("theme", Utils.sTheme);
        editor.putInt("themePos", themePos);

        // Commit edits
//       editor.commit();
        editor.apply();
    }

}
