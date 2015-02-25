package wclem12.com.agameofyou.menu;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;

import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.activity.MainMenuActivity;
import wclem12.com.agameofyou.menu.MenuItem;

public class MenuParserHandler extends DefaultHandler {

    Boolean currentElement = false;
    String currentValue = "";
    MenuItem menuItem = null;
    private ArrayList<HashMap<String, String>> menuItemsList = new ArrayList<HashMap<String, String>>();

    public ArrayList<HashMap<String, String>> getMenuItemsList() { return menuItemsList; }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentElement = true;
        currentValue = "";

        if (localName.equals(MainMenuActivity.CONTEXT_NAME.getResources().getString(R.string.manifest_story_tag))) {
            menuItem = new MenuItem();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        currentElement = false;

        if (localName.equalsIgnoreCase(MainMenuActivity.CONTEXT_NAME.getResources().getString(R.string.manifest_title_tag))) {
            //add title to story menu item
            menuItem.setTitle(currentValue);
        } else if (localName.equalsIgnoreCase(MainMenuActivity.CONTEXT_NAME.getResources().getString(R.string.manifest_author_tag))) {
            //add author to story menu item
            menuItem.setAuthor("By: " + currentValue);
        } else if (localName.equalsIgnoreCase(MainMenuActivity.CONTEXT_NAME.getResources().getString(R.string.manifest_xml_id_tag))) {
            //add xml_id to story menu item
            menuItem.setXmlID(currentValue);
        } else if (localName.equalsIgnoreCase(MainMenuActivity.CONTEXT_NAME.getResources().getString(R.string.manifest_story_tag))) {
            //add story menu item to menu items list
            menuItemsList.add(menuItem.getMap());
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        currentValue = new String(ch, start, length);
    }
}