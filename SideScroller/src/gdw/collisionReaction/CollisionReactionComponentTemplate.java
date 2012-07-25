package gdw.collisionReaction;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class CollisionReactionComponentTemplate extends ComponentTemplate
{

	protected CollisionReactionComponentTemplate(HashMap<String, String> params)
	{
		super(params);
	}

	@Override
	public Component createComponent()
	{
		return new CollisionReactionComponent(this);
	}

}
