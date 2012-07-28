package gdw.gameplay.shooter;

import gdw.collisionDetection.CollisionDetectionComponentManager;
import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.EntityConstructedMessage;
import gdw.entityCore.Message;
import gdw.utils.LinearIstGut;

public class ProjectileComponent extends Component
{
	public static final int COMPONENT_TYPE = 1101;
	
	private float startSpeed;
	private float endSpeed;
	private float acceleration;
	private float localOffsetX;
	private float localOffsetY;
	private float localOffsetAngular;
	private float distance;
	
	private float currentSpeed;
	private double[] direction;
	
	private float[] startPos;
	
	public ProjectileComponent(ComponentTemplate template)
	{
		super(template);
		
		ProjectileComponentTemplate t = (ProjectileComponentTemplate) template;
		startSpeed = t.getStartSpeed();
		endSpeed = t.getEndSpeed();
		acceleration = t.getAcceleration();
		localOffsetX = t.getLocalOffsetX();
		localOffsetY = t.getLocalOffsetY();
		localOffsetAngular = t.getLocalOffsetAngular();
		distance = t.getDistance();
		
		direction = new double[] { 0.0f, -1.0f };
		startPos = new float[] { 0.0f, 0.0f };
		distance = distance * distance;
		
		currentSpeed = startSpeed;
		
		ProjectileComponentManager.getInstance().registerProjectileComponent(this);
	}
	
	@Override
	public int getComponentTypeID()
	{
		return COMPONENT_TYPE;
	}

	public void onMessage(Message msg)
	{
		if (msg instanceof EntityConstructedMessage)
		{
			float angle = (float) Math.toRadians(getOwner().getOrientation() + localOffsetAngular);
			
			double rotation[][] = new double[][] {{ (float) Math.cos(angle), (float) -Math.sin(angle) }, 
												  { (float) Math.sin(angle), (float)  Math.cos(angle) }};
			
			direction = LinearIstGut.matMult(rotation, direction);
			
			double offset[] = new double[] { localOffsetX, localOffsetY };
			
			offset = LinearIstGut.matMult(rotation, offset);
			
			getOwner().setPosX(getOwner().getPosX() + (float) offset[0]);
			getOwner().setPosY(getOwner().getPosY() + (float) offset[1]);
			
			startPos[0] = getOwner().getPosX();
			startPos[1] = getOwner().getPosY();
		}
	}
	
	public void simulate(float deltaTime)
	{
		currentSpeed += (deltaTime) * acceleration;
		
		if (currentSpeed > endSpeed)
		{
			acceleration = 0.0f;
			currentSpeed = endSpeed;
		}
		
		float posX = getOwner().getPosX() + (float) direction[0] * currentSpeed * deltaTime;
		float posY = getOwner().getPosY() + (float) direction[1] * currentSpeed * deltaTime;
		getOwner().setPos(posX, posY);
		
		float distX = getOwner().getPosX() - startPos[0];
		float distY = getOwner().getPosY() - startPos[1];
		
		if (distX * distX + distY * distY > distance) getOwner().markForDestroy();
		

	}
	
	////////////////////////////////////////////////////////////////////
	// Getters & Setters

	public float getStartSpeed()
	{
		return startSpeed;
	}

	public void setStartSpeed(int startSpeed)
	{
		this.startSpeed = startSpeed;
	}

	public float getEndSpeed()
	{
		return endSpeed;
	}

	public void setEndSpeed(int endSpeed)
	{
		this.endSpeed = endSpeed;
	}

	public float getAcceleration()
	{
		return acceleration;
	}

	public void setAcceleration(float acceleration)
	{
		this.acceleration = acceleration;
	}

	public float getLocalOffsetX()
	{
		return localOffsetX;
	}

	public void setLocalOffsetX(float localOffsetX)
	{
		this.localOffsetX = localOffsetX;
	}

	public float getLocalOffsetY()
	{
		return localOffsetY;
	}

	public void setLocalOffsetY(float localOffsetY)
	{
		this.localOffsetY = localOffsetY;
	}

	public float getLocalOffsetAngular()
	{
		return localOffsetAngular;
	}

	public void setLocalOffsetAngular(float localOffsetAngular)
	{
		this.localOffsetAngular = localOffsetAngular;
	}

	public float getDistance()
	{
		return distance;
	}

	public void setDistance(float distance)
	{
		this.distance = distance;
	}
}
