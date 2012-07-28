package gdw.astroids.components.random;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class RandomRotationComponent extends Component {

	private static final int COMPONENT_TYPE = 1006;
	
	private float currentAngularVelocity;
	private float angularVelocity;
	
	public RandomRotationComponent(ComponentTemplate template) {
		super(template);
		RandomRotationComponentTemplate t = (RandomRotationComponentTemplate)template;
		
		angularVelocity = t.angularVelocity;
		currentAngularVelocity = (float)(2*(Math.random()-0.5f))*angularVelocity;
	}

	@Override
	public int getComponentTypeID() {
		// TODO Auto-generated method stub
		return COMPONENT_TYPE;
	}

	
	@Override
	public void tick(float deltaTime) {
		super.tick(deltaTime);
		this.getOwner().setOrientation(this.getOwner().getOrientation() + currentAngularVelocity*deltaTime);
		
	}
}
