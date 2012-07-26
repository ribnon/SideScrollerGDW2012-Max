package gdw.genericBehavior;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.EntityReference;

public class FollowComponentTemplate extends ComponentTemplate
{
	private EntityReference targetEntityID;	
	
	public FollowComponentTemplate(HashMap<String, String> params)
	{
		super(params);
		
		targetEntityID = getEntityReferenceParam("targetEntityID");
	}

	public Component createComponent()
	{
		return new FollowComponent(this);
	}
	
	public EntityReference getTargetEntityID()
	{
		return targetEntityID; 
	}
}
