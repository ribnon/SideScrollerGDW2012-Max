package gdw.astroids.components;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.physics.SimulationComponent;


//react on entity construct msg
public class RandomMovementComponent extends Component {

	private float forceX;
	private float forceY;
	
	public RandomMovementComponent(ComponentTemplate template) {
		super(template);
		RandomMovementComponentTemplate t = (RandomMovementComponentTemplate)template;
		forceX = (2*(float)(Math.random()-0.5))*t.forceX;
		forceY = (2*(float)(Math.random()-0.5))*t.forceY;
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
			scmp.addForce(forceX, forceY);
		}
	}

}
