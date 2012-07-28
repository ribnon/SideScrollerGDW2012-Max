package gdw.astroids.input;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class AstroidsInputComponentTemplate extends ComponentTemplate{


	int leftKey, rightKey, upKey, downKey;
	int shootKey;
	
	public AstroidsInputComponentTemplate(HashMap<String, String> params) {
		super(params);
		upKey = getIntegerParam("upKey", 17);
		rightKey = getIntegerParam("rightKey", 32);
		downKey = getIntegerParam("downKey", 31);
		leftKey = getIntegerParam("leftKey", 30);
		shootKey = getIntegerParam("shootKey", 54);
	}

	@Override
	public Component createComponent() {
		// TODO Auto-generated method stub
		return new AstroidsInputComponent(this);
	}

}
