package wclem12.com.agameofyou.story;

import java.io.Serializable;

public class PageButton implements Serializable {
    private int destination = -1;
    private String text = null;

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
