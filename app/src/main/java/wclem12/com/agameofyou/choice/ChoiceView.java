package wclem12.com.agameofyou.choice;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.activity.MyBookshelfActivity;
import wclem12.com.agameofyou.util.CustomTextView;
import wclem12.com.agameofyou.util.Utils;

public class ChoiceView extends LinearLayout {
    @InjectView(R.id.page_choice_text) CustomTextView choiceText;
    @InjectView(R.id.page_choice_destination) TextView choiceDest;

    public Choice choice;

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
    }

    public ChoiceView(Context context, AttributeSet attrs) { super(context, attrs); }

    public void bindTo(final Choice choice) {
        this.choice = choice;

        choiceText.setText(choice.getText());
        choiceDest.setText(String.valueOf(choice.getDestination()));

        choiceText.setTextSize(TypedValue.COMPLEX_UNIT_SP, Utils.sTextSize);

        Typeface typeface = Typeface.createFromAsset(MyBookshelfActivity.CONTEXT_NAME.getAssets(), "fonts/" + Utils.sFontStyle);

        if(choiceDest.getText().equals("-1")) {
            choiceText.setTypeface(typeface);
            choiceText.setGravity(Gravity.NO_GRAVITY);
        } else {
            choiceText.setTypeface(typeface, Typeface.BOLD);
            choiceText.setGravity(Gravity.CENTER);
        }
    }
}
