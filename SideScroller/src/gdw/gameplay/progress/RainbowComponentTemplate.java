package gdw.gameplay.progress;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;


public class RainbowComponentTemplate extends ComponentTemplate {
	private boolean active = false;
	private int checkPointNumber;
	
	public RainbowComponentTemplate(HashMap<String, String> params) {
		super(params);
		if(getIntegerParam("Active", 0) != 0) active = true;
		checkPointNumber = getIntegerParam("CheckPointNumber", 0);
	}

	@Override
	public Component createComponent() {
		return new RainbowComponent(this);
	}

}
