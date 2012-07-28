package gdw.gameplay.levelObjects;

import java.security.acl.Owner;
import java.util.ArrayList;

import gdw.collisionDetection.CollisionDetectionMessage;
import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Entity;
import gdw.entityCore.EntityManager;
import gdw.entityCore.EntityReference;
import gdw.entityCore.Message;
import gdw.gameplay.levelObjects.messageType.ActivateMessage;
import gdw.gameplay.levelObjects.messageType.DeactivateMessage;

public class SwitchComponent extends Component {

	private ArrayList<EntityReference> targetEntities;

	private SwitchType type;

	public enum SwitchType {
		Hit, Step, Pull
	}

	private boolean contactFlag, activeFlag;

	// Only by PullComponent
	private float completedActivation, activationDuration;

	public static final int COMPONENT_TYPE = 16;

	@SuppressWarnings("unchecked")
	public SwitchComponent(ComponentTemplate template,
			ArrayList<EntityReference> targetEntities, SwitchType type,
			float activationDuration) {
		super(template);

		this.targetEntities = (ArrayList<EntityReference>)targetEntities.clone();

		contactFlag = false;
		activeFlag = false;

		completedActivation = 0.0f;
		this.activationDuration = activationDuration;
	}

	public SwitchComponent(ComponentTemplate template,
			ArrayList<EntityReference> targetEntities, SwitchType type) {
		this(template, targetEntities, type, 3.0f);
	}

	public ArrayList<EntityReference> getTargetEntities() {
		return targetEntities;
	}

	public void setTargetEntity(ArrayList<EntityReference> targetEntity) {
		this.targetEntities = targetEntity;
	}

	public SwitchType getType() {
		return type;
	}

	public void setType(SwitchType type) {
		this.type = type;
	}

	public float getActivationDuration() {
		return activationDuration;
	}

	public void setActivationDuration(float activationDuration) {
		this.activationDuration = activationDuration;
	}

	public float getCompletedActivation() {
		return completedActivation;
	}

	@Override
	public void tick(float deltaTime) {
		// Handle for pullswitch
		if (getType() == SwitchType.Pull) {
			if (!activeFlag && contactFlag) {
				completedActivation += deltaTime;

				if (completedActivation >= activationDuration) {
					for(EntityReference targetEntity: targetEntities){
						Entity entity = EntityManager.getInstance().getEntity(targetEntity.getID());
						if (entity != null) {
							entity.message(new ActivateMessage());
							activeFlag = true;
						}
					}
					return;
				} else {
					contactFlag = false;
					return;
				}
			}

			if (activeFlag && !contactFlag) {
				completedActivation -= deltaTime;

				if (completedActivation <= activationDuration) {
					for(EntityReference targetEntity: targetEntities){
						Entity entity = EntityManager.getInstance().getEntity(targetEntity.getID());
						if (entity != null) {
							entity.message(new DeactivateMessage());
							activeFlag = true;
						}
					}
					return;
				}
				return;
			}

			if (activeFlag && contactFlag) {
				contactFlag = false;
				return;
			}

			return;
		}

		// Handle for stepswitch
		if (getType() == SwitchType.Step) {
			if (activeFlag && contactFlag) {
				contactFlag = false;
				return;
			}

			if (activeFlag && !contactFlag) {
				for(EntityReference targetEntity: targetEntities){
					Entity entity = EntityManager.getInstance().getEntity(targetEntity.getID());
					if (entity != null) {
						entity.message(new DeactivateMessage());
						activeFlag = true;
					}
				}
				return;
			}
			return;
		}
	}

	@Override
	public void onMessage(Message msg) {

		// On CollisionDetectionMessage
		if (msg instanceof CollisionDetectionMessage) {
			CollisionDetectionMessage tmpmsg = (CollisionDetectionMessage) msg;
			int sourceid = getOwner().getID() != tmpmsg.getIDCandidate1() ? tmpmsg.getIDCandidate1() : tmpmsg.getIDCandidate2();

			SwitchUserComponent swusrcomp = (SwitchUserComponent) EntityManager
					.getInstance().getEntity(sourceid)
					.getComponent(SwitchUserComponent.COMPONENT_TYPE);

			// Handle for pullswitch
			if (getType() == SwitchType.Pull) {
				// On Contact
				if (!contactFlag && !activeFlag) {
					if ((swusrcomp != null) && swusrcomp.getpullActive()
							&& !swusrcomp.gethitTrigger()) {
						contactFlag = true;
						return;
					}
				}

				if (!contactFlag && activeFlag) {
					if ((swusrcomp != null) && swusrcomp.getpullActive()
							&& !swusrcomp.gethitTrigger()) {
						contactFlag = false;
						return;
					}
				}
				return;
			}

			// Handle for stepswitch
			if (getType() == SwitchType.Step) {
				if (!contactFlag && !activeFlag) {
					if ((swusrcomp != null) && !swusrcomp.getpullActive()
							&& !swusrcomp.gethitTrigger()) {
						contactFlag = true;
						activeFlag = true;
						for(EntityReference targetEntity: targetEntities){
							Entity entity = EntityManager.getInstance().getEntity(targetEntity.getID());
							if (entity != null) {
								entity.message(new ActivateMessage());
								activeFlag = true;
							}
						}
						return;
					}
					return;
				}

				if (!contactFlag && activeFlag) {
					if ((swusrcomp != null) && !swusrcomp.getpullActive()
							&& !swusrcomp.gethitTrigger()) {
						contactFlag = true;
						return;
					}
					return;
				}
				return;
			}

			// Handle for hitswitch
			if (getType() == SwitchType.Hit) {
				if (!contactFlag && !activeFlag) {
					if ((swusrcomp != null) && !swusrcomp.getpullActive()
							&& swusrcomp.gethitTrigger()) {
						contactFlag = true;
						activeFlag = true;
						for(EntityReference targetEntity: targetEntities){
							Entity entity = EntityManager.getInstance().getEntity(targetEntity.getID());
							if (entity != null) {
								entity.message(new ActivateMessage());
								activeFlag = true;
							}
						}
						return;
					}
					return;
				}
				return;
			}
		}

	}

	@Override
	public int getComponentTypeID() {
		return COMPONENT_TYPE;
	}
}
