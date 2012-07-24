/**
* An Entity with an AttachableComponent can be attached to another Entity
* that has a AttachmentComponent
* @author Oliver Waidler
*/ 

package gdwGenericBehavior;

import gdwComponent.Component;
import gdwComponent.ComponentTemplate;

public class AttachableComponent extends Component
{
	private int groupID = -1;
	private int attachedToEntityID = -1; // TODO: default values?

	public AttachableComponent(ComponentTemplate template)
	{
		super(template);
	}

	/**
	 * Detach this Entity from the Entity that it is attached to.
	 * @author Oliver Waidler
	 */
	protected void destroy()
	{
		// Remove the Entity of this AttachableComponent from the
		// AttachmentComponent
		Entity e = EntityManager.getEntity(attachedToEntityID);
		AttachmentComponent ac = e.getComponent(AttachmentComponent
				.getComponentID());
		ac.detachEntity(groupID);
	}

	public static final int getComponentID()
	{
		return 10;
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
