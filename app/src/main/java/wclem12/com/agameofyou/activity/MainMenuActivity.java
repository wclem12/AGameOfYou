package wclem12.com.agameofyou.activity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.activity.fragment.SettingsFragment;
import wclem12.com.agameofyou.menu.MenuAdapter;
import wclem12.com.agameofyou.menu.MenuParserHandler;
import wclem12.com.agameofyou.story.Story;
import wclem12.com.agameofyou.story.StoryParserHandler;
import wclem12.com.agameofyou.util.Utils;

public class MainMenuActivity extends BaseActivity {
    public static String PACKAGE_NAME;
    public static Context CONTEXT_NAME;

    private static String KEY_TITLE;
    private static String KEY_AUTHOR;
    private static String KEY_XML_ID;
    private static String MANIFEST_FILENAME;

    public static Story story;

    public static final String PREFS_NAME = "settings";
    public static ParseMenuXML menuParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * Establish shared prefs
         */
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String currentActivity = settings.getString("activity", "");

        String themeStr = settings.getString("theme", Utils.THEME_LIGHT);
        float fontSizeFloat = settings.getFloat("font", Utils.TEXTSIZE_MEDIUM);

        Utils.changeTextSize(this, fontSizeFloat, "main_menu");
        Utils.sTheme = themeStr;
        Utils.onActivityCreateSetTheme(this);

        setContentView(R.layout.main_menu);

        /*
         * Load XML Menu
         */
        PACKAGE_NAME = getApplicationContext().getPackageName();
        CONTEXT_NAME = getApplicationContext();

        KEY_TITLE = getString(R.string.manifest_title_tag);
        KEY_AUTHOR = getString(R.string.manifest_author_tag);
        KEY_XML_ID = getString(R.string.manifest_xml_id_tag);

        MANIFEST_FILENAME = getString(R.string.manifest_filename);

        int resourceFilePath = getApplicationContext().getResources().getIdentifier(MANIFEST_FILENAME, "raw", PACKAGE_NAME);
        InputStream inputStream = getResources().openRawResource(resourceFilePath);
        menuParser = new ParseMenuXML();
        menuParser.execute(inputStream);

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

        /*
        * TODO in future releases:
        *-Saving progress for individual stories, much like Kindle does
        *-Consider special customer adapter for Settings and other menu items a la CustomTextView to
        * get the colors right.
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
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(android.R.id.content, new SettingsFragment());
                fragmentTransaction.addToBackStack("settings");
                fragmentTransaction.commit();
                return true;
        }

        return super.onOptionsItemSelected(item);
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

                ArrayList<HashMap<String, String>> menuItemList = handler.getMenuItemsList();

                ListAdapter adapter = new MenuAdapter(CONTEXT_NAME, menuItemList, R.layout.menu_item, new String[]{KEY_TITLE, KEY_AUTHOR, KEY_XML_ID}, new int[]{R.id.title, R.id.author, R.id.xml_id});

                ListView listView = (ListView) findViewById(android.R.id.list);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        // Commit edits
        editor.commit();
    }
}