package wclem12.com.agameofyou.nav_drawer;

public class NavDrawerItem {
    private String description = null;
    private String icon  = null;

    public NavDrawerItem(String description, String icon) {
        this.description = description;
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) { this.description = description; }

    public String getIcon() { return icon; }

    public void setIcon(String icon) { this.icon = icon; }
}
