package gdw.gameplay.color;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.gameplay.GameColor;

public class ColorSourceComponent extends Component
{
	public final static int COMPONENT_TYPE = 21;
	
	private GameColor color;
	
	public ColorSourceComponent(ComponentTemplate template)
	{
		super(template);
		
		if((template != null)&&(template instanceof ColorSourceComponentTemplate))
		{
			ColorSourceComponentTemplate temp = (ColorSourceComponentTemplate) template;
			this.color = temp.getColor().clone();
		}
	}
	
	@Override
	public int getComponentTypeID()
	{
		return COMPONENT_TYPE;
	}


	public GameColor getColor()
	{
		return color;
	}


	public void setColor(GameColor color)
	{
		this.color = color;
	}
	
	

}
