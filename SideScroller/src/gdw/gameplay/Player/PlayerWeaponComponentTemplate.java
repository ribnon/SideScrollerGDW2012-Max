package gdw.gameplay.Player;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

import java.util.HashMap;

public class PlayerWeaponComponentTemplate extends ComponentTemplate {
	protected PlayerWeaponComponentTemplate(HashMap<String, String> params) {
		super(params);
	}

	@Override
	public Component createComponent() {
		return new PlayerWeaponComponent(this);
	}
}
