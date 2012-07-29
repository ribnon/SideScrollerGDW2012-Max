package gdw.astroids.components;

import java.util.Arrays;
import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class DecayComponentTemplate extends ComponentTemplate {

	String[] decayIn;
	float[] averagePieces;
	float[][] spawnRange;
	
	public DecayComponentTemplate(HashMap<String, String> params) {
		super(params);
		
		String[] decayInTypes = getStringParam("decayIn", "").split(":");
		decayIn = new String[decayInTypes.length];
		for(int i=0;i<decayInTypes.length;++i) {
			decayIn[i] = decayInTypes[i];
		}
		
		String[] pieces =getStringParam("spawnValues", "").split(":");
		averagePieces = new float[pieces.length];
		for(int i=0;i<pieces.length;++i) {
			String[] pieceValues = pieces[i].split(";");
			System.out.println(Arrays.toString(pieceValues));
			for(int j=0;j<pieceValues.length;++j) {
				averagePieces[i]+=Float.valueOf(pieceValues[j]);
			}
			averagePieces[i]/=pieceValues.length;
		}
		
		
		pieces = getStringParam("spawnRange", "").split(":");
		spawnRange = new float[pieces.length][2];
		for(int i=0;i<pieces.length;++i) {
			spawnRange[i] = new float[2];
			if(pieces[i].equals("")) {
				spawnRange[i][0] = 0;
				spawnRange[i][1] = 1;
			}
			else {
				String[] spawnParam = pieces[i].split(";");
				spawnRange[i][0] = Float.valueOf(spawnParam[0]);
				spawnRange[i][1] = Float.valueOf(spawnParam[1]);
			}
		}
	}

	@Override
	public Component createComponent() {
		return new DecayComponent(this);
	}

}
