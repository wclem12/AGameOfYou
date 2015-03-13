package wclem12.com.agameofyou.util;

import android.content.Context;
import android.graphics.Typeface;
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

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init (Context context, AttributeSet attrs) {
        setTextSize(TypedValue.COMPLEX_UNIT_SP, Utils.sTextSize);
    }

    @Override
    public void setTypeface(Typeface tf, int style) {
        Typeface normalTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + Utils.sFontStyle);
        Typeface boldTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + Utils.sFontStyleBold);

        if (style == Typeface.BOLD) {
            super.setTypeface(boldTypeface/*, -1*/);
        } else {
            super.setTypeface(normalTypeface/*, -1*/);
        }
    }
}
