package gdw.gameplay.Player;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

import java.util.HashMap;

public class DuckableComponentTemplate extends ComponentTemplate {

	protected DuckableComponentTemplate(HashMap<String, String> params) {
		super(params);
	}

	@Override
	public Component createComponent() {
		return new DuckableComponent(this);
	}

}
