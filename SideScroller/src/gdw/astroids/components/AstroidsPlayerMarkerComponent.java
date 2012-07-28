package gdw.astroids.components;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class AstroidsPlayerMarkerComponent extends Component
{
	public static final int COMPONENT_TYPE = 33334;
	
	public AstroidsPlayerMarkerComponent(ComponentTemplate template)
	{
		super(template);
	}

	@Override
	public int getComponentTypeID()
	{
		return COMPONENT_TYPE;
	}
}
