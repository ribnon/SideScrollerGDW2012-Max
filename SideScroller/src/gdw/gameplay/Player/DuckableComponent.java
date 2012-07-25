package gdw.Gameplay.Player;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import collisionDetection.AABoxCollisionDetectionComponent;
import collisionDetection.CircleCollisionDetectionComponent;
import collisionDetection.OOBoxCollisionDetectionComponent;

public class DuckableComponent extends Component
{
	public static final int COMPONENT_TYPE_ID = 13;
	
	/**
	 * true if the component ducks
	 */
	private boolean ducked = false;
	
	/**
	 * the size for the ducked component for x
	 */
	private float duckedSizeX;

	/**
	 * the size for the ducked component for y
	 */
	private float duckedSizeY;
	
	/**
	 * the original not-ducked size for x
	 */
	private final float originalSizeX;

	/**
	 * the original not-ducked size for y
	 */
	private final float originalSizeY;
	
	
	protected DuckableComponent(ComponentTemplate template)
	{
		super(template);
		duckedSizeX = template.getFloatParam("duckedSizeX");
		duckedSizeY = template.getFloatParam("duckedSizeY");
		
		Component tempComp = getOwner().getComponent(4);
		if (tempComp instanceof AABoxCollisionDetectionComponent)
		{
			originalSizeX = ((AABoxCollisionDetectionComponent) tempComp).getHalfExtentX();
			originalSizeY = ((AABoxCollisionDetectionComponent) tempComp).getHalfExtentY();
		}
		else if (tempComp instanceof OOBoxCollisionDetectionComponent)
		{
			originalSizeX = ((OOBoxCollisionDetectionComponent) tempComp).getHalfExtentX();
			originalSizeY = ((OOBoxCollisionDetectionComponent) tempComp).getHalfExtentY();
		}
		else
		{
			originalSizeX = ((CircleCollisionDetectionComponent) tempComp).getRadius();
			originalSizeY = 0;
		}

	}

	public int getComponentTypeID()
	{
		return COMPONENT_TYPE_ID;
	}
	
	public boolean isDucked()
	{
		return ducked;
	}

	/**
	 * 	change the values of the collisionbox if parameter isn't eqal the member ducked
	 * 
	 * @param ducked set true if character should duck
	 */
	public void setDucked(boolean ducked)
	{
		if (this.ducked == false && ducked)
		{
			this.ducked = ducked;
			Component tempComp = getOwner().getComponent(4);
			if (tempComp instanceof AABoxCollisionDetectionComponent)
			{
				((AABoxCollisionDetectionComponent) tempComp).setHalfExtentX(duckedSizeX);
				((AABoxCollisionDetectionComponent) tempComp).setHalfExtentY(duckedSizeY);
			}
			else if (tempComp instanceof OOBoxCollisionDetectionComponent)
			{
				((OOBoxCollisionDetectionComponent) tempComp).setHalfExtentX(duckedSizeX);
				((OOBoxCollisionDetectionComponent) tempComp).setHalfExtentY(duckedSizeY);
			}
			else
			{
				((CircleCollisionDetectionComponent) tempComp).setRadius(duckedSizeX);
			}
		}
		else if (this.ducked == false && ducked == false)
		{
			return;
		}
		else if (this.ducked && ducked)
		{
			return;
		}
		else //this.ducked && ducked == false
		{
			this.ducked = ducked;
			Component tempComp = getOwner().getComponent(4);
			if (tempComp instanceof AABoxCollisionDetectionComponent ||
					tempComp instanceof OOBoxCollisionDetectionComponent)
			{
				((AABoxCollisionDetectionComponent) tempComp).setHalfExtentX(originalSizeX);
				((AABoxCollisionDetectionComponent) tempComp).setHalfExtentY(originalSizeY);
			}
			else if (tempComp instanceof OOBoxCollisionDetectionComponent)
			{
				((OOBoxCollisionDetectionComponent) tempComp).setHalfExtentX(originalSizeX);
				((OOBoxCollisionDetectionComponent) tempComp).setHalfExtentY(originalSizeY);
			}
			else
			{
				((CircleCollisionDetectionComponent) tempComp).setRadius(originalSizeX);
			}
		}
		
	}

	public float getDuckedSizeX()
	{
		return duckedSizeX;
	}

	public void setDuckedSizeX(float duckedSizeX)
	{
		this.duckedSizeX = duckedSizeX;
	}

	public float getDuckedSizeY()
	{
		return duckedSizeY;
	}

	public void setDuckedSizeY(float duckedSizeY)
	{
		this.duckedSizeY = duckedSizeY;
	}
}
