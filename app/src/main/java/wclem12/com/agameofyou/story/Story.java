package wclem12.com.agameofyou.story;

import java.io.Serializable;
import java.util.ArrayList;

public class Story implements Serializable {
    private ArrayList<StoryPage> storyPageList = new ArrayList<StoryPage>();
    private String title = null;
    private String author = null;
    private String create_date = null;
    private String last_edit_date = null;
    private String creator_username = null;
    private String unique_id = null;
    private String genre = null;
    private String tags = null;
    private String cover = null;

    public StoryPage getStoryPage (int destination) {
        return storyPageList.get(destination);
    }

    public ArrayList<StoryPage> getStoryPageList() {
        return storyPageList;
    }

    public void setStoryPageList(ArrayList<StoryPage> storyPageList) {
        this.storyPageList = storyPageList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreateDate() {
        return create_date;
    }

    public void setCreateDate(String create_date) {
        this.create_date = create_date;
    }

    public String getLastEditDate() {
        return last_edit_date;
    }

    public void setLastEditDate(String last_edit_date) {
        this.last_edit_date = last_edit_date;
    }

    public String getCreatorUsername() {
        return creator_username;
    }

    public void setCreatorUsername(String creator_username) {
        this.creator_username = creator_username;
    }

    public String getUniqueID() {
        return unique_id;
    }

    public void setUniqueID(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCover() { return cover; }

    public void setCover(String cover) {
        if(cover.equals(null) || cover.equals("")) {
            cover = "book";
        }

        this.cover = cover;
    }
}
