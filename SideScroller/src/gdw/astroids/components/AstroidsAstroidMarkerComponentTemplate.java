package gdw.astroids.components;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class AstroidsAstroidMarkerComponentTemplate extends ComponentTemplate
{
	public AstroidsAstroidMarkerComponentTemplate(
			HashMap<String, String> params)
	{
		super(params);
	}

	@Override
	public Component createComponent()
	{
		return new AstroidsAstroidMarkerComponent(this);
	}
}
