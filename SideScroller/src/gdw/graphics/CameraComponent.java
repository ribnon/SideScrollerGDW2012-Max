package gdw.graphics;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class CameraComponent extends Component {

	public final static int COMPONENT_TYPE = 6;
	protected CameraComponent(ComponentTemplate template) {
		super(template);
	}

	@Override
	public int getComponentTypeID() {
		return COMPONENT_TYPE;
	}

}
