package gdw.gameplay.level;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Level {
	private static Level instance=null;
	private TiledMap map;
	private int mapWidth;
	private int mapHeight;
	
	
	private Level(String name)
	{
		try {
			map = new TiledMap(name);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mapWidth = map.getTileWidth()*map.getWidth();
		mapHeight = map.getTileHeight()*map.getHeight();
	}
	
	
	public static void loadLevel(String name)
	{
		instance = new Level(name);
	}
	
	/**
	 * 
	 * @return null if loadLevel hasn't been called,instance otherwise
	 */
	
	public Level getInstance()
	{
		return instance;
	}
}
