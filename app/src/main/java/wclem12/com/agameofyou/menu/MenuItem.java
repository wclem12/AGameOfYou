package wclem12.com.agameofyou.menu;

import java.util.HashMap;

import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.activity.MainMenuActivity;

public class MenuItem {
    private String title = null;
    private String author = null;
    private String xml_id = null;
    private HashMap<String, String> map = new HashMap<String, String>();

    public HashMap<String, String> getMap (){
        return map;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        map.put(MainMenuActivity.CONTEXT_NAME.getString(R.string.manifest_title_tag), title);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
        map.put(MainMenuActivity.CONTEXT_NAME.getString(R.string.manifest_author_tag), author);
    }

    public String getXmlID() {
        return xml_id;
    }

    public void setXmlID(String xml_id) {
        this.xml_id = xml_id;
        map.put(MainMenuActivity.CONTEXT_NAME.getString(R.string.manifest_xml_id_tag), xml_id);
    }
}
