package gdw.network;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Message;

public class NetComponent extends Component
{
	public static final int COMPONENT_TYPE = 8;
	private int sequenceID;

	protected NetComponent(ComponentTemplate template)
	{
		super(template);
		sequenceID = 0;
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
		// TODO unsicher
	}

	public int getSequenceID()
	{
		return sequenceID;
	}
	
	//TODO überprüfung der id sonst dropen

	public void setSequenceID(int sequenceID)
	{
		this.sequenceID = sequenceID;
	}

	public void sendNetworkMessage(Message msg)
	{
		// TODO SubSystem senden
	}

}
