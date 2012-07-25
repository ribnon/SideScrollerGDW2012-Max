package gdw.gameplay.progress;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class StartSpawnComponent extends Component
{
	public static final int COMPONENT_TYPE = 19;
	
	protected StartSpawnComponent(ComponentTemplate template)
	{
		super(template);
	}

	@Override
	public int getComponentTypeID()
	{
		return COMPONENT_TYPE;
	}
}
