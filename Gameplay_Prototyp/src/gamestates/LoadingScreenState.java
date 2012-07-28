package gamestates;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import resourcemanager.ResourceManager;
import tiled.test.SideScrollerGame;

public class LoadingScreenState extends BasicGameState {
	private final int GameStateID;
	private String lastLoaded = null;
	private float loaded = 0.0f;
	private float total = 1.0f;
	private boolean isDeferredLoading = true;
	private boolean loadingCompleted = false;
	private AngelCodeFont greyFont = null;
	
	public LoadingScreenState(int StateID) {
		super();
		GameStateID = StateID;
	}
	
	@Override
	public int getID() { return GameStateID; }

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		greyFont = new AngelCodeFont("data/fonts/averia_30.fnt", "data/fonts/averia_30.png", true);
		FileInputStream fis;
		try {
			fis = new FileInputStream("data/resources.xml");
			ResourceManager.getInstance().loadResources(fis, isDeferredLoading);
		} catch (FileNotFoundException fnfe) {
			throw new SlickException("Could not find resources.xml", fnfe);
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		if (lastLoaded != null) { //TODO: Länge beschränken (Anzahl und Bildschirmbalkenbreite und so)
			g.drawString("Loaded: " + lastLoaded, 100, 100);
	        g.fillRect(100,150,loaded*20,20); // Ladebalken füllen
	        g.drawRect(100,150,total*20,20);  // Rechteck um Ladebalken herum zeichnen
		}
        
        if (loadingCompleted) {
        	g.setFont(greyFont);
        	g.drawString("Loading completed", 100, 250);
        	g.drawString("Press \"Return\" to start the game.", 100, 300);
        }
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		if (LoadingList.get().getRemainingResources() > 0) {
	        DeferredResource nextResource = LoadingList.get().getNext();
	        try {
				nextResource.load();
			} catch (IOException e) {
				throw new SlickException("Error while loading Deferred Resource: " + nextResource.getDescription(), e);
			}
	        lastLoaded = nextResource.getDescription();
	        total = LoadingList.get().getTotalResources(); 
	        loaded = LoadingList.get().getTotalResources() - LoadingList.get().getRemainingResources();  
	    } else {
	        if (!loadingCompleted) {
	        	loadingCompleted = true;
	        }
	        if (container.getInput().isKeyPressed(Input.KEY_RETURN) ||
	        		container.getInput().isKeyPressed(Input.KEY_ENTER))
	        {
	        	game.enterState(SideScrollerGame.PLAY_STATEID);
	        }
	    }
	}

}