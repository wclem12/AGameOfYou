package wclem12.com.agameofyou.story;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

import wclem12.com.agameofyou.R;
import wclem12.com.agameofyou.activity.MainMenuActivity;

public class StoryParserHandler extends DefaultHandler{

    private Boolean currentElement = false;
    private String currentValue = "";

    private Story story = null;
    private StoryPage storyPage = null;
    private PageButton pageButton = null;
    private ArrayList<StoryPage> storyPageList = new ArrayList<StoryPage>();


    public Story getStory() { return story;}

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentElement = true;
        currentValue = "";

        if (localName.equals(MainMenuActivity.CONTEXT_NAME.getResources().getString(R.string.story_tag))) {
            story = new Story();
        } else if (localName.equals(MainMenuActivity.CONTEXT_NAME.getResources().getString(R.string.page_content_tag))) {
            storyPage = new StoryPage();
        } else if (localName.equals(MainMenuActivity.CONTEXT_NAME.getResources().getString(R.string.button_tag))) {
            pageButton = new PageButton();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        currentElement = false;

        if (localName.equalsIgnoreCase(MainMenuActivity.CONTEXT_NAME.getResources().getString(R.string.page_type_tag))) {
            storyPage.setType(currentValue);
        } else if (localName.equalsIgnoreCase(MainMenuActivity.CONTEXT_NAME.getResources().getString(R.string.page_number_tag))) {
            storyPage.setPageNumber((Integer.parseInt(currentValue)));
        } else if (localName.equalsIgnoreCase(MainMenuActivity.CONTEXT_NAME.getResources().getString(R.string.page_text_tag))) {
            storyPage.setText(currentValue);
        } else if (localName.equalsIgnoreCase(MainMenuActivity.CONTEXT_NAME.getResources().getString(R.string.button_destination_tag))) {
            pageButton.setDestination(Integer.parseInt(currentValue));
        } else if (localName.equalsIgnoreCase(MainMenuActivity.CONTEXT_NAME.getResources().getString(R.string.button_text_tag))) {
            pageButton.setText(currentValue);
        } else if (localName.equalsIgnoreCase(MainMenuActivity.CONTEXT_NAME.getResources().getString(R.string.button_tag))) {
            storyPage.addButton(pageButton);
        } else if (localName.equalsIgnoreCase(MainMenuActivity.CONTEXT_NAME.getResources().getString(R.string.page_content_tag))) {
            storyPageList.add(storyPage);
        } else if (localName.equalsIgnoreCase(MainMenuActivity.CONTEXT_NAME.getResources().getString(R.string.title_tag))) {
            story.setTitle(currentValue);
        } else if (localName.equalsIgnoreCase(MainMenuActivity.CONTEXT_NAME.getResources().getString(R.string.author_tag))) {
            story.setAuthor(currentValue);
        } else if (localName.equalsIgnoreCase(MainMenuActivity.CONTEXT_NAME.getResources().getString(R.string.create_date_tag))) {
            story.setCreateDate( currentValue);
        } else if (localName.equalsIgnoreCase(MainMenuActivity.CONTEXT_NAME.getResources().getString(R.string.last_edit_date_tag))) {
            story.setLastEditDate(currentValue);
        } else if (localName.equalsIgnoreCase(MainMenuActivity.CONTEXT_NAME.getResources().getString(R.string.creator_username_tag))) {
            story.setCreatorUsername(currentValue);
        } else if (localName.equalsIgnoreCase(MainMenuActivity.CONTEXT_NAME.getResources().getString(R.string.unique_id_tag))) {
            story.setUniqueID(currentValue);
        } else if (localName.equalsIgnoreCase(MainMenuActivity.CONTEXT_NAME.getResources().getString(R.string.genre_tag))) {
            story.setGenre(currentValue);
        } else if (localName.equalsIgnoreCase(MainMenuActivity.CONTEXT_NAME.getResources().getString(R.string.cover_tag))) {
            story.setCover(currentValue);
    }   else if (localName.equalsIgnoreCase(MainMenuActivity.CONTEXT_NAME.getResources().getString(R.string.story_tag))) {
            story.setStoryPageList(storyPageList);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        currentValue = new String(ch, start, length).replaceAll("\\\\n|\\\\r", System.getProperty("line.separator"));
        currentValue = currentValue.replaceAll("\\\\t", "    ");
    }
}
