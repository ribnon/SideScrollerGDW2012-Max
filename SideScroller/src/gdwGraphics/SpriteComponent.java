package gdwGraphics;

import org.newdawn.slick.Color;
import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

/**
 * 
 * @author eppixx
 *
 */
public abstract class SpriteComponent extends Component
{
	/**
	 * scaling-factor that scales a sprite
	 * default is 1
	 */
	private float scale = 1.0f;
	
	
	/**
	 * filter that modifies color
	 * default is no filter
	 */
	private Color filter = new Color(0, 0, 0, 0);
	
	
	/**
	 * describes the rotation and scale center for the x-axis
	 * default is 0
	 */
	private float pivotX = 0.0f;
	
	
	/**
	 * describes the rotation and scale center for the y-axis
	 * default is 0
	 */
	private float pivotY = 0.0f;
	
	
	/**
	 * describes the layer the images will be drawn on 
	 * default is 1 (should be changed)
	 */
	private int layer = 1;

	
	/**
	 *  Component-ID for SpriteComponent is 1
	 */
	public static final int COMPONENT_TYPE = 1;
	
	
	/**
	 * Constructor for SpriteComponent
	 * initializes scale, filter, pivotX, pivotY and layer
	 * 
	 * @param template	the used ComponentTemplate that is used for parsing
	 */
	public SpriteComponent(ComponentTemplate template)
	{
		this.scale = template.getFloatParam("scale");
		this.filter = new Color(template.getIntParam(), 
				template.getIntParam, template.getIntParam(), template.getntParam());
		this.pivotX = template.getFloatParam("pivotX");
		this.pivotY = template.getFloatParam("pivotY");
		this.layer = template.getIntParam("layer");
	}

	
	/**
	 * marks the object as destroyed
	 */
	protected abstract void destroy();

	
	/**
	 * method to draw the Sprite to a desired location
	 */
	public abstract void draw();
	
	
	/*
	 * generated getter/setter 
	 */
	public float getScale()
	{
		return scale;
	}

	public void setScale(float scale)
	{
		this.scale = scale;
	}

	public Color getFilter()
	{
		return filter;
	}

	public void setFilter(Color filter)
	{
		this.filter = filter;
	}

	public float getPivotX()
	{
		return pivotX;
	}

	public void setPivotX(float pivotX)
	{
		this.pivotX = pivotX;
	}

	public float getPivotY()
	{
		return pivotY;
	}

	public void setPivotY(float pivotY)
	{
		this.pivotY = pivotY;
	}

	public int getLayer()
	{
		return layer;
	}

	public void setLayer(int layer)
	{
		this.layer = layer;
	}
}
