package gdw.gameplay.levelObjects;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.EntityReference;
import gdw.gameplay.levelObjects.SwitchComponent.SwitchType;

public class SwitchComponentTemplate extends ComponentTemplate {

	private EntityReference targentEntity;
	private SwitchType type;
	private float completedActivation, activationDuration;

	@Override
	public Component createComponent() {
		// TODO Auto-generated method stub
		return null;
	}

}
