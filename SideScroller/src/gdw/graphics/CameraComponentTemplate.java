package gdw.graphics;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class CameraComponentTemplate extends ComponentTemplate {

	protected CameraComponentTemplate(HashMap<String, String> params) {
		super(params);
	}

	@Override
	public Component createComponent() {
		return new CameraComponent(this);
	}

}
