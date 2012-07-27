package collisionDetection;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public abstract class CollisionDetectionComponent extends Component
{
	public static final int COMPONENT_TYPE = 4;
	private static int COLLISION_COMPONENT_SUBCLASS_TYPE = -1;

	protected static final int COLLISION_COMPONENT_SUBCLASS_CIRCLE = 0;
	protected static final int COLLISION_COMPONENT_SUBCLASS_AABOX = 1;
	protected static final int COLLISION_COMPONENT_SUBCLASS_OOBOX = 2;

	private CollisionQuadTreeRect treeRect;

	public CollisionDetectionComponent(ComponentTemplate template,
			int subClassType)
	{
		super(template);
		COLLISION_COMPONENT_SUBCLASS_TYPE = subClassType;
		treeRect = null;
	}

	protected void destroy()
	{
	}

	protected int getSubClassType()
	{
		return COLLISION_COMPONENT_SUBCLASS_TYPE;
	}

	public int getComponentTypeID()
	{
		return COMPONENT_TYPE;
	}

	protected void attachTreeRect(CollisionQuadTreeRect treeRect)
	{
		this.treeRect = treeRect;
	}

	protected CollisionQuadTreeRect unAttachTreeRect()
	{
		CollisionQuadTreeRect treeRect = getTreeRect();
		this.treeRect = null;
		return treeRect;
	}

	protected CollisionQuadTreeRect getTreeRect()
	{
		return treeRect;
	}

}
