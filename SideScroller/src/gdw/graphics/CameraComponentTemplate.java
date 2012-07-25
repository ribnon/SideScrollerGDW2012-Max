package gdw.graphics;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class CameraComponentTemplate extends ComponentTemplate {

	private int playerNumber;
	
	public int getPlayerNumber(){
		return playerNumber;
	}
	
	public CameraComponentTemplate(HashMap<String, String> params) {
		super(params);
		playerNumber = getIntegerParam("PlayerNumber");
	}

	@Override
	public Component createComponent() {
		return new CameraComponent(this);
	}

}
