package gdw.graphics;

import gdw.entityCore.Level;

import java.util.LinkedList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
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
		TiledMap map = Level.getInstance().getMap();
		int lc = 0;
		if(map != null) 
		{
			lc = map.getLayerCount();
		}
		
		Graphics g = null;
		if(sprites.size() == 0)
			return;
		try {
			g = sprites.get(0).getImage().getGraphics();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		for(int i=0;i<cameras.size();i++)
		{
			
			for(int j = 0; j < lc; ++j)
			{
				if(map.getLayerProperty(j, "invisible", "false").equals("true"))
					continue;
				map.render((int)(-cameras.get(i).getOwner().getPosX()+0.5f), (int)(-cameras.get(i).getOwner().getPosY()+0.5f), j);
					
			}
			
			float posX = cameras.get(i).getOwner().getPosX();
			float posY = cameras.get(i).getOwner().getPosY();
			
			//Verschiedene Cameras mit unterschiedlich großen viewports zu haben ist eine doofe Idee !
			//g.setClip(i*cameras.get(i).getViewPortX(), i*cameras.get(i).getViewPortY(), cameras.get(i).getViewPortX(), cameras.get(i).getViewPortY());
			for(int j=0;j<sprites.size();j++)
			{
				sprites.get(j).draw(posX,posY);
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
