package gdw.astroids.components;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class DecayComponentTemplate extends ComponentTemplate {

	String decayIn;
	
	public DecayComponentTemplate(HashMap<String, String> params) {
		super(params);
		decayIn = getStringParam("decayIn", "");
	}

	@Override
	public Component createComponent() {
		return new DecayComponent(this);
	}

}
