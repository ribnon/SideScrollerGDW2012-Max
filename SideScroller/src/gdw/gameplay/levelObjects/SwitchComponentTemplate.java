package gdw.gameplay.levelObjects;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.EntityReference;
import gdw.gameplay.levelObjects.SwitchComponent.SwitchType;

import java.util.HashMap;

public class SwitchComponentTemplate extends ComponentTemplate {

	private EntityReference targetEntity;
	private SwitchType type;
	private float activationDuration;

	/**
	 * SwitchComponentTemplate constructor Paramaters which will be initialized:
	 * TargetEntity (:EntityReference), SwitchType (Hit, Step, Pull:String),
	 * ActiviationDuration (:float)
	 * 
	 * @param params
	 */
	public SwitchComponentTemplate(HashMap<String, String> params) {
		super(params);

		targetEntity = super.getEntityReferenceParam("TargetEntity");
		String strtype = super.getStringParam("SwitchType");
		if (strtype.equals("Hit")) {
			type = SwitchType.Hit;
		} else if (strtype.equals("Step")) {
			type = SwitchType.Step;
		} else if (strtype.equals("Pull")) {
			type = SwitchType.Pull;
		}
		activationDuration = super.getFloatParam("ActivationDuration");
	}

	@Override
	public Component createComponent() {
		if (type == SwitchType.Pull)
			return new SwitchComponent(this, targetEntity, type,
					activationDuration);
		else
			return new SwitchComponent(this, targetEntity, type);
	}
}
