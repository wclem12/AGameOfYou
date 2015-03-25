package wclem12.com.agameofyou.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.activity.MyBookshelfActivity;
import wclem12.com.agameofyou.choice.Choice;
import wclem12.com.agameofyou.page.Page;
import wclem12.com.agameofyou.story.Story;

public class BookshelfDBHelper extends SQLiteAssetHelper {
    //Constants
    private static final String TYPE_LAST = "Last";
    private static final String TYPE_FIRST = "First";
    private static final String TYPE_NEXT = "Next";

    // Database information
    private static final String DATABASE_NAME = "game_of_you.sqlite";
    private static final int DATABASE_VERSION = 1;

    // Useful tags
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ", ";

    // Table names
    private static final String TABLE_STORY = "story";
    private static final String TABLE_PAGE = "page";
    private static final String TABLE_CHOICE = "choice";

    // Common column names
    private static final String COLUMN_ID = "_id";

    // Story table column names
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_AUTHOR = "author";
    private static final String COLUMN_SYNOPSIS = "synopsis";
    private static final String COLUMN_CREATE_DATE = "create_date";
    private static final String COLUMN_LAST_EDIT_DATE = "last_edit_date";
    private static final String COLUMN_CREATOR_USERNAME = "creator_username";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_TAGS = "tags";
    private static final String COLUMN_COVER = "cover";
    private static final String COLUMN_LANGUAGE = "language";
    private static final String COLUMN_PAGE_COUNT = "page_count";
    private static final String COLUMN_CURRENT_PAGE = "current_page";
    private static final String COLUMN_FAVORITE = "favorite";

    // Page table column names
    private static final String COLUMN_STORY_ID = "story_id";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_PAGE_NUMBER = "page_number";
    private static final String COLUMN_PAGE_TEXT = "page_text";

    // Choice table column names
    private static final String COLUMN_PAGE_ID = "page_id";
    private static final String COLUMN_DESTINATION = "destination";
    private static final String COLUMN_CHOICE_TEXT = "choice_text";

    public BookshelfDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public ArrayList<Story> getMyBookshelf(int type) {
        ArrayList<Story> myLibraryList = new ArrayList<>();
        String[] sqlSelect = { COLUMN_ID, COLUMN_TITLE, COLUMN_AUTHOR, COLUMN_COVER,
                COLUMN_PAGE_COUNT, COLUMN_CURRENT_PAGE, COLUMN_FAVORITE };
        String sqlTables = TABLE_STORY;
        String selection;

        switch(type) {
            default:
            case MyBookshelfActivity.SORT_BOOKSHELF:
                selection = null;
                break;
            case MyBookshelfActivity.SORT_FAVORITES:
                selection = COLUMN_FAVORITE + "=1";
                break;
            case MyBookshelfActivity.SORT_PROGRESS:
                selection = COLUMN_CURRENT_PAGE + ">1";
                break;
        }

        Cursor cursor = selectQuery(sqlSelect, sqlTables, selection);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do {
                Story story = new Story();
                story.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                story.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                story.setAuthor(cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR)));
                story.setCover(cursor.getString(cursor.getColumnIndex(COLUMN_COVER)));
                story.setPageCount(cursor.getInt(cursor.getColumnIndex(COLUMN_PAGE_COUNT)));
                story.setCurrentPage(cursor.getInt(cursor.getColumnIndex(COLUMN_CURRENT_PAGE)));

                boolean favorite = cursor.getInt(cursor.getColumnIndex(COLUMN_FAVORITE)) == 1;
                story.setFavorite(favorite);
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
        Story story = new Story();

        String[] sqlSelect = { COLUMN_ID, COLUMN_TITLE, COLUMN_SYNOPSIS, COLUMN_AUTHOR,
                COLUMN_CREATE_DATE, COLUMN_LAST_EDIT_DATE, COLUMN_CREATOR_USERNAME,
                COLUMN_GENRE, COLUMN_TAGS, COLUMN_COVER, COLUMN_LANGUAGE, COLUMN_PAGE_COUNT,
                COLUMN_CURRENT_PAGE, COLUMN_FAVORITE };
        String sqlTables = TABLE_STORY;
        String selection = COLUMN_ID + "=" + Integer.toString(id);
        Cursor cursor = selectQuery(sqlSelect, sqlTables, selection);

        if (cursor.moveToFirst()) {
            do {
                story.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                story.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                story.setAuthor(cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR)));
                story.setSynopsis(cursor.getString(cursor.getColumnIndex(COLUMN_SYNOPSIS)));
                story.setCreateDate(cursor.getString(cursor.getColumnIndex(COLUMN_CREATE_DATE)));
                story.setLastEditDate(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_EDIT_DATE)));
                story.setCreatorUsername(cursor.getString(cursor.getColumnIndex(COLUMN_CREATOR_USERNAME)));
                story.setGenre(cursor.getString(cursor.getColumnIndex(COLUMN_GENRE)));
                story.setTags(cursor.getString(cursor.getColumnIndex(COLUMN_TAGS)));
                story.setCover(cursor.getString(cursor.getColumnIndex(COLUMN_COVER)));
                story.setLanguage(cursor.getString(cursor.getColumnIndex(COLUMN_LANGUAGE)));
                story.setPageCount(cursor.getInt(cursor.getColumnIndex(COLUMN_PAGE_COUNT)));
                story.setCurrentPage(cursor.getInt(cursor.getColumnIndex(COLUMN_CURRENT_PAGE)));

                boolean favorite = cursor.getInt(cursor.getColumnIndex(COLUMN_FAVORITE)) == 1;
                story.setFavorite(favorite);
            } while (cursor.moveToNext());
        }

        story.setPageList(getPages(story));

        return story;
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
        if(page.getType().equals(TYPE_LAST)) {
            //Add restart and main menu buttons
            String main_menu = MyBookshelfActivity.CONTEXT_NAME.getResources().getString(R.string.main_menu);
            String restart = MyBookshelfActivity.CONTEXT_NAME.getResources().getString(R.string.restart);

            newChoice = new Choice();
            newChoice.setDestination(0);
            newChoice.setText(main_menu);
            choiceList.add(newChoice);

            newChoice = new Choice();
            newChoice.setDestination(0);
            newChoice.setText(restart);
            choiceList.add(newChoice);
        }

        return choiceList;
    }

    /**
     * Save selected story's progress in the database
     * @param storyId
     * @param currentPage
     */
    public void updateProgress(int storyId, int currentPage) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = COLUMN_ID + "=" + Integer.toString(storyId);
        ContentValues values = new ContentValues();

        values.put(COLUMN_CURRENT_PAGE, currentPage);
        db.update(TABLE_STORY, values, selection, null);
    }

    /**
     * Add selected story to myFavoritesList
     * @param story
     */
    public void updateFavorite(Story story) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = COLUMN_ID + "=" + Integer.toString(story.getId());
        ContentValues values = new ContentValues();

        values.put(COLUMN_FAVORITE, story.getFavorite());
        db.update(TABLE_STORY, values, selection, null);
    }

    /**
     * Delete story and related data from all tables
     * @param storyId
     */
    public void deleteStory(int storyId) {
        SQLiteDatabase db = getReadableDatabase();
        String storySelection = COLUMN_ID + "=" + Integer.toString(storyId);

        // Get pages to be deleted
        String[] sqlSelect = { COLUMN_ID };
        String selection = COLUMN_STORY_ID + "=" + Integer.toString(storyId);
        Cursor cursor = selectQuery(sqlSelect, TABLE_PAGE, selection);
        ArrayList<Integer> pageList = new ArrayList<>();

        if(cursor.moveToFirst()) {
            do {
                pageList.add(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            } while (cursor.moveToNext());
        }

        // Delete from choice based on pageList and then from page
        for(int i = 1; i <= pageList.size(); i++) {
            int pageId = pageList.get(i-1);
            String choiceSelection = COLUMN_PAGE_ID + "=" + Integer.toString(pageId);
            db.delete(TABLE_CHOICE, choiceSelection, null);
            db.delete(TABLE_PAGE, selection, null);
        }

        // Finally, delete from story table
        selection = COLUMN_ID + "=" + Integer.toString(storyId);
        db.delete(TABLE_STORY, selection, null);
    }

    private Cursor selectQuery(String[] sqlSelect, String sqlTables, String selection) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(sqlTables);

        return qb.query(db, sqlSelect, selection, null, null, null, null);
    }

    /**
     * Close database after use
     */
    public void closeDB() {
        SQLiteDatabase db = getReadableDatabase();
        if (db != null && db.isOpen()) { db.close(); }
    }
}
