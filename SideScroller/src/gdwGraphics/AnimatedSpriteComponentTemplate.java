package gdwGraphics;

import java.util.HashMap;

import ComponentTemplate;

public class AnimatedSpriteComponentTemplate extends ComponentTemplate
{
	
	protected AnimatedSpriteComponentTemplate(HashMap<String, String> p)
	{
		super(p);
	}
	
	public Component createComponent()
	{
		return new AnimatedSpriteComponent(this);
	}
}
