/**
 * The AttachmentComponent class determines which other Entities are currently 
 * attached to the Entity that has this component.
 * 
 * @author Oliver Waidler
 */

package gdw.genericBehavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import collisionDetection.CollisionDetectionMessage;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Entity;
import gdw.entityCore.EntityManager;
import gdw.entityCore.EntityReference;
import gdw.entityCore.Message;
import gdw.entityCore.StaticEntityReference;

public class AttachmentComponent extends Component
{
	/**
	 * The X offset at which each Entity is attached.
	 */
	private float attachPointX = 0;
	/**
	 * The Y offset at which each Entity is attached.
	 */
	private float attachPointY = 0;
	/**
	 * The orientation offset at which each Entity is attached
	 */
	private float attachOrientation = 0;
	/**
	 * The list of all attached Entities. Key is the group ID, Value is an
	 * Entity Reference
	 */
	private HashMap<Integer, EntityReference> attachedEntityID = new HashMap<Integer, EntityReference>();
	/**
	 * A list with the group IDs that should be auto attached on collision.
	 */
	private ArrayList<Integer> autoAttachGroups = new ArrayList<Integer>();
	public static final int COMPONENT_TYPE = 9;

	public AttachmentComponent(ComponentTemplate template)
	{
		super(template);
		attachPointX = template.getFloatParam("attachPointX", 0);
		attachPointY = template.getFloatParam("attachPointY", 0);
		attachOrientation = template.getFloatParam("attachOrientation", 0);

		String[] autoAttachGroupsValues = template.getStringParam(
				"autoAttachGroups", "").split(",");
		for (String i : autoAttachGroupsValues)
		{
			autoAttachGroups.add(Integer.parseInt(i));
		}
	}

	/**
	 * Detaches all AttachableComponents
	 * 
	 * Each associated AttachableComponent gets its attachedToEntityID reseted
	 */
	protected void destroy()
	{
		// Tell all AttachableComponents that they have been detached.
		// Iterate through all elements in the map with attached entity
		for (Entry<Integer, EntityReference> entry : attachedEntityID
				.entrySet())
		{
			Entity currentEntity = EntityManager.getInstance().getEntity(
					entry.getValue().getID());
			AttachableComponent ac = (AttachableComponent) currentEntity
					.getComponent(AttachableComponent.COMPONENT_TYPE);
			ac.setAttachedToEntityID(-1);
		}
	}

	@Override
	public final int getComponentTypeID()
	{
		return COMPONENT_TYPE;
	}

	/**
	 * Attaches an Entity
	 * 
	 * The Entity to attach must have an AttachableComponent. If it does not,
	 * nothing will happen.
	 * 
	 * The attached Entity will have the ID of this Entity set as the
	 * attachedToEntityID in its AttachableComponent.
	 * 
	 * If another Entity has already been attached at the groupID of the
	 * AttachableComponent of the Entity to attach, the old Entity will be
	 * detached.
	 * 
	 * @param entityID
	 *            The entityID to attach.
	 */
	public void attachEntity(EntityReference entityID)
	{
		Entity e = EntityManager.getInstance().getEntity(entityID.getID());
		if (e == null)
			return;

		AttachableComponent ac = (AttachableComponent) e
				.getComponent(AttachableComponent.COMPONENT_TYPE);
		if (ac == null)
			return;

		detachEntity(ac.getGroupID());
		attachedEntityID.put(ac.getGroupID(), entityID);
		ac.setAttachedToEntityID(getOwner().getID());
	}

	/**
	 * Detaches a group.
	 * 
	 * If nothing is attached to this group, nothing happens.
	 * 
	 * Also sets the attachedToEntityID of the associated AttachableComponent.
	 * 
	 * @param groupID
	 *            The group to detach.
	 */
	public void detachEntity(int groupID)
	{
		EntityReference e = attachedEntityID.get(groupID);
		if (e == null)
			return;

		AttachableComponent ac = (AttachableComponent) EntityManager
				.getInstance().getEntity(e.getID())
				.getComponent(AttachableComponent.COMPONENT_TYPE);
		ac.setAttachedToEntityID(-1);
		attachedEntityID.remove(groupID);
	}

	/**
	 * Auto-Attaches an Entity on Collision.
	 * 
	 * Only attaches the Entity if it has an AttachableComponent whose groupID
	 * is listed in the AttachmentComponent's autoAttachGroups
	 * 
	 * @param msg
	 */
	public void onMessage(Message msg)
	{
		if (msg instanceof CollisionDetectionMessage)
		{
			CollisionDetectionMessage collisionMessage = (CollisionDetectionMessage) msg;
			// Get the AttachableComponent from the CollisionMessage
			Entity entityWithAttachable = EntityManager.getInstance()
					.getEntity(collisionMessage.getIDCandidate1());
			if (entityWithAttachable == getOwner())
				entityWithAttachable = EntityManager.getInstance().getEntity(
						collisionMessage.getIDCandidate2());

			AttachableComponent ac = (AttachableComponent) entityWithAttachable
					.getComponent(AttachableComponent.COMPONENT_TYPE);
			if (ac == null)
				return;

			// If the AttachableComponent's groupID matches one in
			// autoAttachGroups, detach the old entity and attach the
			// new entity.
			if (autoAttachGroups.contains(ac.getGroupID()))
			{
				detachEntity(ac.getGroupID());
				attachedEntityID
						.put(ac.getGroupID(), new StaticEntityReference(
								entityWithAttachable.getID()));
				ac.setAttachedToEntityID(getOwner().getID());
			}
		}
	}

	/**
	 * Sets the position of all attached entities.
	 * 
	 * The position is this entity's position + attachPoint
	 * 
	 * @param deltaTime
	 *            is not used
	 */
	public void tick(float deltaTime)
	{
		Entity thisEntity = getOwner();
		// Iterate through all elements in the map with attached entity
		for (Entry<Integer, EntityReference> entry : attachedEntityID
				.entrySet())
		{
			Entity currentEntity = EntityManager.getInstance().getEntity(
					entry.getValue().getID());
			// Set the position and orientation of the attached entity
			currentEntity.setPosX(thisEntity.getPosX() + attachPointX);
			currentEntity.setPosY(thisEntity.getPosY() + attachPointY);
			currentEntity.setOrientation(thisEntity.getOrientation()
					+ attachOrientation);
		}
	}

	public float getAttachPointX()
	{
		return attachPointX;
	}

	public void setAttachPointX(float attachPointX)
	{
		this.attachPointX = attachPointX;
	}

	public float getAttachPointY()
	{
		return attachPointY;
	}

	public void setAttachPointY(float attachPointY)
	{
		this.attachPointY = attachPointY;
	}

	public float getAttachOrientation()
	{
		return attachOrientation;
	}

	public void setAttachOrientation(float attachOrientation)
	{
		this.attachOrientation = attachOrientation;
	}
}
