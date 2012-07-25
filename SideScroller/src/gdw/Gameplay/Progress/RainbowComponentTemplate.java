package gdw.Gameplay.Progress;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;


public class RainbowComponentTemplate extends ComponentTemplate {

	protected RainbowComponentTemplate(HashMap<String, String> params) {
		super(params);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Component createComponent() {
		return new RainbowComponent(this);
	}

}
