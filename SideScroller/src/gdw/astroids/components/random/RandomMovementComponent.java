package gdw.astroids.components.random;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.physics.SimulationComponent;


//react on entity construct msg
public class RandomMovementComponent extends Component {

	private float forceX;
	private float currentForceX;
	private float forceY;
	private float currentForceY;
	
	public RandomMovementComponent(ComponentTemplate template) {
		super(template);
		RandomMovementComponentTemplate t = (RandomMovementComponentTemplate)template;
		forceX = t.forceX;
		currentForceX = (2*(float)(Math.random()-0.5))*forceX;
		forceY = t.forceY;
		currentForceY = (2*(float)(Math.random()-0.5))*forceY;
	}

	public static final int COMPONENT_TYPE = 1004;
	
	@Override
	public int getComponentTypeID() {
		// TODO Auto-generated method stub
		return COMPONENT_TYPE;
	}
	
	public void tick(float deltaTime) {
//		super.tick(deltaTime);
		Component cmp = this.getOwner().getComponent(SimulationComponent.COMPONENT_TYPE);
		if(cmp!=null) {
			SimulationComponent scmp = (SimulationComponent) cmp;
			scmp.addForce(currentForceX, currentForceY);
		}
	}

}
