package gdw.astroids.components;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class DestroyableComponentTemplate extends ComponentTemplate {

	int life; 
	int destroyPower;
	int destroyGroup;
	
	public DestroyableComponentTemplate(HashMap<String, String> params) {
		super(params);
		life = getIntegerParam("life", 1);
		destroyPower = getIntegerParam("destroyPower", ~0);
		destroyGroup = getIntegerParam("destroyGroup", ~0);
	}

	@Override
	public Component createComponent() {
		// TODO Auto-generated method stub
		return new DestroyableComponent(this);
	}

}
