package gdw.gameplay.levelObjects;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

import java.util.HashMap;

public class SwitchUserComponentTemplate extends ComponentTemplate {

	private boolean hitTrigger;

	public SwitchUserComponentTemplate(HashMap<String, String> params) {
		super(params);

		int value = super.getIntegerParam("HitTrigger");
		if (value == 0)
			hitTrigger = false;
		else if (value == 1)
			hitTrigger = true;
	}

	@Override
	public Component createComponent() {
		return new SwitchUserComponent(this, hitTrigger);
	}
}
