package wclem12.com.agameofyou.about_app;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.activity.BaseActivity;
import wclem12.com.agameofyou.util.BaseRecyclerAdapter;
import wclem12.com.agameofyou.util.DividerItemDecoration;

public class AboutAppActivity extends BaseActivity {
    @InjectView(R.id.about_app_list) public RecyclerView aboutAppRV;

    private ArrayList<AboutAppItem> aboutAppItemList = new ArrayList<>();
    private String[] aboutStrings = { "Share A Game of You", "Help and Support", "Tutorial", "Privacy Policy",
            "Terms of Service", "Version "};

    private String[] uriStrings = { "null", "null", "null", "null", "null", "null"};

    private String[] iconStrings = { "ic_share", "ic_support", "ic_tutorial", "ic_privacy_and_terms",
            "ic_privacy_and_terms", "ic_share"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Add up navigation to this fragment
        getActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_about_app);

        ButterKnife.inject(this);

        try {
            aboutStrings[5] += getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < aboutStrings.length; i++) {
            AboutAppItem aboutAppItem = new AboutAppItem(aboutStrings[i], uriStrings[i],
                                                            iconStrings[i]);
            aboutAppItemList.add(aboutAppItem);
        }

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        aboutAppRV.setLayoutManager(layoutManager);
        aboutAppRV.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        AboutAppRecyclerAdapter aboutAppAdapter = new AboutAppRecyclerAdapter(aboutItemClick, aboutItemLongClick);
        aboutAppRV.setAdapter(aboutAppAdapter);
    }

    View.OnClickListener aboutItemClick = new View.OnClickListener() {
        @Override public void onClick(View v) {
            click(v);
        }
    };

    View.OnLongClickListener aboutItemLongClick = new View.OnLongClickListener() {
        @Override public boolean onLongClick(View v) {
            click(v);
            return true;
        }
    };

    private void click(View view) {
        //TODO: Handle URI and clicks
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

    class AboutAppRecyclerAdapter extends BaseRecyclerAdapter<AboutAppItem> {

        public AboutAppRecyclerAdapter(View.OnClickListener clickListener, View.OnLongClickListener longClickListener) {
            super(AboutAppActivity.this, clickListener, longClickListener);
            this.items = aboutAppItemList;
        }

        @Override public View newView(ViewGroup container) {
            return inflater.inflate(R.layout.view_about_app, container, false);
        }

        @Override public void bindView(AboutAppItem item, int position, View view) {
            AboutAppView aboutAppView = (AboutAppView) view;
            aboutAppView.bindTo(item);
        }
    }
}
