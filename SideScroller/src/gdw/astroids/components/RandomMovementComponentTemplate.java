package gdw.astroids.components;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class RandomMovementComponentTemplate extends ComponentTemplate {

	float forceX;
	float forceY;
	
	public RandomMovementComponentTemplate(HashMap<String, String> params) {
		super(params);
		forceX = getFloatParam("forceX", 0.0f);
		forceY = getFloatParam("forceY", 0.0f);
	}

	@Override
	public Component createComponent() {
		// TODO Auto-generated method stub
		return new RandomMovementComponent(this);
	}

}
