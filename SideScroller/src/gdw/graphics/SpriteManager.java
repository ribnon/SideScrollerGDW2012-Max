package gdw.graphics;

import gdw.entityCore.Level;

import java.util.LinkedList;

import org.newdawn.slick.tiled.TiledMap;

public class SpriteManager {
	
	private static SpriteManager instance;
	private LinkedList<SpriteComponent> sprites;
	private LinkedList<CameraComponent> cameras;
	
	private static void initialize()
	{
		instance = new SpriteManager();
	}
	
	private SpriteManager()
	{
		sprites = new LinkedList<SpriteComponent>();
		cameras = new LinkedList<CameraComponent>();
	}
	
	
	public static SpriteManager getInstance()
	{
		if(instance == null)
			initialize();
		return instance;		
	}
	
	
	public void render()
	{
		//TiledMap map = Level.getInstance().getMap();
		//if(map == null) System.out.println("Error: Map not loaded, nullpointer exception imminent :P");
		//int lc = map.getLayerCount();
		
		for(int i=0;i<cameras.size();i++)
		{
			/*
			for(int j = 0; j < lc; ++j)
			{
				if(map.getLayerProperty(j, "invisible", "false").equals("true"))
					continue;
				map.render((int)(-cameras.get(i).getOwner().getPosX()+0.5f), (int)(-cameras.get(i).getOwner().getPosY()+0.5f), j);
					
			}*/
			
			float posX = cameras.get(i).getOwner().getPosX();
			float posY = cameras.get(i).getOwner().getPosY();
			
			for(int j=0;j<sprites.size();j++)
			{
				sprites.get(i).draw(posX,posY);
			}
		}
			
			
	}
	
	public void addSprite(SpriteComponent sprite)
	{
		int i = 0;
		for(; i < sprites.size(); ++i)
		{
			if(sprite.getLayer() >= sprites.get(i).getLayer())
				break;
				
		}
		sprites.add(i,sprite);
	}
	
	public void removeSprite(SpriteComponent sprite)
	{
		sprites.remove(sprite);
	}

	
	public void addCamera(CameraComponent camera)
	{
		cameras.add(camera);
	}
	
	public void removeCamera(CameraComponent camera)
	{
		cameras.remove(camera);
	}
}
