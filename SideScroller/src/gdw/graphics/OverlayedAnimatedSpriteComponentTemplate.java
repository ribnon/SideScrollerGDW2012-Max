package gdw.graphics;

import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class OverlayedAnimatedSpriteComponentTemplate extends ComponentTemplate{

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
		try {
			baseSpritesheet = new SpriteSheet(getStringParam("BasePath", ""), getIntegerParam("BaseTileWidth", 64), getIntegerParam("BaseTileHeight", 64));
			overlaySpritesheet1 = new SpriteSheet(getStringParam("Overlay1Path", ""), getIntegerParam("Overlay1TileWidth", 64), getIntegerParam("Overlay1TileHeight", 64));
			overlaySpritesheet1 = new SpriteSheet(getStringParam("Overlay2Path", ""), getIntegerParam("Overlay2TileWidth", 64), getIntegerParam("Overlay2TileHeight", 64));
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		overlayColor1 = new Color(getFloatParam("Overlay1ColorRed", 1.0f), getFloatParam("Overlay1ColorGreen", 1.0f), getFloatParam("Overlay1ColorBlue", 1.0f));
		overlayColor2 = new Color(getFloatParam("Overlay1ColorRed", 1.0f), getFloatParam("Overlay1ColorGreen", 1.0f), getFloatParam("Overlay1ColorBlue", 1.0f));
		
		baseCycleLength = new int[baseSpritesheet.getHorizontalCount()];
		for(int i = 0; i < baseCycleLength.length; ++i) baseCycleLength[i] = 0;
		
		overlayCycleLength1 = new int[overlaySpritesheet1.getHorizontalCount()];
		for(int i = 0; i < baseCycleLength.length; ++i) baseCycleLength[i] = 0;
		
		overlayCycleLength2 = new int[baseSpritesheet.getHorizontalCount()];
		for(int i = 0; i < baseCycleLength.length; ++i) baseCycleLength[i] = 0;
		
		baseCycle = getIntegerParam("BaseCycle", 0);
		overlayCycle1 = getIntegerParam("OverlayCycle1", 0);
		overlayCycle2 = getIntegerParam("OverlayCycle2", 0);
		
		baseStep = getIntegerParam("BaseStep", 0);
		overlayStep1 = getIntegerParam("OverlayStep1", 0);
		overlayStep2 = getIntegerParam("OverlayStep2", 0);
	}

	@Override
	public Component createComponent() {
		return new OverlayedAnimatedSpriteComponent(this);
	}
}
