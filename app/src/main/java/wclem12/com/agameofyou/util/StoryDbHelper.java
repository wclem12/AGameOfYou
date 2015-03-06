package wclem12.com.agameofyou.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StoryDbHelper extends SQLiteOpenHelper {
    // Database information
    private static final int DATABASE_VERISON = 1;
    private static final String DATABASE_NAME = "StoryManifest.db";

    // Useful tags
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String BLOB_TYPE = " BLOB";
    private static final String COMMA_SEP = ",";

    // Table names
    public static final String TABLE_STORY = "story";
    public static final String TABLE_PAGE = "page";
    public static final String TABLE_CHOICE = "choice";

    // Common column names
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TEXT = "text";

    // STORY Table - column names
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_CREATEDATE = "create_date";
    public static final String COLUMN_EDITDATE = "last_edit_date";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_GENRE = "genre";
    public static final String COLUMN_TAGS = "tags";
    public static final String COLUMN_COVER_NAME = "cover_name";
    public static final String COLUMN_COVER_IMAGE = "cover_image";

    // PAGE Table - column names
    public static final String COLUMN_STORY_ID = "story_id";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_NUMBER = "number";

    // CHOICE Table - column names
    public static final String COLUMN_PAGE_ID = "story_page_id";
    public static final String COLUMN_DESTINATION = "destination";

    // Table Create Statements
    // Story table create statement
    private static final String CREATE_TABLE_STORY =
            "CREATE TABLE IF NOT EXISTS " + TABLE_STORY + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_TITLE + TEXT_TYPE + COMMA_SEP
                    + COLUMN_AUTHOR + TEXT_TYPE + COMMA_SEP
                    + COLUMN_CREATEDATE + TEXT_TYPE + COMMA_SEP
                    + COLUMN_EDITDATE + TEXT_TYPE + COMMA_SEP
                    + COLUMN_USERNAME + TEXT_TYPE + COMMA_SEP
                    + COLUMN_GENRE + TEXT_TYPE + COMMA_SEP
                    + COLUMN_TAGS + TEXT_TYPE + COMMA_SEP
                    + COLUMN_COVER_NAME + TEXT_TYPE + COMMA_SEP
                    + COLUMN_COVER_IMAGE + BLOB_TYPE
                    + ");";

    // Page table create statement
    private static final String CREATE_TABLE_PAGE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_STORY + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_STORY_ID + INTEGER_TYPE + COMMA_SEP
                    + COLUMN_TYPE + TEXT_TYPE + COMMA_SEP
                    + COLUMN_NUMBER + INTEGER_TYPE + COMMA_SEP
                    + COLUMN_TEXT + TEXT_TYPE
                    + ");";

    // Choice table create statement
    private static final String CREATE_TABLE_CHOICE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_CHOICE + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_PAGE_ID + INTEGER_TYPE + COMMA_SEP
                    + COLUMN_DESTINATION + INTEGER_TYPE + COMMA_SEP
                    + COLUMN_TEXT + TEXT_TYPE
                    + ");";
    public StoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERISON);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STORY);
        db.execSQL(CREATE_TABLE_PAGE);
        db.execSQL(CREATE_TABLE_CHOICE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade, drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHOICE);

        // create new table
        onCreate(db);
    }

    // ---------------------------"story" table methods-------------------------------- //

    /**
     * Creating a story
     */
//    public long createStory(Story story, long[] page_ids) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_TITLE, story.getTitle());
//        values.put(COLUMN_AUTHOR, story.getAuthor());
//        values.put(COLUMN_CREATEDATE, story.getCreateDate());
//        values.put(COLUMN_EDITDATE, story.getLastEditDate());
//        values.put(COLUMN_USERNAME, story.getCreatorUsername());
//        values.put(COLUMN_GENRE, story.getGenre());
//        values.put(COLUMN_TAGS, story.getTags());
//        values.put(COLUMN_COVER_NAME, story.getCover());
//        //Get image: values.put(COLUMN_COVER_IMAGE, );
//
//        // insert row
//        long story_id = db.insert(TABLE_STORY, null, values);
//        return story_id;
//    }
//
//    /**
//     * get single story
//     */
//    public Story getStory (long story_id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        String selectQuery = "SELECT * FROM " + TABLE_STORY + " WHERE "
//                + COLUMN_ID + " = " + story_id;
//
//        Log.e("LOG", selectQuery);
//
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        if (cursor != null) cursor.moveToFirst();
//
//        Story story = new Story();
//        story.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
//        story.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
//        story.setAuthor(cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR)));
//        story.setCreateDate(cursor.getString(cursor.getColumnIndex(COLUMN_CREATEDATE)));
//        story.setLastEditDate(cursor.getString(cursor.getColumnIndex(COLUMN_EDITDATE)));
//        story.setCreatorUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
//        story.setGenre(cursor.getString(cursor.getColumnIndex(COLUMN_GENRE)));
//        story.setTags(cursor.getString(cursor.getColumnIndex(COLUMN_TAGS)));
//        story.setCover(cursor.getString(cursor.getColumnIndex(COLUMN_COVER_NAME)));
//
//        return story;
//    }
//
//    /**
//     * getting all stories
//     */
//    public List<Story> getAllStories () {
//        List<Story> stories = new ArrayList<Story>();
//        String selectQuery = "SELECT * FROM " + TABLE_STORY;
//
//        Log.e("LOG", selectQuery);
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()){
//            do {
//                Story story = new Story();
//                story.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
//                story.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
//                story.setAuthor(cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR)));
//                story.setCreateDate(cursor.getString(cursor.getColumnIndex(COLUMN_CREATEDATE)));
//                story.setLastEditDate(cursor.getString(cursor.getColumnIndex(COLUMN_EDITDATE)));
//                story.setCreatorUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
//                story.setGenre(cursor.getString(cursor.getColumnIndex(COLUMN_GENRE)));
//                story.setTags(cursor.getString(cursor.getColumnIndex(COLUMN_TAGS)));
//                story.setCover(cursor.getString(cursor.getColumnIndex(COLUMN_COVER_NAME)));
//
//                // adding to the story list
//                stories.add(story);
//            } while (cursor.moveToNext());
//        }
//
//        return stories;
//    }
//
//    /**
//     * getting story count
//     */
//    public int getStoryCount() {
//        String countQuery = "SELECT * FROMT " + TABLE_STORY;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//
//        int count = cursor.getCount();
//        cursor.close();
//
//        // return count
//        return count;
//    }
//
//    /**
//     * Updating a story
//     */
//    public int updateStory (Story story) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_TITLE, story.getTitle());
//        values.put(COLUMN_AUTHOR, story.getAuthor());
//        values.put(COLUMN_CREATEDATE, story.getCreateDate());
//        values.put(COLUMN_EDITDATE, story.getLastEditDate());
//        values.put(COLUMN_USERNAME, story.getCreatorUsername());
//        values.put(COLUMN_GENRE, story.getGenre());
//        values.put(COLUMN_TAGS, story.getTags());
//        values.put(COLUMN_COVER_NAME, story.getCover());
//        //Get image: values.put(COLUMN_COVER_IMAGE, );
//
//        // updating row
//        return db.update(TABLE_STORY, values, COLUMN_ID + " = ?",
//                new String[] { String.valueOf(story.getId())});
//    }
//
//    /**
//     * Deleting a story
//     */
//    public void deleteStory (long story_id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_STORY, COLUMN_ID + " = ?",
//                new String[] { String.valueOf(story_id)});
//    }
//
//    // ---------------------------"page" table methods-------------------------------- //
//
//    /**
//     * Creating a story page
//     */
//    public long createStoryPage(StoryPage storyPage, long[] choice_ids) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_TYPE, storyPage.getType());
//        values.put(COLUMN_NUMBER, storyPage.getPageNumber());
//        values.put(COLUMN_TEXT, storyPage.getText());
//
//        // insert row
//        long page_id = db.insert(TABLE_PAGE, null, values);
//
//        return page_id;
//    }
//
//    /**
//     * get single story page
//     */
//    public StoryPage getStoryPage (long page_id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        String selectQuery = "SELECT * FROM " + TABLE_PAGE + " WHERE "
//                + COLUMN_ID + " = " + page_id;
//
//        Log.e("LOG", selectQuery);
//
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        if (cursor != null) { cursor.moveToFirst(); }
//
//        StoryPage storyPage = new StoryPage();
//        storyPage.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
//        storyPage.setType(cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)));
//        storyPage.setPageNumber(cursor.getInt(cursor.getColumnIndex(COLUMN_NUMBER)));
//        storyPage.setText(cursor.getString(cursor.getColumnIndex(COLUMN_TEXT)));
//
//        return storyPage;
//    }
//
//    /**
//     * getting all story pages
//     */
//    public List<StoryPage> getAllStoryPages () {
//        List<StoryPage> storyPages = new ArrayList<StoryPage>();
//        String selectQuery = "SELECT * FROM " + TABLE_PAGE;
//
//        Log.e("LOG", selectQuery);
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()){
//            do {
//                StoryPage storyPage = new StoryPage();
//                storyPage.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
//                storyPage.setType(cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)));
//                storyPage.setPageNumber(cursor.getInt(cursor.getColumnIndex(COLUMN_NUMBER)));
//                storyPage.setText(cursor.getString(cursor.getColumnIndex(COLUMN_TEXT)));
//
//                // adding to the story list
//                storyPages.add(storyPage);
//            } while (cursor.moveToNext());
//        }
//
//        return storyPages;
//    }
//
//    /**
//     * Updating a story page
//     */
//    public int updateStoryPage (StoryPage storyPage) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_TYPE, storyPage.getType());
//        values.put(COLUMN_NUMBER, storyPage.getPageNumber());
//        values.put(COLUMN_TEXT, storyPage.getText());
//
//        // updating row
//        return db.update(TABLE_PAGE, values, COLUMN_ID + " = ?",
//                new String[] { String.valueOf(storyPage.getId())});
//    }
//
//    /**
//     * Deleting a story page
//     */
//    public void deleteStoryPage (long page_id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_PAGE, COLUMN_ID + " = ?",
//                new String[] { String.valueOf(page_id)});
//    }
//
//    // ---------------------------"choice" table methods-------------------------------- //
//
//    /**
//     * Creating a story page choice
//     */
//    public long createStoryPageChoice(StoryPageChoice storyPageChoice, long[] choice_ids) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_DESTINATION, storyPageChoice.getDestination());
//        values.put(COLUMN_TEXT, storyPageChoice.getText());
//
//        // insert row
//        long choice_id = db.insert(TABLE_CHOICE, null, values);
//
//        return choice_id;
//    }
//
//    /**
//     * get single story page choice
//     */
//    public StoryPageChoice getStoryPageChoice (long choice_id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        String selectQuery = "SELECT * FROM " + TABLE_PAGE + " WHERE "
//                + COLUMN_ID + " = " + choice_id;
//
//        Log.e("LOG", selectQuery);
//
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        if (cursor != null) { cursor.moveToFirst(); }
//
//        StoryPageChoice storyPageChoice = new StoryPageChoice();
//        storyPageChoice.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
//        storyPageChoice.setText(cursor.getString(cursor.getColumnIndex(COLUMN_TEXT)));
//        storyPageChoice.setDestination(cursor.getInt(cursor.getColumnIndex(COLUMN_DESTINATION)));
//
//        return storyPageChoice;
//    }
//
//    /**
//     * getting all story pages choice
//     */
//    public List<StoryPageChoice> getAllStoryPageChoices () {
//        List<StoryPageChoice> storyPageChoices = new ArrayList<StoryPageChoice>();
//        String selectQuery = "SELECT * FROM " + TABLE_CHOICE;
//
//        Log.e("LOG", selectQuery);
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()){
//            do {
//                StoryPageChoice storyPageChoice = new StoryPageChoice();
//                storyPageChoice.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
//                storyPageChoice.setText(cursor.getString(cursor.getColumnIndex(COLUMN_TEXT)));
//                storyPageChoice.setDestination(cursor.getInt(cursor.getColumnIndex(COLUMN_DESTINATION)));
//
//                // adding to the story list
//                storyPageChoices.add(storyPageChoice);
//            } while (cursor.moveToNext());
//        }
//
//        return storyPageChoices;
//    }
//
//    /**
//     * Updating a story page choice
//     */
//    public int updateStoryPageChoice (StoryPageChoice storyPageChoice) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_DESTINATION, storyPageChoice.getDestination());
//        values.put(COLUMN_TEXT, storyPageChoice.getText());
//
//        // updating row
//        return db.update(TABLE_CHOICE, values, COLUMN_ID + " = ?",
//                new String[] { String.valueOf(storyPageChoice.getId())});
//    }
//
//    /**
//     * Deleting a story page choice
//     */
//    public void deleteStoryPageChoice (long choice_id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_CHOICE, COLUMN_ID + " = ?",
//                new String[] { String.valueOf(choice_id)});
//    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()) { db.close(); }
    }
}
