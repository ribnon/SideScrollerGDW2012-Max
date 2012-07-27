package gdw.gameplay.player;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

import java.util.HashMap;

public class DuckableComponentTemplate extends ComponentTemplate {

	private boolean ducked = false;
	private float duckedSizeX;
	private float duckedSizeY;
	
	public DuckableComponentTemplate(HashMap<String, String> params) {
		super(params);
		
		ducked = getIntegerParam("ducked", 0) == 1;
		duckedSizeX = getFloatParam("duckedSizeX", 5.0f);
		duckedSizeY = getFloatParam("duckedSizeY", 5.0f);
	}

	public boolean isDucked() {
		return ducked;
	}

	public float getDuckedSizeX() {
		return duckedSizeX;
	}

	public float getDuckedSizeY() {
		return duckedSizeY;
	}

	@Override
	public Component createComponent() {
		return new DuckableComponent(this);
	}

}
