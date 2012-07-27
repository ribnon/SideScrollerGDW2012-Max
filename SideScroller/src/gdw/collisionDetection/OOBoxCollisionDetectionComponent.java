package gdw.collisionDetection;

import java.util.Arrays;

import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.EntityConstructedMessage;
import gdw.entityCore.Message;

public class OOBoxCollisionDetectionComponent extends CollisionDetectionComponent
{
	private float halfExtentX;
	private float halfExtentY;
	
	public OOBoxCollisionDetectionComponent(ComponentTemplate template)
	{
		super(template, CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_OOBOX);
		
		if (template instanceof OOBoxCollisionDetectionComponentTemplate)
		{
			halfExtentX = ((OOBoxCollisionDetectionComponentTemplate) template).getHalfExtentX();
			halfExtentY = ((OOBoxCollisionDetectionComponentTemplate) template).getHalfExtentY();
		}
		else
		{
			halfExtentX = 1.0f;
			halfExtentY = 1.0f;
		}
		
		CollisionDetectionComponentManager.getInstance().registerOOBoxCollisionDetectionComponent(this);
	}
	
	protected void destroy()
	{
		CollisionDetectionComponentManager.getInstance().removeOOBoxCollisionDetectionComponent(this);
		super.destroy();
	}
	
	// Getters / Setters
	public float getHalfExtentX()
	{
		return halfExtentX;
	}

	public void setHalfExtentX(float halfExtentX)
	{
		this.halfExtentX = halfExtentX;
	}

	public float getHalfExtentY()
	{
		return halfExtentY;
	}

	public void setHalfExtentY(float halfExtentY)
	{
		this.halfExtentY = halfExtentY;
	}
	
	public void onMessage(Message msg)
	{
		if (msg instanceof EntityConstructedMessage)
			CollisionDetectionComponentManager.getInstance().registerTreeRect(this);
	}

	@Override
	public float[] getDimensions()
	{
//		float diag = (float) Math.sqrt(halfExtentX * halfExtentX + halfExtentY * halfExtentY);
		float angle = (float)Math.toRadians(this.getOwner().getOrientation());
		float cosAngle = (float)Math.cos(angle);
		float sinAngle = (float)Math.sin(angle);
		float[] diag = new float[] {
			cosAngle*(getHalfExtentX()) - sinAngle*(getHalfExtentY()),
			sinAngle*(getHalfExtentX()) + cosAngle*(getHalfExtentY()),
			
			cosAngle*(-getHalfExtentX()) - sinAngle*(getHalfExtentY()),
			sinAngle*(getHalfExtentX()) + cosAngle*(-getHalfExtentY()),
		};
		return new float[] { Math.max(Math.abs(diag[0]),Math.abs(diag[2])), Math.max(Math.abs(diag[1]),Math.abs(diag[3])) };
	}
}
