package collisionDetection;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public abstract class CollisionDetectionComponent extends Component
{
	public static final int COMPONENT_TYPE = 4;
	
	public CollisionDetectionComponent(ComponentTemplate template)
	{
		super(template);
	}
	
	protected void destroy()
	{
	}
	
	public int getComponentTypeID()
	{
		return COMPONENT_TYPE;
	}
}
