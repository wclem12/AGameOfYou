package wclem12.com.agameofyou.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

import wclem12.com.agameofyou.story.Choice;
import wclem12.com.agameofyou.story.Page;
import wclem12.com.agameofyou.story.Story;

public class LibraryDBHelper extends SQLiteAssetHelper {
    // Database information
    private static final String DATABASE_NAME = "game_of_you.sqlite";
    private static final int DATABASE_VERSION = 1;

    // Useful tags
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    // Table names
    private static final String TABLE_STORY = "story";
    private static final String TABLE_PAGE = "page";
    private static final String TABLE_CHOICE = "choice";

    // Common column names
    private static final String COLUMN_ID = "_id";

    // Story table column names
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_AUTHOR = "author";
    private static final String COLUMN_CREATE_DATE = "create_date";
    private static final String COLUMN_LAST_EDIT_DATE = "last_edit_date";
    private static final String COLUMN_CREATOR_USERNAME = "creator_username";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_TAGS = "tags";
    private static final String COLUMN_COVER = "cover";

    // Page table column names
    private static final String COLUMN_STORY_ID = "story_id";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_PAGE_NUMBER = "page_number";
    private static final String COLUMN_PAGE_TEXT = "page_text";

    // Choice table column names
    private static final String COLUMN_PAGE_ID = "page_id";
    private static final String COLUMN_DESTINATION = "destination";
    private static final String COLUMN_CHOICE_TEXT = "choice_text";

    public LibraryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public ArrayList<Story> getMyLibraryList() {
        ArrayList<Story> myLibraryList = new ArrayList<>();
        String[] sqlSelect = {COLUMN_ID, COLUMN_TITLE, COLUMN_AUTHOR, COLUMN_COVER};
        String sqlTables = TABLE_STORY;
        Cursor cursor = selectQuery(sqlSelect, sqlTables, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do {
                Story story = new Story();
                story.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                story.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                story.setAuthor(cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR)));
                story.setCover(cursor.getString(cursor.getColumnIndex(COLUMN_COVER)));

                // adding to the story list
                myLibraryList.add(story);
            } while (cursor.moveToNext());
        }

        return myLibraryList;
    }

    /**
     * Load and create story object
     * @return
     */
    public Story getStory(int id) {
        Story newStory = new Story();

        String[] sqlSelect = {COLUMN_ID, COLUMN_TITLE, COLUMN_AUTHOR, COLUMN_CREATE_DATE,
                COLUMN_LAST_EDIT_DATE, COLUMN_CREATOR_USERNAME, COLUMN_GENRE,
                COLUMN_TAGS, COLUMN_COVER};
        String sqlTables = TABLE_STORY;
        String selection = COLUMN_ID + "=" + Integer.toString(id);
        Cursor cursor = selectQuery(sqlSelect, sqlTables, selection);

        if (cursor.moveToFirst()) {
            do {
                newStory.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                newStory.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                newStory.setAuthor(cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR)));
                newStory.setCreateDate(cursor.getString(cursor.getColumnIndex(COLUMN_CREATE_DATE)));
                newStory.setLastEditDate(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_EDIT_DATE)));
                newStory.setCreatorUsername(cursor.getString(cursor.getColumnIndex(COLUMN_CREATOR_USERNAME)));
                newStory.setGenre(cursor.getString(cursor.getColumnIndex(COLUMN_GENRE)));
                newStory.setTags(cursor.getString(cursor.getColumnIndex(COLUMN_TAGS)));
                newStory.setCover(cursor.getString(cursor.getColumnIndex(COLUMN_COVER)));
            } while (cursor.moveToNext());
        }

        newStory.setPageList(getPages(newStory));

        return newStory;
    }

    /**
     * Load and create page list for selected story
     * @return
     */
    private ArrayList<Page> getPages(Story story) {
        //Load and create pages for selected story
        ArrayList<Page> pageList = new ArrayList<>();
        String[] sqlSelect = {COLUMN_ID, COLUMN_STORY_ID, COLUMN_TYPE, COLUMN_PAGE_NUMBER,
                COLUMN_PAGE_TEXT};
        String sqlTables = TABLE_PAGE;
        String selection = COLUMN_STORY_ID + "=" + Integer.toString(story.getId());
        Cursor cursor = selectQuery(sqlSelect, sqlTables, selection);

        if (cursor.moveToFirst()){
            do {
                Page newPage = new Page();
                newPage.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                newPage.setType(cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)));
                newPage.setPageNumber(cursor.getInt(cursor.getColumnIndex(COLUMN_PAGE_NUMBER)));
                newPage.setText(cursor.getString(cursor.getColumnIndex(COLUMN_PAGE_TEXT)));

                newPage.setChoiceList(getChoices(newPage));

               pageList.add(newPage);
            } while (cursor.moveToNext());
        }

        return pageList;
    }

    /**
     * Load and create choice list for selected page
     * @return
     */
    private ArrayList<Choice> getChoices(Page page) {
        ArrayList<Choice> choiceList = new ArrayList<>();
        String[] sqlSelect = {COLUMN_ID, COLUMN_PAGE_ID, COLUMN_DESTINATION, COLUMN_CHOICE_TEXT};
        String sqlTables = TABLE_CHOICE;
        String selection = COLUMN_PAGE_ID + "=" + Integer.toString(page.getId());
        Cursor cursor = selectQuery(sqlSelect, sqlTables, selection);

        if (cursor.moveToFirst()){
            do {
                Choice newChoice = new Choice();
                newChoice.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                newChoice.setDestination(cursor.getInt(cursor.getColumnIndex(COLUMN_DESTINATION)));
                newChoice.setText(cursor.getString(cursor.getColumnIndex(COLUMN_CHOICE_TEXT)));

                choiceList.add(newChoice);

            } while (cursor.moveToNext());
        }

        //special case 1: Text page
        String pageText = page.getText();
        pageText = pageText.replaceAll("\\\\n|\\\\r", System.getProperty("line.separator"));
        pageText = pageText.replaceAll("\\\\t", "    ");

        Choice newChoice = new Choice();
        newChoice.setDestination(-1);
        newChoice.setText(pageText);
        choiceList.add(0, newChoice);

        //special case 2: Last page
        if(page.getType().equals("Last")) {
            //Add restart and main menu buttons
            newChoice = new Choice();
            newChoice.setDestination(0);
            newChoice.setText("Restart Story");
            choiceList.add(newChoice);

            newChoice = new Choice();
            newChoice.setDestination(0);
            newChoice.setText("Main Menu");
            choiceList.add(newChoice);
        }

        return choiceList;
    }

    private Cursor selectQuery(String[] sqlSelect, String sqlTables, String selection) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, selection, null, null, null, null);

        return c;
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = getReadableDatabase();
        if (db != null && db.isOpen()) { db.close(); }
    }
}
