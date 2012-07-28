package gdw.graphics;

import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Component;

public class StaticSpriteComponentTemplate extends ComponentTemplate
{
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
	
	private Image image;
	
	public Image getImage()
	{
		return image;
	}

	public StaticSpriteComponentTemplate(HashMap<String, String> params)
	{
		super(params);
		
		scale = getFloatParam("scale", 1.0f);
		filter = new Color(getFloatParam("filterRed", 1.0f), getFloatParam("filterGreen", 1.0f), getFloatParam("filterBlue", 1.0f));
		pivotX = getFloatParam("pivotX", 0.0f);
		pivotY = getFloatParam("pivotY", 0.0f);
		layer = getIntegerParam("layer", 1);
		if(getIntegerParam("flipped", 0) == 1) flipped = true;
		
		try {
			image = new Image(getStringParam("image", getStringParam("path", "./assets/error.png")));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public Component createComponent()
	{
		return new StaticSpriteComponent(this);
	}
	
	public static boolean isGhostOnly()
	{
		return true;
	}

}

