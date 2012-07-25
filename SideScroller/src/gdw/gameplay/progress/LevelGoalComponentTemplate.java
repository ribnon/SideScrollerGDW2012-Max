package gdw.gameplay.progress;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class LevelGoalComponentTemplate extends ComponentTemplate
{
	protected LevelGoalComponentTemplate(HashMap<String, String> params)
	{
		super(params);
	}

	@Override
	public Component createComponent()
	{
		return new LevelGoalComponent(this);
	}

}
