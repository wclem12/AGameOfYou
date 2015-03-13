package wclem12.com.agameofyou.about;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.activity.MyLibraryActivity;
import wclem12.com.agameofyou.util.Utils;

public class AboutAdapter extends ArrayAdapter<AboutItem> {
    private ArrayList<AboutItem> mObjects;

    public AboutAdapter (Context context, int textResourceId, ArrayList<AboutItem> objects) {
        super(context, textResourceId, objects);
        mObjects = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.view_about_app, null);

        }

        //Get this menu item
        AboutItem aboutItem = mObjects.get(position);

        if (aboutItem != null) {
            TextView description = (TextView) view.findViewById(R.id.description);
            TextView uri = (TextView) view.findViewById(R.id.uri);
            ImageView icon = (ImageView) view.findViewById(R.id.iconImage);

            //set text
            description.setText(aboutItem.getDescription());
            uri.setText(aboutItem.getUri());

            int color = 0;

            //set color for these texts
            switch (Utils.sTheme) {
                default:
                case Utils.THEME_LIGHT:
                    color = MyLibraryActivity.CONTEXT_NAME.getResources().getColor(android.R.color.primary_text_light);
                    break;
                case Utils.THEME_DARK:
                    color = MyLibraryActivity.CONTEXT_NAME.getResources().getColor(android.R.color.primary_text_dark);
                    break;
                case Utils.THEME_SEPIA:
                    color = MyLibraryActivity.CONTEXT_NAME.getResources().getColor(R.color.sepia_dark);
                    break;
            }

            setTextColor(description, color);

            //retrieve icon filename and set it
            String iconStr = aboutItem.getIcon();
            icon.setImageResource(getImageId(iconStr));
        }

        return view;
    }

    private void setTextColor (TextView textView, int color) {
        textView.setTextColor(color);
    }

    public int getImageId (String imageName) {
        return MyLibraryActivity.CONTEXT_NAME.getResources().getIdentifier(imageName, "drawable", MyLibraryActivity.PACKAGE_NAME);
    }
}
