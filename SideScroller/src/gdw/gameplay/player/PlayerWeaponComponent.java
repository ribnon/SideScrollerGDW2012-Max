package gdw.gameplay.player;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Message;

public class PlayerWeaponComponent extends Component
{
	private GameColor currentColor;
	public static final int COMPONENT_TYPE = 11;
	
	protected PlayerWeaponComponent(ComponentTemplate template)
	{
		super(template);
		currentColor = new GameColor();
	}

	@Override
	public int getComponentTypeID()
	{
		return COMPONENT_TYPE;
	}

	public void onMessage(Message msg)
	{
		if (msg instanceof ColorableComponentMessage)
		{
			ColorableComponentMessage cMsg = (ColorableComponentMessage) msg;
			currentColor.mix(cMsg.getGameColor());
		}
		else if(msg instanceof ColorSourceComponent)
		{
			ColorSourceComponentMessage cMsg = (ColorableComponentMessage) msg;
			currentColor = cMsg.getGameColor();
		}
	}
}
