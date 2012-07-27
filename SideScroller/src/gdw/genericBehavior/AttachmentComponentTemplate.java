package gdw.genericBehavior;

import java.util.ArrayList;
import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.EntityReference;

public class AttachmentComponentTemplate extends ComponentTemplate
{
	private float attachPointX = 0;
	private float attachPointY = 0;
	private float attachOrientation = 0;	
	private ArrayList<Integer> autoAttachGroups = new ArrayList<Integer>();
	

	public AttachmentComponentTemplate(HashMap<String, String> params)
	{
		super(params);
		
		attachPointX = getFloatParam("attachPointX", 0);
		attachPointY = getFloatParam("attachPointY", 0);
		attachOrientation = getFloatParam("attachOrientation", 0);
		
		String[] autoAttachGroupsValues = getStringParam(
				"autoAttachGroups", "").split(",");
		for (String i : autoAttachGroupsValues)
		{
			autoAttachGroups.add(Integer.parseInt(i));
		}
	}

	@Override
	public Component createComponent()
	{
		return new AttachableComponent(this);
	}

	public float getAttachPointX()
	{
		return attachPointX;
	}

	public float getAttachPointY()
	{
		return attachPointY;
	}

	public float getAttachOrientation()
	{
		return attachOrientation;
	}

	public ArrayList<Integer> getAutoAttachGroups()
	{
		return autoAttachGroups;
	}
}
