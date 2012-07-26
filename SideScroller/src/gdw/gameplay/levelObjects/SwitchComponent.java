package gdw.gameplay.levelObjects;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.EntityReference;
import gdw.entityCore.Message;

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
			float completedActivition) {
		super(template);

		this.targetEntity = targetEntity;

		contactFlag = false;
		activeFlag = false;

		completedActivation = completedActivition;
		activationDuration = 0.0f;
	}

	public SwitchComponent(ComponentTemplate template,
			EntityReference targetEntity, SwitchType type) {
		this(template, targetEntity, type, 3.0f);
	}

	@Override
	public void tick(float deltaTime) {

	}

	@Override
	public void onMessage(Message msg) {

	}

	@Override
	public int getComponentTypeID() {
		return COMPONENT_TYPE;
	}
}
