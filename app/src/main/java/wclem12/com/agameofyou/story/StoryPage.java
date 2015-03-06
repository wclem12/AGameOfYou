package wclem12.com.agameofyou.story;

import java.io.Serializable;
import java.util.ArrayList;

public class StoryPage implements Serializable {
    private int id = -1;
    private String type = null;
    private int page_number = -1;
    private String text = null;
    private ArrayList<StoryPageChoice> buttonList = new ArrayList<StoryPageChoice>();

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPageNumber() {
        return page_number;
    }

    public void setPageNumber(int page_number) {
        this.page_number = page_number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<StoryPageChoice> getButtonList() {
        return buttonList;
    }

    public void addButton (StoryPageChoice button) {
        buttonList.add(button);
    }


}
