package gdw.gameplay.levelObjects;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.EntityManager;
import gdw.entityCore.EntityReference;
import gdw.entityCore.Message;
import collisionDetection.CollisionDetectionMessage;

public class SwitchComponent extends Component {

	private EntityReference targetEntity;

	private SwitchType type;

	public enum SwitchType {
		Hit, Step, Pull
	}

	private boolean contactFlag, activeFlag;

	// Only by PullComponent
	private float completedActivation, activationDuration;

	public static final int COMPONENT_TYPE = 16;

	public SwitchComponent(ComponentTemplate template,
			EntityReference targetEntity, SwitchType type,
			float activationDuration) {
		super(template);

		this.targetEntity = targetEntity;

		contactFlag = false;
		activeFlag = false;

		completedActivation = 0.0f;
		activationDuration = activationDuration;
	}

	public SwitchComponent(ComponentTemplate template,
			EntityReference targetEntity, SwitchType type) {
		this(template, targetEntity, type, 3.0f);
	}

	public EntityReference getTargetEntity() {
		return targetEntity;
	}

	public void setTargetEntity(EntityReference targetEntity) {
		this.targetEntity = targetEntity;
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
			}

		}
	}

	@Override
	public void onMessage(Message msg) {
		// Handle for pullswitch
		if (getType() == SwitchType.Pull) {
			if (msg instanceof CollisionDetectionMessage) {
				CollisionDetectionMessage tmpmsg = (CollisionDetectionMessage) msg;

				if (!contactFlag && !activeFlag) {
					int sourceid = targetEntity.getID() != tmpmsg
							.getIDCandidate1() ? tmpmsg.getIDCandidate1()
							: tmpmsg.getIDCandidate2();

					SwitchUserComponent swusrcomp = (SwitchUserComponent) EntityManager
							.getInstance().getEntity(sourceid)
							.getComponent(SwitchUserComponent.COMPONENT_TYPE);

					if ((swusrcomp != null) && swusrcomp.getpullActive()) {
						contactFlag = true;
					}
				}

			}
		}
	}

	@Override
	public int getComponentTypeID() {
		return COMPONENT_TYPE;
	}
}
