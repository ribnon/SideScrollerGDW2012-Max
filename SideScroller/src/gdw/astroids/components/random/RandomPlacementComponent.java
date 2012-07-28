package gdw.astroids.components.random;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class RandomPlacementComponent extends Component {

	
	private static final int COMPONENT_TYPE = 1005;
	
	public RandomPlacementComponent(ComponentTemplate template) {
		super(template);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getComponentTypeID() {
		// TODO Auto-generated method stub
		return COMPONENT_TYPE;
	}

}
