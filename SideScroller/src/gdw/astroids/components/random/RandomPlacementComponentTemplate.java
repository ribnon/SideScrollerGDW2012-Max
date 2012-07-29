package gdw.astroids.components.random;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class RandomPlacementComponentTemplate extends ComponentTemplate {

	
	int placementPosX;
	int placementPosY;
	int boxWidth;
	int boxHeight;
	boolean isLocal;
	
	
	public RandomPlacementComponentTemplate(HashMap<String, String> params) {
		super(params);
		
		placementPosX = getIntegerParam("placementPosX", 0);
		placementPosY = getIntegerParam("placementPosY", 0);
		System.out.println(placementPosY+"");
		
		boxWidth = getIntegerParam("boxWidth", 800);
		boxHeight = getIntegerParam("boxHeight", 600);
		
		isLocal = getIntegerParam("isLocal", 0) == 1;
	}

	@Override
	public Component createComponent() {
		// TODO Auto-generated method stub
		return new RandomPlacementComponent(this);
	}

}
