package gdw.gameplay.levelObjects;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class PathFollowComponentTemplate extends ComponentTemplate
{

	public PathFollowComponentTemplate(HashMap<String, String> params)
	{
		super(params);
	}

	@Override
	public Component createComponent()
	{
		return new PathFollowComponent(this);
	}

}
