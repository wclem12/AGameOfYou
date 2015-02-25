package wclem12.com.agameofyou.menu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.activity.MainMenuActivity;
import wclem12.com.agameofyou.util.Utils;

public class MenuAdapter extends SimpleAdapter{
    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */
    private Context mContext;

    public MenuAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);

        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        Object item = getItem(position);

        TextView title = (TextView) view.findViewById(R.id.title);
        TextView author = (TextView) view.findViewById(R.id.author);

        switch (Utils.sTheme) {
            default:
            case Utils.THEME_LIGHT:

                title.setTextColor(MainMenuActivity.CONTEXT_NAME.getResources().getColor(android.R.color.primary_text_light));
                author.setTextColor(MainMenuActivity.CONTEXT_NAME.getResources().getColor(android.R.color.primary_text_light));
                break;
            case Utils.THEME_DARK:
                title.setTextColor(MainMenuActivity.CONTEXT_NAME.getResources().getColor(android.R.color.primary_text_dark));
                author.setTextColor(MainMenuActivity.CONTEXT_NAME.getResources().getColor(android.R.color.primary_text_dark));
                break;
            case Utils.THEME_SEPIA:
                title.setTextColor(MainMenuActivity.CONTEXT_NAME.getResources().getColor(R.color.sepia_dark));
                author.setTextColor(MainMenuActivity.CONTEXT_NAME.getResources().getColor(R.color.sepia_dark));
                break;
        }

        return view;
    }
}
