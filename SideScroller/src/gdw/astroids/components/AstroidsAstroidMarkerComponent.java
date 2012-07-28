package gdw.astroids.components;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class AstroidsAstroidMarkerComponent extends Component
{
	public static final int COMPONENT_TYPE = 33335;
	
	public AstroidsAstroidMarkerComponent(ComponentTemplate template)
	{
		super(template);
	}

	@Override
	public int getComponentTypeID()
	{
		return COMPONENT_TYPE;
	}
}