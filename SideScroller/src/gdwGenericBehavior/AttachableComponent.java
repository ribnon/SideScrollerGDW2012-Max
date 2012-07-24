/**
 * An Entity with an AttachableComponent can be attached to another Entity
 * that has a AttachmentComponent
 * @author Oliver Waidler
 */

package gdwGenericBehavior;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Entity;
import gdw.entityCore.EntityManager;

public class AttachableComponent extends Component
{
	/**
	 * The group ID of this attachable. If the groupID is listed in an
	 * AttachmentComponent's autoAttachGroups, it will automatically be attached
	 * on collision.
	 */
	private int groupID = -1;

	/**
	 * The ID of the entity this attachable is currently attached to. If this
	 * attachable is currently not attached to anything, it is -1
	 */
	private int attachedToEntityID = -1;
	public static final int COMPONENT_TYPE = 9;

	public AttachableComponent(ComponentTemplate template)
	{
		super(template);
	}

	/**
	 * Detach this Entity from the Entity that it is attached to.
	 * 
	 * @author Oliver Waidler
	 */
	protected void destroy()
	{
		if (attachedToEntityID == -1)
			return;
		// Remove the Entity of this AttachableComponent from the
		// AttachmentComponent
		Entity e = EntityManager.getInstance().getEntity(attachedToEntityID);
		AttachmentComponent ac = (AttachmentComponent) e
				.getComponent(AttachmentComponent.COMPONENT_TYPE);
		ac.detachEntity(groupID);
	}

	@Override
	public final int getComponentTypeID()
	{
		return COMPONENT_TYPE;
	}

	public int getGroupID()
	{
		return groupID;
	}

	public int getAttachedToEntityID()
	{
		return attachedToEntityID;
	}

	public void setGroupID(int newID)
	{
		groupID = newID;
	}

	public void setAttachedToEntityID(int newID)
	{
		attachedToEntityID = newID;
	}

}
