package gdw.collisionReaction;

import collisionDetection.CollisionDetectionMessage;

import Physics.SimulationComponent;
import Physics.SimulationComponentManager;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Entity;
import gdw.entityCore.EntityManager;
import gdw.entityCore.Message;

public class CollisionReactionComponent extends Component
{
	public static final int COMPONENT_TYPE = 5;
	private boolean impassableFromTop = true;
	private boolean impassableFromSide = true;
	private static float VERTICAL_ANGLE_LIMIT = 0.2F;

	protected CollisionReactionComponent(ComponentTemplate template)
	{
		super(template);

		impassableFromTop = (template.getIntegerParam("impassableFromTop") == 1);
		impassableFromSide = (template.getIntegerParam("impassableFromSide") == 1);
	}

	@Override
	public int getComponentTypeID()
	{
		return COMPONENT_TYPE;
	}

	public void destroy()
	{
	}

	/**
	 * If the passed Message is a CollisionDetectionMessage, perform collision
	 * with this Entity as simulated Object (with a SimulationComponent) and the
	 * other Entity as an unsimulated Object. If this Entity has no
	 * SimulationComponent, the collision will not be handled. Since the
	 * CollisionDetectionMessage is passed to both collision partners the
	 * collision can still be handled at the CollisionReactionComponent of the
	 * other Entity.
	 * 
	 * @param msg
	 *            Only CollisionDetectionMessages will be handled. If it is not
	 *            a CollisionDetectionMessage, it will be ignored.
	 */
	public void onMessage(Message msg)
	{
		if (msg instanceof CollisionDetectionMessage)
		{
			CollisionDetectionMessage collisionMessage = (CollisionDetectionMessage) msg;
			Entity owner = getOwner();
			SimulationComponent ownerSimulation = (SimulationComponent) owner
					.getComponent(SimulationComponent.COMPONENT_TYPE);
			// Abort if no owner simulation (unable to set velocity later)
			if (ownerSimulation == null)
				return;

			int collided1 = collisionMessage.getIDCandidate1();
			// Entity collided2 =
			// EntityMananger.getEntity(collisionMessage.getID2());
			Entity other;
			if (collided1 == owner.getID())
				other = EntityManager.getInstance().getEntity(
						collisionMessage.getIDCandidate2());
			else
				other = EntityManager.getInstance().getEntity(collided1);

			reactToCollision(ownerSimulation, other);
		}
	}

	/**
	 * Adjusts the simulatedObject's velocity so the collision will not happen
	 * again. The simulatedObject will also be pushed out of the staticObject
	 * 
	 * @param simulatedObject
	 * @param staticObject
	 */
	private void reactToCollision(SimulationComponent simulatedObject,
			Entity staticObject)
	{
		float diffX = simulatedObject.getOwner().getPosX()
				- staticObject.getPosX();
		float diffY = simulatedObject.getOwner().getPosY()
				- staticObject.getPosY();
		float ratio = 1;
		if (diffY != 0)
		{
			// Normalize the vector between the two objects
			float length = getLength(diffX, diffY);
			ratio = (diffX / length) / (diffY / length);
		}
		// If the entity is penetrable from the top (but not the sides) and the
		// simulated object is moving downwards and is above the static object
		if (impassableFromTop
				&& !impassableFromSide
				&& (simulatedObject.getVelocityY() < 0 || ratio > VERTICAL_ANGLE_LIMIT))
			return;

		float deltaTime = SimulationComponentManager.getInstance()
				.getDeltaTime();
		// Push the simulated Entity out of the static Entity
		simulatedObject.getOwner().setPos(
				-simulatedObject.getVelocityX() * deltaTime,
				-simulatedObject.getVelocityY() * deltaTime);
		float veloX = simulatedObject.getVelocityX();
		float veloY = simulatedObject.getVelocityY();

		// project the velocity onto the vector between the entities
		// p = dot(a, b) / dot (b,b) * b
		float scalar = (veloX * diffX + veloY * diffY)
				/ (diffX * diffX + diffY * diffY);
		float projectedX = scalar * diffX;
		float projectedY = scalar * diffY;

		// This vector is subracted from the velocity of the simulated object
		// to get a velocity that will not move the simulated object into the
		// static object
		simulatedObject.setVelocity(veloX - projectedX, veloY - projectedY);
	}

	private float getLength(float x, float y)
	{
		return (float) Math.sqrt(x * x + y * y);
	}

	public boolean isImpassableFromTop()
	{
		return impassableFromTop;
	}

	public boolean isImpassableFromSides()
	{
		return impassableFromSide;
	}

	public void setImpassableFromTop(boolean b)
	{
		impassableFromTop = b;
	}

	public void setImpassableFromSides(boolean b)
	{
		impassableFromSide = b;
	}

}
