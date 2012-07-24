package gdw.network;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Message;

public class NetComponent extends Component
{
	public static final int COMPONENT_TYPE = 8;
	
	private final G

	protected NetComponent(ComponentTemplate template)
	{
		super(template);
	}

	@Override
	public int getComponentTypeID()
	{
		return 8;
	}
	
	@Override
	protected void destroy()
	{
		super.destroy();
	}
	
	public void sendNetworkMessage(Message msg)
	{
		//TODO SubSystem senden
	}

}
