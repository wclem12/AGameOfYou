package wclem12.com.agameofyou.choice;

import java.io.Serializable;

public class Choice implements Serializable {
    private int id = -1;
    private int destination = -1;
    private String text = null;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

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
