package gamestates;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import resourcemanager.ResourceManager;
import tiled.test.SideScrollerGame;


public class CreditsScreenState extends BasicGameState {

	private final int GameStateID;
	private final String credits     = "CREDITS";
	private final String escToReturn = "press Escape to return to game";
	
	private Music music = null;
	private Font font = null;
	
	// background image (with alpha layer, so the font can fade out)
	private Image background;
	
	//these variables are used for drawing the strings
	private int creditsPosY   = 0;
	private int maxCreditY    = 0;
	private String creditText = null;


	public CreditsScreenState(int StateID) {
		super();
		GameStateID = StateID;
	}

	@Override
	public int getID() { return GameStateID; }

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		font = new AngelCodeFont("data/fonts/averia_30.fnt", "data/fonts/averia_30.png", true);
		background = ResourceManager.getInstance().getImage("bg_credits");
		
		//check if image has to be scaled to fit completely
		if (background != null && 
				( background.getHeight() != container.getHeight()
				  || background.getWidth() != container.getWidth() )
			)
		{
			background = background.getScaledCopy(container.getWidth(), container.getHeight());
		}
		
		StringBuilder sb = new StringBuilder();
		int lineCount = 0;
		try {
			String NL = System.getProperty("line.separator");
		    Scanner scanner = new Scanner(new FileInputStream("data/credits.txt"), "UTF-8");
		    try {
		      while (scanner.hasNextLine()){
		        sb.append(scanner.nextLine() + NL);
		        ++lineCount;
		      }
		    }
		    finally{
		      scanner.close();
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			sb = new StringBuilder(e.getLocalizedMessage());
			lineCount = 1;
		}
		creditsPosY = container.getHeight();
		creditText  = sb.toString();
		maxCreditY  = lineCount * container.getGraphics().getFont().getLineHeight(); //font.getLineHeight()
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);
		if (!container.isMusicOn()) {
			if (music != null) {
				music.stop();
			}
		} else {
			music = ResourceManager.getInstance().getMusic("green_town");
			if (music != null) {
				music.loop();
			}
		}
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.leave(container, game);
		if (container.isMusicOn()) {
			if (music != null && music.playing())
				music.stop();
		}
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
		//Draw credits
		g.setColor(Color.white);
		g.setFont(font);
		
		// credits (running from bottom to top of the screen)
		g.drawString(creditText, 32.0f, creditsPosY);
		
		// alpha-layered background image
		background.draw(0, 0);
		
		// info messages, what the user can do within this screen
		g.drawString(credits, (container.getWidth() / 2) - (font.getWidth(credits) / 2), font.getHeight(credits)); // fixed position for word "CREDITS"
		g.drawString(escToReturn, container.getWidth() - font.getWidth(escToReturn) , container.getHeight() - font.getLineHeight());
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		if (container.getInput().isKeyPressed(Input.KEY_ESCAPE))
		{
			game.enterState(SideScrollerGame.PLAY_STATEID,
							new FadeOutTransition(Color.white, 1000),
							new FadeInTransition(Color.black, 1000)
			);
		}
		
		--creditsPosY;
		if (creditsPosY < 0 - maxCreditY) {
			creditsPosY = container.getHeight();
		}
	}

}
