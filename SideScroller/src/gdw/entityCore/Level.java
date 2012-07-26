package gdw.entityCore;

import gdw.network.NetSubSystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;
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
		try {
			BufferedReader rdr = new BufferedReader(new FileReader("levels.txt"));
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("Levelliste nicht gefunden. Bitte levels.txt erstellen.");
		}
	}
	
	private TiledMap map = null;
	private ArrayList<String> mapNames = new ArrayList<String>();
	private int levelIndex=0;
	
	public int getMapWidth(){
		return map.getWidth();
	}
	
	public int getMapHeight(){
		return map.getHeight();
	}
	
	public TiledMap getMap(){
		return map;
	}
	
	public void start(){
		levelIndex=0;
		loadLevel(mapNames.get(levelIndex));
	}
	
	public void loadLevel(String name){
		try {
			map = new TiledMap(name);
		} catch (SlickException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("Levelliste nicht gefunden. Bitte levels.txt erstellen.");
		}	
		EntityManager.getInstance().deleteAllEntities();
		EntityTemplateManager.getInstance().reinitialize();
		try {
			EntityTemplateManager.getInstance().loadEntityTemplates("general.templates");
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("Entity-Templatefile nicht gefunden. Bitte general.templates erstellen.");
		}
	//	EntityTemplateManager.getInstance().loadEntityTemplatesFromLevel();
		try {
			EntityManager.getInstance().loadEntities("general.entities");
		} catch (IOException e) {}
		//EntityManager.getInstance().loadEntitiesFromLevel();
	}
	
	public void levelFinished(){
		if(!EntityManager.getInstance().isOfflineMode()) if(!NetSubSystem.getInstance().isServer()) return;
		levelIndex=(levelIndex+1)%mapNames.size();
		loadLevel(mapNames.get(levelIndex));
	}
}
