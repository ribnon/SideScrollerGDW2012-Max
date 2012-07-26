package gdw.gameplay.color;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class FadeInComponentTemplate extends ComponentTemplate {

	public FadeInComponentTemplate(HashMap<String, String> params) {
		super(params);
		
		
	}

	@Override
	public Component createComponent() {
		// TODO Auto-generated method stub
		return new FadeInComponent(this);
	}

}
