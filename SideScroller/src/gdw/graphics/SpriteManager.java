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
		
		/*
		Graphics g = null;
		if(sprites.size() == 0)
			return;
		try {
			g = sprites.get(0).getImage().getGraphics();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		*/
		
		for(int i=0;i<cameras.size();i++)
		{
			CameraComponent cc = cameras.get(i);
			float posX = -cc.getOwner().getPosX()+cc.getViewPortX()/2.0f;
			float posY = -cc.getOwner().getPosY()+cc.getViewPortY()/2.0f;
//			float posX = -cc.getOwner().getPosX();
//			float posY = -cc.getOwner().getPosY();
//			System.out.println(posX + ", " + posY + "  " + cc.getOwner().getPosX() + ", " + cc.getOwner().getPosY() + "  " + cc.getViewPortX()/2.0f + ", " + cc.getViewPortY()/2.0f);
			
			for(int j = 0; j < lc; ++j)
			{
				
				if(map.getLayerProperty(j, "invisible", "false").equals("true"))
					continue;
				map.render((int)(posX), (int)(posY), j);
					
			}
				
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
			if(sprite.getLayer() < sprites.get(i).getLayer())
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
