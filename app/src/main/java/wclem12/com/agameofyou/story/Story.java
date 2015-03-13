package wclem12.com.agameofyou.story;

import java.io.Serializable;
import java.util.ArrayList;

public class Story implements Serializable {
    private int id = -1;
    private String title = null;
    private String author = null;
    private String create_date = null;
    private String last_edit_date = null;
    private String creator_username = null;
    private String genre = null;
    private String tags = null;
    private String cover = null;
    private ArrayList<Page> pageList = new ArrayList<Page>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Page getStoryPage (int destination) {
        return pageList.get(destination);
    }

    public ArrayList<Page> getPageList() {
        return pageList;
    }

    public void setPageList(ArrayList<Page> pageList) {
        this.pageList = pageList;
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
