package gdw.entityCore;

import org.newdawn.slick.tiled.TiledMap;

public class Level {
	//Singleton-Stuff:
	private static Level instance = null;
	public static Level getInstance(){
		if(instance==null){
			instance = new Level();
		}
		return instance;
	}
	private Level(){
		//TODO: Implement
	}
	
	private TiledMap map = null;
	
	public void loadLevel(String name){
		//TODO: Implement
	}
	
	public void levelFinished(){
		//TODO: Implement
	}
}
