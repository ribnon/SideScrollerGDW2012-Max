package gdw.gameplay.player.messageType;

import gdw.entityCore.Message;

public class HealthModify extends Message
{
	private static final long serialVersionUID = -8353293199896830032L;
	
	public final float healthModify;
	
	public HealthModify(float healthModify)
	{
		this.healthModify = healthModify;
	}
//amount
}
