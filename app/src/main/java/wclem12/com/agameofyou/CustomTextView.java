package wclem12.com.agameofyou;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import wclem12.com.agameofyou.util.Utils;

public class CustomTextView extends TextView {

    public CustomTextView(Context context) {
        super(context);
        init(context, null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init (Context context, AttributeSet attrs) {
        setTextSize(TypedValue.COMPLEX_UNIT_SP, Utils.sTextSize);
    }
}
