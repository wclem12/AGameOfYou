package wclem12.com.agameofyou.about_app;

public class AboutAppItem {
    private String description = null;
    private String uri = null;
    private String icon  = null;

    public AboutAppItem(String description, String uri, String icon) {
        this.description = description;
        this.uri = uri;
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) { this.description = description; }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) { this.uri = uri; }

    public String getIcon() { return icon; }

    public void setIcon(String icon) { this.icon = icon; }
}
