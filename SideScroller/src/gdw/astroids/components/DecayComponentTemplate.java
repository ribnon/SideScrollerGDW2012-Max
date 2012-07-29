package gdw.astroids.components;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class DecayComponentTemplate extends ComponentTemplate {

	String decayIn;
	float averagePieces;
	float[] spawnRange;
	
	public DecayComponentTemplate(HashMap<String, String> params) {
		super(params);
		decayIn = getStringParam("decayIn", "");
		
		String[] pieceValues = getStringParam("spawnValues", "0;1").split(";");
		for(int i=0;i<pieceValues.length;++i) {
			averagePieces+=Float.valueOf(pieceValues[i]);
		}
		averagePieces/=pieceValues.length;
		
		pieceValues = getStringParam("spawnRange", "0;1").split(";");
		spawnRange = new float[2];
		spawnRange[0] = Float.valueOf(pieceValues[0]);
		spawnRange[1] = Float.valueOf(pieceValues[1]);
	}

	@Override
	public Component createComponent() {
		return new DecayComponent(this);
	}

}
