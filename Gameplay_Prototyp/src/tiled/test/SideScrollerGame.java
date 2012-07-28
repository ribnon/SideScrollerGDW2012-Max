package tiled.test;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import gamestates.CreditsScreenState;
import gamestates.LoadingScreenState;
import gamestates.PlayState;

/**
 * Die Hauptklasse des Spiels.
 * 
 * @author Thomas
 *
 */
public class SideScrollerGame extends StateBasedGame {
	
	public final static String GAME_TITLE = "DreamGuards - Gameplay Prototype";
	
	public static final int LOADING_SCREEN_STATEID  = 1;
	//public static final int MAIN_MENU_STATEID       = 2; // not yet implemented
	public static final int CREDITS_SCREEN_STATEID  = 3;
    public static final int PLAY_STATEID            = 4;
    
    public static boolean FULLSCREEN = false;
    public static boolean MUSIC_ON_OFF = false;
    public static boolean SOUND_ON_OFF = true;
    public static boolean WAIT_FOR_VSYNC = true;
    
	public SideScrollerGame(String title) {
		super(title);
		this.addState(new LoadingScreenState(LOADING_SCREEN_STATEID));
		this.addState(new CreditsScreenState(CREDITS_SCREEN_STATEID));
        this.addState(new PlayState(PLAY_STATEID));
        this.enterState(LOADING_SCREEN_STATEID);
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.getState(LOADING_SCREEN_STATEID).init(container, this);
		this.getState(CREDITS_SCREEN_STATEID).init(container, this);
		this.getState(PLAY_STATEID).init(container, this);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new SideScrollerGame(GAME_TITLE));
		app.setVSync(WAIT_FOR_VSYNC);
		app.setSmoothDeltas(true);
		app.setTargetFrameRate(60);
		app.setShowFPS(false);
		app.setUpdateOnlyWhenVisible(true);
		
		app.setMusicOn(MUSIC_ON_OFF);
		app.setSoundOn(SOUND_ON_OFF);
		
		if (app.supportsMultiSample())
			app.setMultiSample(2);
		
		app.setDisplayMode(800, 600, FULLSCREEN);
		
		app.start();
	}

}
