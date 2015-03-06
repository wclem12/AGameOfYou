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
    public static Story story;
    public static SharedPreferences settings;
    public static boolean isList;
    public static ParseMenuXML menuParser;

    private GridView menuItems;

    public static final String PREFS_NAME = "settings";

    private static final String MANIFEST_FILENAME = "story_manifest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PACKAGE_NAME = getApplicationContext().getPackageName();
        CONTEXT_NAME = getApplicationContext();

        loadSettings();
    }

    /**
     * Load shared prefs
     */
    private void loadSettings() {
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        String currentActivity = settings.getString("Activity", Utils.ACTIVITY_MAIN);

        float textSizeFloat = settings.getFloat("textsize", Utils.TEXTSIZE_MEDIUM);
        String fontStyleStr = settings.getString("fontstyle", Utils.FONTSTYLE_ROBOTO);
        Utils.sTheme = settings.getString("theme", Utils.THEME_LIGHT);
        isList = settings.getBoolean("isList", true);

        Utils.changeTextSize(textSizeFloat);
        Utils.changeFontStyle(fontStyleStr);
        Utils.onActivityCreateSetTheme(this);

        setContentView(R.layout.main_menu);

        loadData();

        if (!currentActivity.equals(Utils.ACTIVITY_MAIN)) {
            String currentStory = settings.getString("Story", "");

            if (currentStory != null && !currentStory.isEmpty()) {
                if (currentActivity.equals(Utils.ACTIVITY_TITLE)) {
                    //load title page
                    menuParser.loadStory(currentStory);

                } else if (currentActivity.equals(Utils.ACTIVITY_STORY)) {
                    int currentPage = settings.getInt("Page", 1);
                    menuParser.loadStory(currentStory, currentPage);
                }
            }
        }
    }

    /**
     * Load xml data
     */
    private void loadData() {
        int resourceFilePath = getApplicationContext().getResources().getIdentifier(MANIFEST_FILENAME, "raw", PACKAGE_NAME);
        InputStream inputStream = getResources().openRawResource(resourceFilePath);
        menuParser = new ParseMenuXML();
        menuParser.execute(inputStream);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        if (!isList) {
            menu.findItem(R.id.action_layout).setIcon(R.drawable.ic_view_as_list);
        } else {
            menu.findItem(R.id.action_layout).setIcon(R.drawable.ic_view_as_grid);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.action_settings:
                Bundle extra = new Bundle();
                extra.putSerializable("Activity", Utils.ACTIVITY_MAIN);

                intent = new Intent(MainMenuActivity.CONTEXT_NAME, SettingsActivity.class);
                intent.putExtra("extra", extra);
                startActivity(intent);
                return true;
            case R.id.action_layout:
                setViewButton(item);
                return true;
            case R.id.action_about:
                intent = new Intent(MainMenuActivity.CONTEXT_NAME, AboutActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setViewButton(android.view.MenuItem menuItem) {
        if (isList) {
            menuItems.setNumColumns(3);
            menuItem.setIcon(R.drawable.ic_view_as_list);
        } else {
            menuItems.setNumColumns(1);
            menuItem.setIcon(R.drawable.ic_view_as_grid);
        }

        isList = !isList;

        Utils.SaveSettings(Utils.ACTIVITY_MAIN, null, -1);
    }

    @Override
    public void onBackPressed() {
        Utils.SaveSettings(Utils.ACTIVITY_MAIN, null, -1);

        super.onBackPressed();
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

                menuItems = (GridView) findViewById(R.id.main_grid);
                menuItems.setNumColumns(1);
                menuItems.setAdapter(menuAdapter);
                menuItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String xml_id = ((TextView) view.findViewById(R.id.xml_id)).getText().toString();
                        loadStory(xml_id);
                    }
                });

                if (!isList) {
                    menuItems.setNumColumns(3);
                } else {
                    menuItems.setNumColumns(1);
                }
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

        public void loadStory(String xml_id, int currentPage){
            try {
                int resourceFilePath = getApplicationContext().getResources().getIdentifier(xml_id, "raw", PACKAGE_NAME);
                InputStream inputStream = getResources().openRawResource(resourceFilePath);

                story = new ParseStoryXML().execute(inputStream).get();

                Bundle extra = new Bundle();
                extra.putSerializable("Story", story);
                extra.putSerializable("Page", currentPage);

                Intent intent = new Intent(MainMenuActivity.CONTEXT_NAME, StoryPageActivity.class);
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
}