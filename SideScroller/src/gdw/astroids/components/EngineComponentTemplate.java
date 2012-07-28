package gdw.astroids.components;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class EngineComponentTemplate extends ComponentTemplate {
	
	float power;
	float breakPower;
	float rotation;

	public EngineComponentTemplate(HashMap<String, String> params) {
		super(params);
		power = getFloatParam("power", 0.0f);
		breakPower = getFloatParam("breakPower", 0.0f);
		rotation = getFloatParam("rotation",0.0f);
	}

	@Override
	public Component createComponent() {
		// TODO Auto-generated method stub
		return new EngineComponent(this);
	}

}
