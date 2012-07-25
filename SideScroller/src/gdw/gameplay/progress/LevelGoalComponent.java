package gdw.gameplay.progress;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Message;

public class LevelGoalComponent extends Component
{
	public static final int COMPONENT_TYPE = 20;
	
	protected LevelGoalComponent(ComponentTemplate template)
	{
		super(template);
	}

	@Override
	public int getComponentTypeID()
	{
		return COMPONENT_TYPE;
	}
	
	public void onMessage(Message msg)
	{
		// TODO: send to level singleton
	}
}
