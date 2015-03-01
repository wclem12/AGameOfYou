package wclem12.com.agameofyou.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.activity.MainMenuActivity;
import wclem12.com.agameofyou.util.Utils;

public class MenuAdapter extends ArrayAdapter<MenuItem> {
    private ArrayList<MenuItem> mObjects;

    public MenuAdapter (Context context, int textResourceId, ArrayList<MenuItem> objects) {
        super(context, textResourceId, objects);
        mObjects = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.menu_item, null);

        }

        //Get this menu item
        MenuItem menuItem = mObjects.get(position);

        if (menuItem != null) {
            TextView title = (TextView) view.findViewById(R.id.title);
            TextView author = (TextView) view.findViewById(R.id.author);
            TextView xml_id = (TextView) view.findViewById(R.id.xml_id);
            ImageView cover = (ImageView) view.findViewById(R.id.coverImage);

            //set text
            title.setText(menuItem.getTitle());
            author.setText(menuItem.getAuthor());
            xml_id.setText(menuItem.getXmlID());

            int color = 0;

            //set color for these texts
            switch (Utils.sTheme) {
                default:
                case Utils.THEME_LIGHT:
                    color = MainMenuActivity.CONTEXT_NAME.getResources().getColor(android.R.color.primary_text_light);
                    break;
                case Utils.THEME_DARK:
                    color = MainMenuActivity.CONTEXT_NAME.getResources().getColor(android.R.color.primary_text_dark);
                    break;
                case Utils.THEME_SEPIA:
                    color = MainMenuActivity.CONTEXT_NAME.getResources().getColor(R.color.sepia_dark);
                    break;
            }

            setTextColor(title, color);
            setTextColor(author, color);

            //retrieve cover filename and set cover
            String coverStr = menuItem.getCover();
            cover.setImageResource(getImageId(coverStr));
        }

        return view;
    }

    private void setTextColor (TextView textView, int color) {
        textView.setTextColor(color);
    }

    public int getImageId (String imageName) {
        return MainMenuActivity.CONTEXT_NAME.getResources().getIdentifier(imageName, "drawable", MainMenuActivity.PACKAGE_NAME);
    }
}