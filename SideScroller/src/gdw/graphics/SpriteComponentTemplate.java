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
	private boolean flipped;
	
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
		scale = getFloatParam("Scale", 1.0f);
		filter = new Color(getFloatParam("FilterRed", 1.0f), getFloatParam("FilterGreen", 1.0f), getFloatParam("FilterBlue", 1.0f));
		pivotX = getFloatParam("PivotX", 0.0f);
		pivotY = getFloatParam("PivotY", 0.0f);
		layer = getIntegerParam("Layer", 1);
		flipped = false;
	}

	@Override
	public Component createComponent() {
		// TODO Auto-generated method stub
		return null;
	}
}
