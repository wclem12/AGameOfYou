package wclem12.com.agameofyou.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.menu.MenuAdapter;
import wclem12.com.agameofyou.menu.MenuItem;
import wclem12.com.agameofyou.menu.MenuParserHandler;
import wclem12.com.agameofyou.story.Story;
import wclem12.com.agameofyou.story.StoryParserHandler;
import wclem12.com.agameofyou.util.Utils;

public class MainMenuActivity extends BaseActivity {
    public static String PACKAGE_NAME;
    public static Context CONTEXT_NAME;

    private static String MANIFEST_FILENAME;

    public static Story story;

    public static final String PREFS_NAME = "settings";
    public static ParseMenuXML menuParser;

    private static boolean isList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * Establish shared prefs
         */
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String currentActivity = settings.getString("Activity", "");

        float textSizeFloat = settings.getFloat("textsize", Utils.TEXTSIZE_MEDIUM);
        String fontStyleStr = settings.getString("fontstyle", Utils.FONTSTYLE_ROBOTO);
        String themeStr = settings.getString("theme", Utils.THEME_LIGHT);

        Utils.changeTextSize(this, textSizeFloat, "main_menu");
        Utils.changeFontStyle(this, fontStyleStr, "main_menu");
        Utils.sTheme = themeStr;
        Utils.onActivityCreateSetTheme(this);

        isList = settings.getBoolean("isList", true);

        setContentView(R.layout.main_menu);

        /*
         * Load XML Menu
         */
        PACKAGE_NAME = getApplicationContext().getPackageName();
        CONTEXT_NAME = getApplicationContext();

        MANIFEST_FILENAME = getString(R.string.manifest_filename);

        int resourceFilePath = getApplicationContext().getResources().getIdentifier(MANIFEST_FILENAME, "raw", PACKAGE_NAME);
        InputStream inputStream = getResources().openRawResource(resourceFilePath);
        menuParser = new ParseMenuXML();
        menuParser.execute(inputStream);

        /*
        if (!currentActivity.equals("main_menu")) {
            String currentStory = settings.getString("story", "");

            if (!currentStory.equals("")) {
                if (currentActivity.equals("title_page")) {
                    //load title page
                    menuParser.loadStory(currentStory);

                } else if (currentActivity.equals("story_page")) {
                    int currentPage = settings.getInt("page", 1);
                    menuParser.loadStory(currentStory);

                    //load StoryPageActivity intent, starting with page 1
                    Bundle extra = new Bundle();
                    extra.putSerializable("Story", story);
                    extra.putSerializable("Page", currentPage);

                    Intent intent = new Intent(MainMenuActivity.CONTEXT_NAME, StoryPageActivity.class);
                    intent.putExtra("extra", extra);
                    startActivity(intent);
                }
            }
        }
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                Bundle extra = new Bundle();
                extra.putSerializable("Activity", "main_menu");

                Intent intent = new Intent(MainMenuActivity.CONTEXT_NAME, SettingsActivity.class);
                intent.putExtra("extra", extra);
                startActivity(intent);
                return true;
            case R.id.action_layout:
                setViewButton(item);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setViewButton(android.view.MenuItem menuItem) {
        GridView menuItems = (GridView) findViewById(R.id.main_grid);

        if (isList) {
            menuItems.setNumColumns(3);
            menuItem.setIcon(R.drawable.ic_view_as_list);
        } else {
            menuItems.setNumColumns(1);
            menuItem.setIcon(R.drawable.ic_view_as_grid);
        }

        isList = !isList;
    }

    private class ParseMenuXML extends AsyncTask<InputStream, Void, Void> {
        @Override
        protected Void doInBackground(InputStream... params) {

            try {
                InputStream inputStream = params[0];
                SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
                SAXParser saxParser = saxParserFactory.newSAXParser();
                XMLReader xmlReader = saxParser.getXMLReader();

                MenuParserHandler handler = new MenuParserHandler();
                xmlReader.setContentHandler(handler);
                InputSource inputSource = new InputSource(inputStream);
                xmlReader.parse(inputSource);

                ArrayList<MenuItem> menuItemList = handler.getMenuItemsList();

                MenuAdapter menuAdapter = new MenuAdapter(CONTEXT_NAME, R.layout.menu_item, menuItemList);

                GridView gridView = (GridView) findViewById(R.id.main_grid);
                gridView.setNumColumns(1);
                gridView.setAdapter(menuAdapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String xml_id = ((TextView) view.findViewById(R.id.xml_id)).getText().toString();
                        loadStory(xml_id);
                    }
                });

            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        public void loadStory(String xml_id){
            try {
                int resourceFilePath = getApplicationContext().getResources().getIdentifier(xml_id, "raw", PACKAGE_NAME);
                InputStream inputStream = getResources().openRawResource(resourceFilePath);

                story = new ParseStoryXML().execute(inputStream).get();

                Bundle extra = new Bundle();
                extra.putSerializable("Story", story);

                Intent intent = new Intent(CONTEXT_NAME, TitlePageActivity.class);
                intent.putExtra("extra", extra);
                startActivity(intent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private class ParseStoryXML extends AsyncTask<InputStream, Void, Story> {
        private Story story = null;

        @Override
        protected Story doInBackground(InputStream... params) {

            try {
                InputStream inputStream = params[0];
                SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
                SAXParser saxParser = saxParserFactory.newSAXParser();
                XMLReader xmlReader = saxParser.getXMLReader();

                StoryParserHandler handler = new StoryParserHandler();
                xmlReader.setContentHandler(handler);
                InputSource inputSource = new InputSource(inputStream);
                xmlReader.parse(inputSource);

                story = handler.getStory();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return story;
        }
    }

    private void saveSettings() {
        SharedPreferences settings = getSharedPreferences(MainMenuActivity.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("activity", "main_menu");
        editor.putBoolean("isList", isList);

        // Commit edits
        editor.commit();
    }
}