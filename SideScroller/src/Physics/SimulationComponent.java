package Physics;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class SimulationComponent extends Component {

	private static final float TOLERANCE = 0.001f;

	public static final int COMPONENT_TYPE = 2;

	private float velocityX;
	private float velocityY;
	private float accelerationX;
	private float accelerationY;

	// Mass of Entity, if 0 - object is unmoveable (Infinite mass)
	private float mass;
	private float friction;
	private float externalForceX;
	private float externalForceY;
	private boolean active;

	public SimulationComponent(ComponentTemplate template) {
		super(template);
		// copy blueprint values
		SimulationComponentTemplate tmp = null;
		try {
			tmp = (SimulationComponentTemplate) template;
		} catch (ClassCastException e) {
			e.printStackTrace();
		}
		if (tmp == null) {
			System.out
					.println("Creating SimulationComponent from NonSimulationTemplate");
			return;
		}
		velocityX = tmp.getVelocityX();
		velocityY = tmp.getVelocityY();

		accelerationX = tmp.getAccelerationX();
		accelerationY = tmp.getAccelerationY();

		mass = tmp.getMass();

		friction = tmp.getFriction();

		externalForceX = tmp.getExternalForceX();
		externalForceY = tmp.getExternalForceY();

		// default value
		active = true;
		
		SimulationComponentManager mng = SimulationComponentManager.get();
	}

	protected void destroy() {
		SimulationComponentManager manager = SimulationComponentManager.get();
		manager.removeSimulationComponent(this);
	}

	public void addForce(float x, float y) {
		this.externalForceX += x;
		this.externalForceY += y;

		this.active = true;
	}

	public float getVelocityX() {
		return velocityX;
	}

	public float getVelocityY() {
		return velocityY;
	}

	public void setVelocity(float x, float y) {
		this.velocityX = x;
		this.velocityY = y;
		this.active = true;
	}

	public float getAccelerationX() {
		return accelerationX;
	}

	public void setAccelerationX(float accelerationX) {
		this.accelerationX = accelerationX;
		this.active = true;
	}

	public float getAccelerationY() {
		return accelerationY;
	}

	public void setAccelerationY(float accelerationY) {
		this.accelerationY = accelerationY;
		this.active = true;
	}

	public float getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

	public float getFriction() {
		return friction;
	}

	public void setFriction(float friction) {
		this.friction = friction;
	}

	public float getExternalForceX() {
		return externalForceX;
	}

	public void setForce(float x, float y) {
		this.externalForceX = x;
		this.externalForceY = y;
	}

	public float getExternalForceY() {
		return externalForceY;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setVelocityX(float velocityX) {
		this.velocityX = velocityX;
	}

	public void setVelocityY(float velocityY) {
		this.velocityY = velocityY;
	}

	public void setExternalForceX(float externalForceX) {
		this.externalForceX = externalForceX;
	}

	public void setExternalForceY(float externalForceY) {
		this.externalForceY = externalForceY;
	}

	public void resetForce() {
		this.externalForceX = 0.f;
		this.externalForceY = 0.f;
	}

	public void simulate(float deltaTime) {
		if (!active || mass <= 0.0f) {// Unmoveable object
			if(mass<=0.0f)
				resetForce();
			return;
		}
		
		float forceX = this.externalForceX - (this.friction * this.velocityX);
		float forceY = this.externalForceY - (this.friction * this.velocityY);
//		float forceX = this.externalForceX;
//		float forceY = this.externalForceY;
		
		this.accelerationX = forceX / this.mass;
		
		this.accelerationY = forceY / this.mass;
		
		boolean shouldSleep = false;

		if (Math.abs(accelerationX) < TOLERANCE) {
			accelerationX = 0.0f;
		}
		if (Math.abs(accelerationY) < TOLERANCE) {
			accelerationY = 0.0f;
		}
		
		resetForce();

		this.velocityX += this.accelerationX * deltaTime;
		this.velocityX -= this.friction * this.velocityX * deltaTime;
		this.velocityY += this.accelerationY * deltaTime;
		this.velocityY -= this.friction * this.velocityY * deltaTime;

		if (Math.abs(velocityX) < TOLERANCE) {
			velocityX = 0.0f;
			shouldSleep=true;
		}
		if (Math.abs(velocityY) < TOLERANCE) {
			velocityY = 0.0f;
		}
		else 
			shouldSleep = false;
		
		if(shouldSleep) 
			active = false;
	}

	@Override
	public int getComponentTypeID() {
		return SimulationComponent.COMPONENT_TYPE;
	}
}
