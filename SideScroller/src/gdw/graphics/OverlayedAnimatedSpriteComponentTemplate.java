package gdw.graphics;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class OverlayedAnimatedSpriteComponentTemplate extends ComponentTemplate{

	public OverlayedAnimatedSpriteComponentTemplate(HashMap<String, String> params) {
		super(params);
	}

	@Override
	public Component createComponent() {
		return new OverlayedAnimatedSpriteComponent(this);
	}
}
