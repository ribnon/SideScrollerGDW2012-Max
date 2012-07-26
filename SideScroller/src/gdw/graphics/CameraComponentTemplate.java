package gdw.graphics;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class CameraComponentTemplate extends ComponentTemplate {

	private int playerNumber;
	private int viewPortX;
	private int viewPortY;
	
	public int getPlayerNumber(){
		return playerNumber;
	}
	
	public CameraComponentTemplate(HashMap<String, String> params) {
		super(params);
		playerNumber = getIntegerParam("playerNumber",1);
		viewPortX = getIntegerParam("viewPortX",200);
		viewPortY = getIntegerParam("viewPortY",200);
	}
	@Override
	public Component createComponent() {
		return new CameraComponent(this);
	}
	
	public int getViewPortX()
	{
		return viewPortX;
	}
	
	public int getViewPortY()
	{
		return viewPortY;
	}

}
