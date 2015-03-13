package wclem12.com.agameofyou.activity;

import android.app.Activity;
import android.os.Bundle;

import wclem12.com.agameofyou.util.Utils;

public abstract class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
    }
}