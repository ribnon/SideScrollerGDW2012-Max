package gdw.astroids.components.random;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class RandomRotationComponentTemplate extends ComponentTemplate {

	
	float angularVelocity;
	
	public RandomRotationComponentTemplate(HashMap<String, String> params) {
		super(params);
		angularVelocity = getFloatParam("angularVelocity", 0.0f);
	}

	@Override
	public Component createComponent() {
		// TODO Auto-generated method stub
		return new RandomRotationComponent(this);
	}

}
