package gdw.astroids.components;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

import java.util.HashMap;

public class AstroidsPlayerMarkerComponentTemplate extends ComponentTemplate
{
	public AstroidsPlayerMarkerComponentTemplate(
			HashMap<String, String> params)
	{
		super(params);
	}

	@Override
	public Component createComponent()
	{
		return new AstroidsPlayerMarkerComponent(this);
	}
}
