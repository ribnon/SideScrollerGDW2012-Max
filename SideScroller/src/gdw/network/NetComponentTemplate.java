package gdw.network;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

import java.util.HashMap;

public class NetComponentTemplate extends ComponentTemplate
{

	public NetComponentTemplate(HashMap<String, String> params) 
	{
		super(params);

	}

	@Override
	public Component createComponent()
	{
		return new NetComponent(this);
	}
}
