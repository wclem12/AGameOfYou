package wclem12.com.agameofyou.story;

import java.io.Serializable;
import java.util.ArrayList;

import wclem12.com.agameofyou.story.PageButton;

public class StoryPage implements Serializable {
    private String type = null;
    private int page_number = -1;
    private String text = null;
    private ArrayList<PageButton> buttonList = new ArrayList<PageButton>();

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

    public ArrayList<PageButton> getButtonList() {
        return buttonList;
    }

    public void addButton (PageButton button) {
        buttonList.add(button);
    }


}
