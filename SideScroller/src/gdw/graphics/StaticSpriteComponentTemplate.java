package gdw.graphics;

import java.util.HashMap;

import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Component;

public class StaticSpriteComponentTemplate extends ComponentTemplate
{
	
	protected StaticSpriteComponentTemplate(HashMap<String, String> params)
	{
		super(params);
	}
	
	public Component createComponent()
	{
		return new StaticSpriteComponent(this);
	}
}

