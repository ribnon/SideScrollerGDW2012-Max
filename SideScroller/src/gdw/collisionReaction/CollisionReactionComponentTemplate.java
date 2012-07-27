package gdw.collisionReaction;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class CollisionReactionComponentTemplate extends ComponentTemplate
{

	private boolean impassableFromTop = true;
	private boolean impassableFromSide = true;
	public boolean isImpassableFromTop() {
		return impassableFromTop;
	}

	public boolean isImpassableFromSide() {
		return impassableFromSide;
	}
	
	public CollisionReactionComponentTemplate(HashMap<String, String> params)
	{
		super(params);
		impassableFromTop = (getIntegerParam("impassableFromTop",1) == 1);
		impassableFromSide = (getIntegerParam("impassableFromSide",1) == 1);
	}

	@Override
	public Component createComponent()
	{
		return new CollisionReactionComponent(this);
	}

}
