package gdw.gameplay.color;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.gameplay.GameColor;

public class ColorSourceComponentTemplate extends ComponentTemplate
{
	private GameColor color;
	
	protected ColorSourceComponentTemplate(HashMap<String, String> params)
	{
		super(params);
		String colorStr = getStringParam("color", "0;0;0");
		String[] colorsStr = colorStr.split(";");
		//teste eingabe auf korrektheit
		if(colorsStr.length == 3)
		{
			this.color = new GameColor(colorsStr[0].equals("1"), colorsStr[1].equals("1"), colorsStr[2].equals("1"));
		}else
		{
			this.color = new GameColor();
		}	
	}
	

	public GameColor getColor()
	{
		return color;
	}

	@Override
	public Component createComponent()
	{
		return new ColorSourceComponent(this);
	}

}
