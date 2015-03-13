package wclem12.com.agameofyou.about_story;

public class AboutStoryItem {
    private String tag;
    private String data;

    public AboutStoryItem(String tag, String data) {
        this.tag = tag;
        this.data = data;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
