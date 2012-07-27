package gdw.gameplay.levelObjects;

import gdw.collisionDetection.CollisionDetectionComponentManager;
import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Message;
import gdw.gameplay.levelObjects.messageType.ActivateMessage;
import gdw.gameplay.levelObjects.messageType.DeactivateMessage;
import gdw.genericBehavior.PivotRotationComponent;

public class RotateBySwitchComponent extends Component
{
	public static final int COMPONENT_TYPE = 27;
	
	private float activateRotation;
	private float deactivateRotation;
	private float angularVelocity;
	
	private int rotatingState;
	private int currentRotationAngle;
	
	private static final int ROTATING_START = 0;
	private static final int ROTATING_END = 1;
	private static final int ROTATING_ACTIVATION = 2;
	private static final int ROTATING_DEACTIVATION = 3;
	
	protected RotateBySwitchComponent(ComponentTemplate template)
	{
		super(template);

		if (template instanceof RotateBySwitchComponentTemplate)
		{
			activateRotation = ((RotateBySwitchComponentTemplate) template).getActivateRotation();
			deactivateRotation = ((RotateBySwitchComponentTemplate) template).getDeactivateRotation();
			angularVelocity = ((RotateBySwitchComponentTemplate) template).getAngularVelocity();
		}
		else
		{
			activateRotation = 0.0f;
			deactivateRotation = 0.0f;
			angularVelocity = 90.0f;
		}
		
		rotatingState = ROTATING_START;
		currentRotationAngle = 0;
	}

	public void onMessage(Message msg)
	{
		PivotRotationComponent pivotComp = (PivotRotationComponent) getOwner().getComponent(PivotRotationComponent.COMPONENT_TYPE);
		if (pivotComp == null) return;
		
		if (msg instanceof ActivateMessage)
		{
			if (rotatingState == ROTATING_START)
			{
				rotatingState = ROTATING_ACTIVATION;
				currentRotationAngle = 0;
			}
		}
		
		if (msg instanceof DeactivateMessage)
		{
			if (rotatingState == ROTATING_END)
			{
				rotatingState = ROTATING_DEACTIVATION;
				currentRotationAngle = 0;
			}
			
		}
	}
	
	@Override
	public int getComponentTypeID()
	{
		return COMPONENT_TYPE;
	}

	public float getActivateRotation()
	{
		return activateRotation;
	}

	public void setActivateRotation(float activateRotation)
	{
		this.activateRotation = activateRotation;
	}

	public float getDeactivateRotation()
	{
		return deactivateRotation;
	}

	public void setDeactivateRotation(float deactivateRotation)
	{
		this.deactivateRotation = deactivateRotation;
	}
	
	///////////////////////////////////////////////////////////////////////
	// Update Methods
	
	public void tick(float deltaTime)
	{
		PivotRotationComponent pivotComp = (PivotRotationComponent) getOwner().getComponent(PivotRotationComponent.COMPONENT_TYPE);
		if (pivotComp == null) return;
		
		if (rotatingState == ROTATING_ACTIVATION)
		{
			float deltaAngle = deltaTime * angularVelocity;
			currentRotationAngle += deltaAngle;	
			if (currentRotationAngle >= activateRotation)
			{
				rotatingState = ROTATING_END;
				deltaAngle -= (currentRotationAngle - activateRotation);
			}
			pivotComp.rotate(deltaAngle);
			if (CollisionDetectionComponentManager.getInstance().usingQuadTree())
				CollisionDetectionComponentManager.getInstance().detectCollisions(getOwner());
		}
		
		else if (rotatingState == ROTATING_DEACTIVATION)
		{
			float deltaAngle = deltaTime * angularVelocity;
			currentRotationAngle += deltaAngle;
			if (currentRotationAngle >= deactivateRotation)
			{
				rotatingState = ROTATING_START;
				deltaAngle -= (currentRotationAngle - activateRotation);
			}
			pivotComp.rotate(-deltaAngle);
			if (CollisionDetectionComponentManager.getInstance().usingQuadTree())
				CollisionDetectionComponentManager.getInstance().detectCollisions(getOwner());
		}
	}

}
