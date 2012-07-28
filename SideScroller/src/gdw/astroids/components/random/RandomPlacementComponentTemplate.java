package gdw.astroids.components.random;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class RandomPlacementComponentTemplate extends ComponentTemplate {

	public RandomPlacementComponentTemplate(HashMap<String, String> params) {
		super(params);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Component createComponent() {
		// TODO Auto-generated method stub
		return new RandomPlacementComponent(this);
	}

}
