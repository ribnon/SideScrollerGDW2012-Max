package gdw.gameplay.levelObjects;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.EntityReference;
import gdw.gameplay.levelObjects.SwitchComponent.SwitchType;

import java.util.ArrayList;
import java.util.HashMap;

public class SwitchComponentTemplate extends ComponentTemplate {

	private ArrayList<EntityReference> targetEntity;
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

		targetEntity = super.getEntityReferenceArrayParam("TargetEntities");
		String strtype = super.getStringParam("SwitchType", "Hit");
		if (strtype.equals("Hit")) {
			type = SwitchType.Hit;
		} else if (strtype.equals("Step")) {
			type = SwitchType.Step;
		} else if (strtype.equals("Pull")) {
			type = SwitchType.Pull;
		}
		activationDuration = super.getFloatParam("ActivationDuration", 3.0f);
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
