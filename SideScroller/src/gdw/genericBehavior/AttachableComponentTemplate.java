package gdw.genericBehavior;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class AttachableComponentTemplate extends ComponentTemplate
{
	private int groupID = -1;
	private int attachedToEntityID = -1;

	public AttachableComponentTemplate(HashMap<String, String> params)
	{
		super(params);
		
		groupID = getIntegerParam("groupID", -1);
		attachedToEntityID = getIntegerParam("attachedToEntity", -1);
	}

	@Override
	public Component createComponent()
	{
		return new AttachableComponent(this);
	}

	public int getGroupID()
	{
		return groupID;
	}

	public int getAttachedToEntityID()
	{
		return attachedToEntityID;
	}
}
