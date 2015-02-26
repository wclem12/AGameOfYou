package wclem12.com.agameofyou.activity.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.activity.MainMenuActivity;
import wclem12.com.agameofyou.util.Utils;

public class SettingsFragment extends PreferenceFragment {
    private String callingActivity = "";
    private String themeStr;
    private float fontSizeFloat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        PreferenceManager.setDefaultValues(getActivity().getBaseContext(), R.xml.preferences, false);

        //Load preferences from XML resource
        addPreferencesFromResource(R.xml.preferences);

        //Add up navigation to this fragment
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

        //get calling activity
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            callingActivity = bundle.getString("activity");
        }

        //Add functionality to font size listpreference
        final ListPreference fontSize = (ListPreference) findPreference(getString(R.string.pref_font_size));
        fontSize.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String fontSizeStr = String.valueOf(newValue.toString());
                fontSizeFloat = Float.parseFloat(fontSizeStr);

                Utils.changeTextSize(getActivity(), fontSizeFloat, callingActivity);

                saveSettings();

                return true;
            }
        });

        //Add functionality to font style listpreference
        final ListPreference fontStyle = (ListPreference) findPreference(getString(R.string.pref_font_style));
        fontStyle.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String fontStyleStr = String.valueOf(newValue.toString());

                Utils.changeFontStyle(getActivity(), fontStyleStr, callingActivity);

                saveSettings();

                return true;
            }
        });

        //Add functionality to app theme listpreference
        final ListPreference theme = (ListPreference) findPreference(getString(R.string.pref_theme));
        theme.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                themeStr = String.valueOf(newValue.toString());

                Utils.changeToTheme(getActivity(), themeStr);

                saveSettings();

                return true;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        switch (Utils.sTheme) {
            default:
            case Utils.THEME_LIGHT:
                view.setBackgroundColor(getResources().getColor(android.R.color.background_light));
                break;
            case Utils.THEME_DARK:
                view.setBackgroundColor(getResources().getColor(android.R.color.background_dark));
                break;
            case Utils.THEME_SEPIA:
                view.setBackgroundColor(getResources().getColor(R.color.sepia_light));
                break;
        }

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getFragmentManager().popBackStack();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(false);

        super.onStop();
    }

    private void saveSettings() {
        SharedPreferences settings = getActivity().getSharedPreferences(MainMenuActivity.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("theme", Utils.sTheme);
        editor.putFloat("textsize", Utils.sTextSize);
        editor.putString("fontstyle", Utils.sFontStyle);

        // Commit edits
        editor.commit();
    }

}
