package gdw.entityCore;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Level {
	//Singleton-Stuff:
	private static Level instance = null;
	
	/**
	 * 
	 * @return null if loadLevel hasn't been called, instance otherwise
	 */
	public static Level getInstance(){
		return instance;
	}
	private Level(String name){
		try {
			map = new TiledMap(name);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private TiledMap map = null;
	
	public int getMapWidth(){
		return map.getWidth();
	}
	
	public int getMapHeight(){
		return map.getHeight();
	}
	
	public TiledMap getMap(){
		return map;
	}
	
	public void loadLevel(String name){
		instance = new Level(name);
	}
	
	public void levelFinished(){
		//TODO: Implement
	}
}
