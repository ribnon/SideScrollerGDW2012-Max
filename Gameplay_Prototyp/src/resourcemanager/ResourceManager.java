package resourcemanager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.Image;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.loading.LoadingList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
 
public class ResourceManager {
 
	private static ResourceManager _instance = new ResourceManager();
 
	private Map<String, Sound> soundMap;
	private Map<String, Music> musicMap;
	private Map<String, Image> imageMap;
	private Map<String, String> textMap;
	private Map<String, Animation> animationMap;
	
	@SuppressWarnings("unchecked")
	private ArrayList<String>[] customizablesListArray = (ArrayList<String>[])new ArrayList[6];
	
	public ArrayList<String>[] getCustomizablesListArray() {
		return customizablesListArray;
	}
 
	private ResourceManager(){
		soundMap 	 = new HashMap<String, Sound>();
		musicMap 	 = new HashMap<String, Music>();
		imageMap 	 = new HashMap<String, Image>();
		textMap 	 = new HashMap<String, String>();
		animationMap = new HashMap<String, Animation>();
		
		for (int i=0; i<6; ++i) {
			customizablesListArray[i] = new ArrayList<String>();
		}
	}
 
	public final static ResourceManager getInstance(){
		return _instance;
	}
 
	public void loadResources(InputStream is) throws SlickException {
		loadResources(is, false);
	}
 
	public void loadResources(InputStream is, boolean deferred) throws SlickException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new SlickException("Could not load resources", e);
		}
		Document doc = null;
        try {
			doc = docBuilder.parse (is);
		} catch (SAXException e) {
			throw new SlickException("Could not load resources", e);
		} catch (IOException e) {
			throw new SlickException("Could not load resources", e);
		}
 
		// normalize text representation
        doc.getDocumentElement().normalize();
 
        NodeList listResources = doc.getElementsByTagName("resource");
 
        int totalResources = listResources.getLength();
 
        if(deferred){
        	LoadingList.setDeferredLoading(true);
        }
 
 
        for(int resourceIdx = 0; resourceIdx < totalResources; resourceIdx++){
 
        	Node resourceNode = listResources.item(resourceIdx);
 
        	if(resourceNode.getNodeType() == Node.ELEMENT_NODE){
        		Element resourceElement = (Element)resourceNode;
 
        		String type = resourceElement.getAttribute("type");
 
        		if(type.startsWith("image")){
        			addElementAsImage(resourceElement, type);
        		}else if(type.startsWith("animation")){
        			addElementAsAnimation(resourceElement, type);
        		}else if(type.equals("sound")){
        			addElementAsSound(resourceElement);
        		}else if(type.equals("music")){
        			addElementAsMusic(resourceElement);
        		}else if(type.equals("text")){
        			addElementAsText(resourceElement);
        		}else if(type.equals("font")){
        			//fonts can only be loaded directly, not deferred
        		}
        	}
        }
 
	}
 
	private void addElementAsText(Element resourceElement) throws SlickException{
		loadText(resourceElement.getAttribute("id"), resourceElement.getTextContent());
	}
 
	public String loadText(String id, String value) throws SlickException{
		if(value == null)
			throw new SlickException("Text resource [" + id + "] has invalid value");
 
		textMap.put(id, value);
 
		return value;
	}
 
	public String getText(String ID) {
		return textMap.get(ID);
	}
 
	private void addElementAsSound(Element resourceElement) throws SlickException {
		loadSound(resourceElement.getAttribute("id"), resourceElement.getTextContent());
	}
 
	public Sound loadSound(String id, String path) throws SlickException{
		if(path == null || path.length() == 0)
			throw new SlickException("Sound resource [" + id + "] has invalid path");
 
		Sound sound = null;
 
		try {
			sound = new Sound(path);
		} catch (SlickException e) {
			throw new SlickException("Could not load sound", e);
		}
 
		this.soundMap.put(id, sound);
 
		return sound;
	}
 
	public final Sound getSound(String ID){
		return soundMap.get(ID);
	}
 
	private void addElementAsMusic(Element resourceElement) throws SlickException {
		loadMusic(resourceElement.getAttribute("id"), resourceElement.getTextContent());
	}
 
	public Music loadMusic(String id, String path) throws SlickException{
		if(path == null || path.length() == 0)
			throw new SlickException("Music resource [" + id + "] has invalid path");
 
		Music music = null;
 
		try {
			music = new Music(path);
		} catch (SlickException e) {
			throw new SlickException("Could not load music", e);
		}
 
		this.musicMap.put(id, music);
		customizablesListArray[5].add(id);
 
		return music;
	}
 
	public final Music getMusic(String ID){
		return musicMap.get(ID);
	}
	
	private void addElementAsAnimation(Element resourceElement, String type) throws SlickException{
		loadAnimation(resourceElement.getAttribute("id"), resourceElement.getTextContent(), 
				Integer.valueOf(resourceElement.getAttribute("tw")),
				Integer.valueOf(resourceElement.getAttribute("th")),
				Integer.valueOf(resourceElement.getAttribute("duration")),
				Boolean.valueOf(resourceElement.getAttribute("flipped")),
				type);
	}
	private void loadAnimation(String id, String spriteSheetPath, int tw, int th, int duration, boolean flipped, String type) throws SlickException{
		// type is declared after the slash "/" within resources.xml
		
		if(spriteSheetPath == null || spriteSheetPath.length() == 0)
			throw new SlickException("Image resource [" + id + "] has invalid path");
 
		final SpriteSheet animationSheet = new SpriteSheet(spriteSheetPath, tw, th);
		Animation anim = new Animation();
		
		for (int frame = 0; frame < animationSheet.getHorizontalCount(); ++frame)
		{
			if (flipped)
			{
				anim.addFrame(animationSheet.getSprite(frame, 0).getFlippedCopy(true, false), duration);
			}
			else
			{
				anim.addFrame(animationSheet.getSprite(frame, 0), duration);
			}
		}
 
		animationMap.put(id, anim);
	}
	
	private final void addElementAsImage(Element resourceElement, String type) throws SlickException {
		loadImage(resourceElement.getAttribute("id"), resourceElement.getTextContent(), type);
	}
 
	public Image loadImage(String id, String path, String type) throws SlickException{
		if(path == null || path.length() == 0)
			throw new SlickException("Image resource [" + id + "] has invalid path");
 
		Image image = null;
		try{
			image = new Image(path);
		} catch (SlickException e) {
			throw new SlickException("Could not load image", e);
		}
 
		this.imageMap.put(id, image);
		insertImageIdIntoCorrespondingList(id, type);
 
		return image;
	}
 
	public final Image getImage(String ID){
		return imageMap.get(ID);
	}
	
	private final void insertImageIdIntoCorrespondingList(String id, String type) {
		if(type.endsWith("bg")){
			customizablesListArray[0].add(id);
		}else if(type.endsWith("border")){
			customizablesListArray[1].add(id);
		}else if(type.endsWith("brick")){
			customizablesListArray[2].add(id);
		}else if(type.endsWith("paddle")){
			customizablesListArray[3].add(id);
		}else if(type.endsWith("ball")){
			customizablesListArray[4].add(id);
		}
	}
}