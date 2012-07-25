package gdw.genericBehavior;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.EntityReference;

public class FollowComponentTemplate extends ComponentTemplate
{
	
	private EntityReference targetEntityID;
	
	public EntityReference getTargetEntityID()
	{
		return targetEntityID; 
	}
	
	protected FollowComponentTemplate(HashMap<String, String> params)
	{
		super(params);
		
		
		targetEntityID = getTargetEntityID();
		
	}

	@Override
	public Component createComponent()
	{
		// TODO Auto-generated method stub
		return new FollowComponent(this);
	}

}
