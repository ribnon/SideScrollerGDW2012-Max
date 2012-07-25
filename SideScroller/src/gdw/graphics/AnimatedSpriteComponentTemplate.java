package gdw.graphics;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

import java.util.HashMap;


public class AnimatedSpriteComponentTemplate extends ComponentTemplate
{
	
	protected AnimatedSpriteComponentTemplate(HashMap<String, String> params)
	{
		super(params);
	}
	
	public Component createComponent()
	{
		return new AnimatedSpriteComponent(this);
	}
}
