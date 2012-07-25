package gdw.network;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

import java.util.HashMap;

public class NetComponentTemplate extends ComponentTemplate
{
	private Ghost ghost;
	
	protected NetComponentTemplate(HashMap<String, String> params) 
	{
		super(params);
		float posX = getFloatParam("posX",0.0f);
		float posY = getFloatParam("posY",0.0f);
		float velocityX = getFloatParam("velocityX",0.0f);
		float velocityY = getFloatParam("velocityY",0.0f);
		this.ghost = new Ghost(posX, posY, velocityX, velocityY);
	}
	
	
	public Ghost getGhost()
	{
		return ghost;
	}

	@Override
	public Component createComponent()
	{
		return new NetComponent(this);
	}
}
