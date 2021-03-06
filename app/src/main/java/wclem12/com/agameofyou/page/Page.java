package wclem12.com.agameofyou.page;

import java.io.Serializable;
import java.util.ArrayList;

import wclem12.com.agameofyou.choice.Choice;

public class Page implements Serializable {
    private int id = -1;
    private String type = null;
    private int page_number = -1;
    private String text = null;
    private ArrayList<Choice> choiceList = new ArrayList<Choice>();

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

    public ArrayList<Choice> getChoiceList() {
        return choiceList;
    }

    public void setChoiceList(ArrayList<Choice> choiceList) {
        this.choiceList = choiceList;
    }


}
