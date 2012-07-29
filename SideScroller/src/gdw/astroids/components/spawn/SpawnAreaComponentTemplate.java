package gdw.astroids.components.spawn;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class SpawnAreaComponentTemplate extends ComponentTemplate {
	
	int positionX;
	int positionY;
	int areaX;
	int areaY;
	String[] spawns;
	float[] spawnTime;
	

	public SpawnAreaComponentTemplate(HashMap<String, String> params) {
		super(params);
		areaX = getIntegerParam("areaX", 0);
		areaY = getIntegerParam("areaY", 0);
		
		String[] spawnTemplates = getStringParam("spawns", "").split(";");
		spawns = new String[spawnTemplates.length];
		for(int i=0;i<spawnTemplates.length;++i) {
			spawns[i] = spawnTemplates[i];
		}
		
		String[] tspawnRates = getStringParam("spawnTime", "").split(";");
		spawnTime = new float[tspawnRates.length];
		for(int i=0;i<tspawnRates.length;++i) {
			if(spawnTemplates[i].equals("")) {
				spawnTime[i] = 0.0f;
			}
			else
				spawnTime[i] = Float.valueOf(tspawnRates[i]);
		}
	}

	@Override
	public Component createComponent() {
		// TODO Auto-generated method stub
		return new SpawnAreaComponent(this);
	}

}
