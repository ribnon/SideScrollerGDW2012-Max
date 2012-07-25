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
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getComponentTypeID()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public void onMessage(Message msg)
	{
		
	}
}
