package gdw.graphics;

import java.util.HashMap;

import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Component;

public class StaticSpriteComponentTemplate extends ComponentTemplate
{
	
	protected StaticSpriteComponentTemplate(HashMap<String, String> p)
	{
		super(p);
	}
	
	public Component createComponent()
	{
		return new StaticSpriteComponent(this);
	}
}

