package wclem12.com.agameofyou.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.about.AboutAdapter;
import wclem12.com.agameofyou.about.AboutItem;

public class AboutActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Add up navigation to this fragment
        getActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_about_app);

        loadAbout();
    }

    private void loadAbout() {
        //go through Array of About Items, create AboutItem objects for each
        String[] aboutStrings = { "Share A Game of You", "Help and Support", "Tutorial", "Privacy Policy",
                                    "Terms of Service" };

        String[] uriStrings = { "null", "null", "null", "null", "null" };

        String[] iconStrings = { "ic_share", "ic_support", "ic_tutorial", "ic_privacy_and_terms",
                                    "ic_privacy_and_terms"};

        ArrayList<AboutItem> aboutItemList = new ArrayList<>();

        for(int i = 0; i < aboutStrings.length; i++) {
            AboutItem aboutItem = new AboutItem();
            aboutItem.setDescription(aboutStrings[i]);
            aboutItem.setUri(uriStrings[i]);
            aboutItem.setIcon(iconStrings[i]);

            aboutItemList.add(aboutItem);
        }

        AboutAdapter aboutAdapter = new AboutAdapter(MyLibraryActivity.CONTEXT_NAME, R.layout.view_about_app, aboutItemList);

        View footer = getLayoutInflater().inflate(R.layout.about_page_footer, null);

        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.addFooterView(footer, null, true);

        TextView appVersion = (TextView) findViewById(R.id.app_version);

        try {
            appVersion.setText("Version " + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        listView.setAdapter(aboutAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: Handle URI and clicks
            }
        });
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
}
