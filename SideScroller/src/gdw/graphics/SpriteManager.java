package gdw.graphics;

import java.util.LinkedList;

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
		for(int i=0;i<cameras.size();i++)
		{
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
