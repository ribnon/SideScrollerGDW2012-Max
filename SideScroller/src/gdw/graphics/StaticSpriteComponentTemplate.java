package gdw.graphics;

import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Component;

public class StaticSpriteComponentTemplate extends ComponentTemplate
{
	private Image image;
	
	public Image getImage()
	{
		return image;
	}

	public StaticSpriteComponentTemplate(HashMap<String, String> params)
	{
		super(params);
		
		try {
			image = new Image(getStringParam("Path", ""));
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Component createComponent()
	{
		return new StaticSpriteComponent(this);
	}
}

