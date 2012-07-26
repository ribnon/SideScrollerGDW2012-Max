package gdw.graphics;

import java.util.HashMap;

import org.newdawn.slick.Color;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class SpriteComponentTemplate extends ComponentTemplate{
	
	private float scale;
	private Color filter;
	private float pivotX;
	private float pivotY;
	private int layer;
	private boolean flipped = false;
	
	public float getScale() {
		return scale;
	}

	public Color getFilter() {
		return filter;
	}

	public float getPivotX() {
		return pivotX;
	}

	public float getPivotY() {
		return pivotY;
	}

	public int getLayer() {
		return layer;
	}

	public boolean isFlipped() {
		return flipped;
	}

	public SpriteComponentTemplate(HashMap<String, String> params) {
		super(params);
		scale = getFloatParam("scale", 1.0f);
		filter = new Color(getFloatParam("filterRed", 1.0f), getFloatParam("filterGreen", 1.0f), getFloatParam("filterBlue", 1.0f));
		pivotX = getFloatParam("pivotX", 0.0f);
		pivotY = getFloatParam("pivotY", 0.0f);
		layer = getIntegerParam("layer", 1);
		if(getIntegerParam("flipped", 0) == 1) flipped = true;
	}

	@Override
	public Component createComponent() {
		// TODO Auto-generated method stub
		return null;
	}
}
