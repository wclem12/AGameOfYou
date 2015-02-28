package wclem12.com.agameofyou.menu;

public class MenuItem {
    private String title = null;
    private String author = null;
    private String xml_id = null;
    private String cover = null;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title; }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) { this.author = author; }

    public String getXmlID() {
        return xml_id;
    }

    public void setXmlID(String xml_id) { this.xml_id = xml_id; }

    public String getCover() { return cover; }

    public void setCover(String cover) { this.cover = cover; }
}
