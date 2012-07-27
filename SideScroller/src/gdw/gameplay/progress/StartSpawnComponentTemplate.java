package gdw.gameplay.progress;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class StartSpawnComponentTemplate extends ComponentTemplate
{
	public StartSpawnComponentTemplate(HashMap<String, String> params)
	{
		super(params);
	}

	@Override
	public Component createComponent()
	{
		return new StartSpawnComponent(this);
	}

}
