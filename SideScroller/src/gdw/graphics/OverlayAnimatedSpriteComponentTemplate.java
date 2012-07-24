package gdw.graphics;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class OverlayAnimatedSpriteComponentTemplate extends ComponentTemplate{

	public OverlayAnimatedSpriteComponentTemplate(HashMap<String, String> params) {
		super(params);
	}

	@Override
	public Component createComponent() {
		return new OverlayedAnimatedSpriteComponent(this);
	}
}
