package wclem12.com.agameofyou.story;

import java.io.Serializable;
import java.util.ArrayList;

import wclem12.com.agameofyou.page.Page;

public class Story implements Serializable {
    private int id = -1;
    private String title = null;
    private String author = null;
    private String synopsis = null;
    private String createDate = null;
    private String lastEditDate = null;
    private String creatorUsername = null;
    private String genre = null;
    private String tags = null;
    private String cover = null;
    private String language = null;
    private int pageCount = -1;
    private int currentPage = -1;
    private boolean favorite = false;
    private ArrayList<Page> pageList = new ArrayList<Page>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSynopsis() { return synopsis; }

    public void setSynopsis(String synopsis) { this.synopsis = synopsis; }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String create_date) {
        this.createDate = create_date;
    }

    public String getLastEditDate() {
        return lastEditDate;
    }

    public void setLastEditDate(String last_edit_date) {
        this.lastEditDate = last_edit_date;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creator_username) {
        this.creatorUsername = creator_username;
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
        if((cover == null) || (cover.length() < 1)) {
            cover = "book";
        }

        this.cover = cover;
    }

    public String getLanguage() { return language; }

    public void setLanguage(String language) { this.language = language; }

    public int getPageCount() { return pageCount; }

    public void setPageCount (int page_count) { this.pageCount = page_count; }

    public int getCurrentPage() { return currentPage; }

    public void setCurrentPage (int current_page) { this.currentPage = current_page; }

    public boolean getFavorite() { return favorite; }

    public void setFavorite(boolean favorite) { this.favorite = favorite; }

    public ArrayList<Page> getPageList() {
        return pageList;
    }

    public void setPageList(ArrayList<Page> pageList) {
        this.pageList = pageList;
    }

    public Page getStoryPage (int destination) {
        return pageList.get(destination);
    }
}
