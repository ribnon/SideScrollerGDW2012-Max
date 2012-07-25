package gdw.gameplay.progress;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class StartSpawnComponent extends Component
{
	public static final int COMPONENT_TYPE = 19;
	
	protected StartSpawnComponent(ComponentTemplate template)
	{
		super(template);
		GameplayProgressManager.getInstance().setStartSpawnComponent(this); //add as fallback spawnpoint, in case no rainbows have been activated
	}

	@Override
	public int getComponentTypeID()
	{
		return COMPONENT_TYPE;
	}
}
