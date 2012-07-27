package gdw.graphics;

import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class OverlayedAnimatedSpriteComponentTemplate extends ComponentTemplate{

	private float scale;
	private Color filter;
	private float pivotX;
	private float pivotY;
	private int layer;
	private boolean flipped = false;
	
	private SpriteSheet baseSpritesheet;
	private SpriteSheet overlaySpritesheet1;
	private SpriteSheet overlaySpritesheet2;
	
	private Color overlayColor1;
	private Color overlayColor2;
	
	private int[] baseCycleLength;
	private int[] overlayCycleLength1;
	private int[] overlayCycleLength2;
	
	private int baseCycle;
	private int overlayCycle1;
	private int overlayCycle2;
	
	private int baseStep = 0;
	private int overlayStep1 = 0;
	private int overlayStep2 = 0;
	
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
	
	public SpriteSheet getBaseSpritesheet() {
		return baseSpritesheet;
	}

	public SpriteSheet getOverlaySpritesheet1() {
		return overlaySpritesheet1;
	}

	public SpriteSheet getOverlaySpritesheet2() {
		return overlaySpritesheet2;
	}

	public Color getOverlayColor1() {
		return overlayColor1;
	}

	public Color getOverlayColor2() {
		return overlayColor2;
	}

	public int[] getBaseCycleLength() {
		return baseCycleLength;
	}

	public int[] getOverlayCycleLength1() {
		return overlayCycleLength1;
	}

	public int[] getOverlayCycleLength2() {
		return overlayCycleLength2;
	}

	public int getBaseCycle() {
		return baseCycle;
	}

	public int getOverlayCycle1() {
		return overlayCycle1;
	}

	public int getOverlayCycle2() {
		return overlayCycle2;
	}

	public int getBaseStep() {
		return baseStep;
	}

	public int getOverlayStep1() {
		return overlayStep1;
	}

	public int getOverlayStep2() {
		return overlayStep2;
	}

	public OverlayedAnimatedSpriteComponentTemplate(HashMap<String, String> params) {
		super(params);
		
		scale = getFloatParam("scale", 1.0f);
		filter = new Color(getFloatParam("filterRed", 1.0f), getFloatParam("filterGreen", 1.0f), getFloatParam("filterBlue", 1.0f));
		pivotX = getFloatParam("pivotX", 0.0f);
		pivotY = getFloatParam("pivotY", 0.0f);
		layer = getIntegerParam("layer", 1);
		if(getIntegerParam("flipped", 0) == 1) flipped = true;
		
		try {
			baseSpritesheet = new SpriteSheet(getStringParam("basePath", ""), getIntegerParam("baseTileWidth", 64), getIntegerParam("baseTileHeight", 64));
			overlaySpritesheet1 = new SpriteSheet(getStringParam("overlay1Path", ""), getIntegerParam("overlay1TileWidth", 64), getIntegerParam("overlay1TileHeight", 64));
			overlaySpritesheet2 = new SpriteSheet(getStringParam("overlay2Path", ""), getIntegerParam("overlay2TileWidth", 64), getIntegerParam("overlay2TileHeight", 64));
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		overlayColor1 = new Color(getFloatParam("overlay1ColorRed", 1.0f), getFloatParam("overlay1ColorGreen", 1.0f), getFloatParam("overlay1ColorBlue", 1.0f));
		overlayColor2 = new Color(getFloatParam("overlay2ColorRed", 1.0f), getFloatParam("overlay2ColorGreen", 1.0f), getFloatParam("overlay2ColorBlue", 1.0f));
		
		baseCycleLength = new int[baseSpritesheet.getHorizontalCount()];
		for(int i = 0; i < baseCycleLength.length; ++i) baseCycleLength[i] = 0;
		
		overlayCycleLength1 = new int[overlaySpritesheet1.getHorizontalCount()];
		for(int i = 0; i < baseCycleLength.length; ++i) baseCycleLength[i] = 0;
		
		overlayCycleLength2 = new int[baseSpritesheet.getHorizontalCount()];
		for(int i = 0; i < baseCycleLength.length; ++i) baseCycleLength[i] = 0;
		
		baseCycle = getIntegerParam("baseCycle", 0);
		overlayCycle1 = getIntegerParam("overlayCycle1", 0);
		overlayCycle2 = getIntegerParam("overlayCycle2", 0);
		
		baseStep = getIntegerParam("baseStep", 0);
		overlayStep1 = getIntegerParam("overlayStep1", 0);
		overlayStep2 = getIntegerParam("overlayStep2", 0);
	}

	@Override
	public Component createComponent() {
		return new OverlayedAnimatedSpriteComponent(this);
	}
	
	public static boolean isGhostOnly()
	{
		return true;
	}

}
