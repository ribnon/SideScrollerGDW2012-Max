package gdw.Gameplay.Player;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;


public class PlayerBehaviorComponentTemplate extends ComponentTemplate {

	protected PlayerBehaviorComponentTemplate(HashMap<String, String> params) {
		super(params);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Component createComponent() {
		return new PlayerBehaviorComponent(this);
	}

}
