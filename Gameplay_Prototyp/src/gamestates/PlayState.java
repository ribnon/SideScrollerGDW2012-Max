package gamestates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import resourcemanager.ResourceManager;
import tiled.test.Player;
import tiled.test.SideScrollerGame;
import tiled.test.blocks.Block;
import tiled.test.blocks.BlockMap;
import tiled.test.camera.Camera;
import tiled.test.entities.ColorBucket;
import tiled.test.entities.HotButton;
import tiled.test.entities.MoveableObject;
import tiled.test.entities.RotatableObject;
import tiled.test.entities.StaticSprite;

public class PlayState extends BasicGameState {
	private final int GameStateID;
	public final static String GAME_TITLE = "DreamGuards - Gameplay Prototype";
	public final static String DEFAULT_IMAGE_PATH = "./data/assets/staticSprites/default.png";
	public final static String[] ICON_REF_ARRAY = { "./data/assets/staticSprites/icon/teddy_icon_16.png",
													//"./data/assets/staticSprites/icon/teddy_icon_24.png",
													"./data/assets/staticSprites/icon/teddy_icon_32.png",
													"./data/assets/staticSprites/icon/teddy_icon_48.png"};
	
	public static final String BRUSH_SHEET_REF       = "data/assets/spritesheets/brush_sheet.png";
	public static final String BRUSH_PAINT_SHEET_REF = "data/assets/spritesheets/brush_paint_sheet.png";
	public static final String HAT_SHEET_REF         = "data/assets/spritesheets/hat_sheet.png";
	
	private Player playerOne;
	@SuppressWarnings("unused")	private BlockMap map;
	private Camera camera;
	private Music music = null;
//	private final static int SCREEN_WIDTH  = 800;
//	private final static int SCREEN_HEIGHT = 600;
//	private Rectangle miniMapRect = new Rectangle(SCREEN_WIDTH - SCREEN_WIDTH/4, 0, SCREEN_WIDTH%4, SCREEN_HEIGHT/4);
	
	// gravitiy variable
	private float grav = 0;
	
	/** debug variables */
	private boolean disregardCollisions = false;
	private boolean drawBoundingBoxes = false;
	private boolean vsyncOn = true;
	
	
	public PlayState(int StateID) {
		super();
		GameStateID = StateID;
	}

	@Override
	public int getID() {
		return GameStateID;
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
			music = ResourceManager.getInstance().getMusic("horror_tale");
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
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		container.setVSync(vsyncOn);
		container.setShowFPS(!vsyncOn);
		container.setIcons(ICON_REF_ARRAY);
		
		
		// load tiled map
		//map = new BlockMap("data/level01.tmx");
//		map = new BlockMap("data/testKarte.tmx");
		map = new BlockMap("data/map/Karte_proto.tmx");
		
		// create background image
//		background = new Image("data/area02_bkg0.jpg");
		
		// create PlayerEntity
		playerOne = new Player("./data/assets/spritesheets/teddy_anim.png", "./data/assets/spritesheets/hat_sheet.png", BlockMap.getPlayerStart());
		
		// get an array of all visible map layers
		camera = new Camera(container, BlockMap.tmap, BlockMap.getVisibleLayers());
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		if (container.getInput().isKeyPressed(Input.KEY_ESCAPE))
		{
			container.exit();
		}
		
		/** DEBUG-MODE CHECK INPUT */
		if (container.getInput().isKeyPressed(Input.KEY_F2))
		{
			disregardCollisions = !disregardCollisions;
		}
		if (container.getInput().isKeyPressed(Input.KEY_F3))
		{
			drawBoundingBoxes = !drawBoundingBoxes;
		}
		if (container.getInput().isKeyPressed(Input.KEY_F4))
		{
			vsyncOn = !vsyncOn;
			container.setVSync(vsyncOn);
			container.setShowFPS(!vsyncOn);
		}
		if (container.getInput().isKeyPressed(Input.KEY_F5))
		{
			playerOne.switchHat();
		}
		
		if (container.getInput().isKeyPressed(Input.KEY_C))
		{
			game.enterState(SideScrollerGame.CREDITS_SCREEN_STATEID,
							new FadeOutTransition(Color.white, 1000),
							new FadeInTransition(Color.black, 1000)
			);
		}
		
		/** check if the player is within range to reach any ColorBucket or press any HotButton */
		Polygon playerPoly = playerOne.getBoundingPolygon();
		
		// check if there's a button within the players reach
		playerOne.setWithinHotButtonReach(false);
		for (HotButton button : BlockMap.hotButtonList)
		{
			if (playerPoly.intersects(button.getShape()) || playerPoly.contains(button.getShape()))
			{
				playerOne.setWithinHotButtonReach(true);
				break;
			}
		}
		
		// check if there's a button within the players reach
		playerOne.setWithinColorBucketReach(false);
		for (ColorBucket bucket : BlockMap.colorBucketList)
		{
			if (playerPoly.intersects(bucket.getShape()) || playerPoly.contains(bucket.getShape()))
			{
				playerOne.setWithinColorBucketReach(true);
				break;
			}
		}
		
		for (ColorBucket bucket : BlockMap.colorBucketList)
		{
			bucket.update(delta);
		}
		
		// rotate objects
		for (RotatableObject rotO : BlockMap.rotatableObjectList)
		{
			rotO.startRotation();
			rotO.update(delta);
		}
		
		// move objects
		for (MoveableObject movO : BlockMap.moveableObjectList)
		{
			movO.update(delta);
		}
		
		// gravity simulation
		final float buf = playerOne.getPosition().y;
		final float tempY = buf + delta * grav;
		playerPoly.setY(tempY);
		grav += 0.00225f;
		if (entityCollisionWith(playerPoly))
		{
			playerPoly.setY(buf);
			grav = 0.0f;
		} else {
			playerOne.setY(tempY);
		}
		
		/* Input Handling */
		if (handleInput(container.getInput(), delta))
		{
			playerOne.update(delta);
		}
		else
		{
			playerOne.restartAllAnimations();
		}
		
		//lock the camera on the player by default (player should be centered by the camera)
		//after calculating the positions of all entities
		camera.centerOn(playerPoly.getX(), playerPoly.getY());
	}
	
	private boolean handleInput(Input input, int delta) throws SlickException
	{
		boolean playerHasMoved = false;
		playerOne.setStanding(true);
		
		float playerX = playerOne.getPosition().x;
		float playerY = playerOne.getPosition().y;
		Polygon playerPoly = playerOne.getBoundingPolygon();
		
		if (input.isKeyDown(Input.KEY_LEFT))
		{
			playerOne.setMoveDirection(false);
			playerX -= 2;
			playerPoly.setX(playerX);
			if (entityCollisionWith(playerPoly) && !disregardCollisions){
				playerX += 2;
				playerPoly.setX(playerX);
			} else {
				playerHasMoved = true;
				playerOne.setPosition(playerX, playerY);
			}
		}
		if (input.isKeyDown(Input.KEY_RIGHT))
		{
			playerOne.setMoveDirection(true);
			playerX += 2;
			playerPoly.setX(playerX);
			if (entityCollisionWith(playerPoly) && !disregardCollisions){
				playerX -= 2;
				playerPoly.setX(playerX);
			} else {
				playerHasMoved = true;
				playerOne.setPosition(playerX, playerY);
			}
		}
		if (input.isKeyDown(Input.KEY_UP))
		{
			playerY -= 2;
			playerPoly.setY(playerY);
			if (entityCollisionWith(playerPoly) && !disregardCollisions){
				playerY += 2;
				playerPoly.setY(playerY);
				grav += 0.005f; // collision with ceiling, add gravity (and get a headache..)
			} else {
				playerHasMoved = true;
				playerOne.setPosition(playerX, playerY);
			}
		}
		if (input.isKeyDown(Input.KEY_DOWN))
		{
			playerOne.setStanding(false);
			playerY += 2;
			playerPoly.setY(playerY);
			if (entityCollisionWith(playerPoly) && !disregardCollisions){
				playerY -= 2;
				playerPoly.setY(playerY);
			} else {
				playerHasMoved = true;
				playerOne.setPosition(playerX, playerY);
			}
		}
		
		if (input.isKeyDown(Input.KEY_RETURN) || input.isKeyDown(Input.KEY_ENTER))
		{
			if (playerOne.isWithinColorBucketReach())
			{
				for (ColorBucket bucket : BlockMap.colorBucketList)
				{
					if (playerPoly.intersects(bucket.getShape()) || playerPoly.contains(bucket.getShape()) )
					{
						playerOne.setColor(bucket.getColor());
					}
				}
			}
		}
		
		playerOne.setPosition(playerX, playerY);
		
		return playerHasMoved;
	}
	
	public boolean entityCollisionWith(Polygon polygon) throws SlickException {
		for (Block entity : BlockMap.collisionBlockList)
		{
			if (polygon.intersects(entity.poly))
			{
				return true;
			} 
		}
		for (RotatableObject rotO : BlockMap.rotatableObjectList)
		{
			if(polygon.intersects(rotO.getShape()))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
		// draw background image
		g.fillRect(0.0f, 0.0f, container.getWidth(), container.getHeight());
		//background.draw(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

		//in the render()-method
		camera.drawMap();

		// draw information overlay
		if (playerOne.isWithinHotButtonReach())
		{
			final String str = new String("there's a button!");
			g.drawString(str, ((container.getWidth() >> 1) - (g.getFont().getWidth(str) >> 1)), 10);
		}
		if (playerOne.isWithinColorBucketReach())
		{
			final String str = new String("there's a ColorBucket!!");
			g.drawString(str, ((container.getWidth() >> 1) - (g.getFont().getWidth(str) >> 1)), 10);
		}

		/** --- DEBUG-MODE OVERLAY --- */
		final int lineHeight = g.getFont().getLineHeight();
		int heightMultiplier = 0;
		
		final String creditStr = new String("press C for credits");
		g.drawString(creditStr, ((container.getWidth()) - (g.getFont().getWidth(creditStr))), heightMultiplier * lineHeight);
		++heightMultiplier;
		
		if (disregardCollisions)
		{
			final String str = new String("NoClip mode");
			g.drawString(str, ((container.getWidth()) - (g.getFont().getWidth(str))), heightMultiplier * lineHeight);
			++heightMultiplier;
		}
		if (drawBoundingBoxes)
		{
			final String str = new String("draw BoundingBoxes");
			g.drawString(str, ((container.getWidth()) - (g.getFont().getWidth(str))), heightMultiplier * lineHeight);
			++heightMultiplier;
		}
		if (!vsyncOn)
		{
			final String str = new String("VSync off");
			g.drawString(str, ((container.getWidth()) - (g.getFont().getWidth(str))), heightMultiplier * lineHeight);
			++heightMultiplier;
		}
		
		
		// re-translate everything, so it will be in its real position
		camera.translateGraphics();
		
		
		/** --- DEBUG-MODE DRAW BOUNDING BOXES --- */
		if (drawBoundingBoxes)
		{
			BlockMap.tmap.render(0, 0, BlockMap.tmap.getLayerIndex("Collision"));
			g.setColor(Color.orange);
			g.draw(playerOne.getBoundingPolygon());
		}
		
//		g.setColor(Color.orange);
//		g.setLineWidth(1.0f);
//		g.draw(playerOne.getBoundingPolygon());

		for (StaticSprite spr : BlockMap.staticSpriteList)
		{
			spr.draw(g);
		}
		
		for (HotButton button : BlockMap.hotButtonList)
		{
			button.draw(g);
			if (drawBoundingBoxes) { g.draw(button.getShape()); }
		}
		
		for (ColorBucket bucket : BlockMap.colorBucketList)
		{
			bucket.draw(g);
			if (drawBoundingBoxes) { g.draw(bucket.getShape()); }
		}
		
		for (RotatableObject rotO : BlockMap.rotatableObjectList)
		{
			rotO.draw(g);
			if (drawBoundingBoxes) { g.draw(rotO.getShape()); }
		}
		
		for (MoveableObject movO : BlockMap.moveableObjectList)
		{
			movO.draw(g);
			if (drawBoundingBoxes) { g.draw(movO.getShape()); }
		}
		
		// render player animation
		playerOne.drawAnimation();
		
		/** minimap - not functional... */
//		
//		g.setColor(Color.pink);
//		g.fillRect(miniMapRect.getMinX(), miniMapRect.getMinY(), miniMapRect.getMaxX(), miniMapRect.getMaxY());
//		
//		camera.untranslateGraphics();
//		g.pushTransform();
//		
//		float scaleX = (float)(container.getWidth() >> 2)  / (float)(container.getWidth());
//		float scaleY = (float)(container.getHeight() >> 2) / (float)container.getHeight();
//		int miniMapPosX = (int)(container.getWidth() - (container.getWidth() * scaleX));
//		g.scale(scaleX, scaleY);
//		camera.centerOn(miniMapRect);
//		camera.drawMap();
//		camera.centerOn(playerOne.getBoundingPolygon());
//		
//		g.popTransform();
//		camera.translateGraphics();
		
		// reset graphics device
		g.setColor(Color.white);
		g.resetLineWidth();
		g.resetTransform();
		g.resetFont();
	}
}