package gdw.Gameplay.Player;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.graphics.AnimatedSpriteComponent;

public class DuckableComponentTemplate extends ComponentTemplate
{
	

	protected DuckableComponentTemplate(HashMap<String, String> params)
	{
		super(params);
	}

	@Override
	public Component createComponent()
	{
		return new DuckableComponent(this);
	}


}
