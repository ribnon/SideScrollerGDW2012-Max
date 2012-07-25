package gdw.gameplay.progress;

import gdw.entityCore.Component;

import java.util.LinkedList;

public class GameplayProgressManager {
	private static GameplayProgressManager instance;
	LinkedList<RainbowComponent> rainbows;
	StartSpawnComponent spawn;

	private GameplayProgressManager() {
		rainbows = new LinkedList<RainbowComponent>();
	}

	private static void initialize() {
		instance = new GameplayProgressManager();
	}

	public static GameplayProgressManager getInstance() {
		if (instance == null)
			initialize();
		return instance;
	}
	
	public void setStartSpawnComponent(StartSpawnComponent newSpawn) {
		spawn = newSpawn;
	}
	
	public void addRainbow(RainbowComponent rainbow)
	{
		rainbows.add(rainbow);
	}
	
	public void removeRainbow(RainbowComponent rainbow)
	{
		rainbows.remove(rainbow);
	}
	
	public Component getCurrentSpawnComponent() {
		RainbowComponent currentSpawn = null;
		for (RainbowComponent i : rainbows) {
			if (!i.getActive()) {
				continue;
			}
			if (currentSpawn != null) {
				if( i.getCheckPointNumber() > currentSpawn.getCheckPointNumber()) {
					currentSpawn = i;
				}
			}
			else {
				currentSpawn = i;
			}
		}
		if(currentSpawn == null)
			return spawn;
		return currentSpawn;
	}
}