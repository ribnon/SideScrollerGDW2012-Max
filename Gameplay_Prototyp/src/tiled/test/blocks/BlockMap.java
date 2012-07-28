package tiled.test.blocks;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

import tiled.test.Game;
import tiled.test.entities.ColorBucket;
import tiled.test.entities.HotButton;
import tiled.test.entities.MoveableObject;
import tiled.test.entities.MovementDirection;
import tiled.test.entities.RotatableObject;
import tiled.test.entities.StaticSprite;
 
public class BlockMap {
	public static TiledMap tmap;
	public static int mapWidth;
	public static int mapHeight;
	private final int square[] = {1,1,15,1,15,15,1,15}; //square shaped tile
	private static Integer visibleLayers[]; // array, which layers should be rendered
	public static ArrayList<Block> collisionBlockList;
	public static ArrayList<HotButton> hotButtonList;
	public static ArrayList<ColorBucket> colorBucketList;
	public static ArrayList<RotatableObject> rotatableObjectList;
	public static ArrayList<MoveableObject> moveableObjectList;
	public static ArrayList<StaticSprite> staticSpriteList;
	private static Vector2f playerStartVector;
	
	public static Vector2f getPlayerStart()
	{
		return playerStartVector;
	}
	
	public static Integer[] getVisibleLayers()
	{
		return visibleLayers.clone();
	}
	
	public BlockMap(String ref) throws SlickException {
		// ArrayList initializations
		collisionBlockList  = new ArrayList<Block>();
		hotButtonList       = new ArrayList<HotButton>();
		colorBucketList     = new ArrayList<ColorBucket>();
		rotatableObjectList = new ArrayList<RotatableObject>();
		moveableObjectList  = new ArrayList<MoveableObject>();
		staticSpriteList    = new ArrayList<StaticSprite>();
		
		tmap            = new TiledMap(ref, "data/map");
		mapWidth        = tmap.getWidth() * tmap.getTileWidth();
		mapHeight       = tmap.getHeight() * tmap.getTileHeight();
		
		// create an ArrayList of all visible layers
		ArrayList<Integer> tempList = new ArrayList<Integer>();
		for (int i = 0; i < tmap.getLayerCount(); ++i)
		{
			if ("false".equals(tmap.getLayerProperty(i, "invisible", "false")))
			{
				tempList.add(i);
			}
		}
		// cast this ArrayList to an Array of Integer
		tempList.trimToSize();
		visibleLayers = new Integer[tempList.size()];
		tempList.toArray(visibleLayers);
		tempList.clear();
 
		// check for the layer named "Collision" (case-sensitive) and create collision geometry
		final int layerIndex = tmap.getLayerIndex("Collision");
		for (int x = 0; x < tmap.getWidth(); ++x) {
			for (int y = 0; y < tmap.getHeight(); ++y) {
				int tileID = tmap.getTileId(x, y, layerIndex); // layerIndex == collision layer
				if (tileID == 1) {
					collisionBlockList.add( new Block(x * 16, y * 16, square, "square") );
				}
			}
		}
		
		for (int groupID = 0; groupID < tmap.getObjectGroupCount(); ++groupID)
		{
			for (int objectID = 0; objectID < tmap.getObjectCount(groupID); ++objectID)
			{
				final String type = tmap.getObjectType(groupID, objectID);
				// if/else für alle Objekttypen
				if (type.equals("PlayerStart"))
				{
					playerStartVector = new Vector2f(
							tmap.getObjectX(groupID, objectID),
							tmap.getObjectY(groupID, objectID) //+ tmap.getObjectHeight(groups, count)
							);
				}
				else if (type.equals("HotButton"))
				{
					final Vector2f position = new Vector2f(tmap.getObjectX(groupID, objectID), tmap.getObjectY(groupID, objectID));
					final Color color = new Color(Color.decode(tmap.getObjectProperty(groupID, objectID, "color", "0x00000")));
					final String entityRef = tmap.getObjectProperty(groupID, objectID, "Switch.targetEntity", null);
					final String baseImageRef = tmap.getObjectProperty(groupID, objectID, "OverlayedAnimatedSprite.baseSpritesheet", null);
					final String overlayImageRef = tmap.getObjectProperty(groupID, objectID, "OverlayedAnimatedSprite.overlaySpritesheet1", null);
					
					hotButtonList.add(new HotButton(baseImageRef, overlayImageRef, position, color, entityRef));
				}
				else if (type.equals("ColorBucket"))
				{
					final Vector2f position = new Vector2f(tmap.getObjectX(groupID, objectID), tmap.getObjectY(groupID, objectID));

					final String sheetRef = tmap.getObjectProperty(groupID, objectID, "OverlayedAnimatedSprite.baseSpritesheet", null);
					final String overlaySheetRef = tmap.getObjectProperty(groupID, objectID, "OverlayedAnimatedSprite.overlaySpritesheet1", null);
					final boolean flipped = "true".equals(tmap.getObjectProperty(groupID, objectID, "OverlayedAnimatedSprite.flipped", "false"));
					final int[] cycles = new int[]
							{
								Integer.parseInt(tmap.getObjectProperty(groupID, objectID, "OverlayedAnimatedSprite.cycleLength", "1").substring(0, 1)),
								Integer.parseInt(tmap.getObjectProperty(groupID, objectID, "OverlayedAnimatedSprite.cycleLength", "1").substring(2, 3))
							};
					final int tileWidth  = Integer.parseInt(tmap.getObjectProperty(groupID, objectID, "OverlayedAnimatedSprite.tileWidth", "1"));
					final int tileHeight = Integer.parseInt(tmap.getObjectProperty(groupID, objectID, "OverlayedAnimatedSprite.tileHeight", "1"));
					
					final Color color = new Color(Color.decode(tmap.getObjectProperty(groupID, objectID, "color", "0xCCCCCC"))); 
					colorBucketList.add(new ColorBucket(sheetRef, overlaySheetRef, cycles, tileWidth, tileHeight, flipped, position, color));
				}
				else if (type.equals("MoveableObject"))
				{
					final int x     = tmap.getObjectX(groupID, objectID)+1;
					final int y     = tmap.getObjectY(groupID, objectID)+1;
					final int x_max = x + tmap.getObjectWidth(groupID, objectID)-1;
					final int y_max = y + tmap.getObjectHeight(groupID, objectID)-1;
					
					final float box[] = {x,y, x_max,y, x_max,y_max, x,y_max};
					final Polygon p = new Polygon(box);
					
					final Vector2f minPosition = new Vector2f(16 * Float.parseFloat(tmap.getObjectProperty(groupID, objectID, "minPositionX", "0")),
															  16 * Float.parseFloat(tmap.getObjectProperty(groupID, objectID, "minPositionY", "0")));
					final Vector2f maxPosition = new Vector2f(16 * Float.parseFloat(tmap.getObjectProperty(groupID, objectID, "maxPositionX", "0")),
							  								  16 * Float.parseFloat(tmap.getObjectProperty(groupID, objectID, "maxPositionY", "0")));
					final float acceleration = Float.parseFloat(tmap.getObjectProperty(groupID, objectID, "acceleration", "0"));
					final MovementDirection initialDirection = MovementDirection.valueOf(tmap.getObjectProperty(groupID, objectID, "initialDirection", "NONE"));
					moveableObjectList.add(new MoveableObject(p, minPosition, maxPosition, acceleration, initialDirection));
				}
				else if (type.equals("RotatableObject"))
				{
					final int x     = tmap.getObjectX(groupID, objectID)+1;
					final int y     = tmap.getObjectY(groupID, objectID)+1;
					final int x_max = x + tmap.getObjectWidth(groupID, objectID)-1;
					final int y_max = y + tmap.getObjectHeight(groupID, objectID)-1;
					
					final float box[] = {x,y, x_max,y, x_max,y_max, x,y_max};
					final Polygon p = new Polygon(box);
					
					final Color color = new Color(Color.decode(tmap.getObjectProperty(groupID, objectID, "color", "0x00000")));
					
					//final float pivotX = x_max * Float.parseFloat(tmap.getObjectProperty(groups, count, "pivotX", "0"));
					//final float pivotY = y_max * Float.parseFloat(tmap.getObjectProperty(groups, count, "pivotY", "0"));
					final float pivotX = Float.parseFloat(tmap.getObjectProperty(groupID, objectID, "pivotX", "0"));
					final float pivotY = Float.parseFloat(tmap.getObjectProperty(groupID, objectID, "pivotY", "0"));
					final float acceleration = Float.parseFloat(tmap.getObjectProperty(groupID, objectID, "acceleration", "0"));
					final MovementDirection direction = MovementDirection.valueOf(tmap.getObjectProperty(groupID, objectID, "direction", "NONE"));
					final String entityRef = tmap.getObjectProperty(groupID, objectID, "triggeredBy", "");
					rotatableObjectList.add(new RotatableObject(p, color, pivotX, pivotY, acceleration, direction, entityRef));
				}
				else if (type.equals("StaticSprite"))
				{
					final String imageRef = tmap.getObjectProperty(groupID, objectID, "StaticSprite.image", Game.DEFAULT_IMAGE_PATH);
					final Vector2f position = new Vector2f(tmap.getObjectX(groupID, objectID)-1, tmap.getObjectY(groupID, objectID)-1);
					
					staticSpriteList.add(new StaticSprite(imageRef, position, Color.white));
				}
				else if (true)
				{
					
				}
			}
		}
	}
}