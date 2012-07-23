package Physics;

import java.util.HashMap;

class Component {

};

class ComponentTemplate {
	protected HashMap<String, String> params;

	public ComponentTemplate(HashMap<String, String> params) {
		this.params = params;
	}
};

public class SimulationComponent extends Component {

	private static final float TOLERANCE = 0.01f;

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

	private float forceX;
	private float forceY;

	public SimulationComponent(ComponentTemplate template) {
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
		this.forceX = 0.0f;
		this.forceY = 0.0f;
		active = true;
	}

	protected void destroy() {
		// throw new UnsupportedOperationException();
		// do nothing
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
	}

	public float getAccelerationX() {
		return accelerationX;
	}

	public void setAccelerationX(float accelerationX) {
		this.accelerationX = accelerationX;
	}

	public float getAccelerationY() {
		return accelerationY;
	}

	public void setAccelerationY(float accelerationY) {
		this.accelerationY = accelerationY;
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

	public void resetForce() {
		this.externalForceX = 0.f;
		this.externalForceY = 0.f;
	}

	public void simulate(float deltaTime) {
		if (!active || mass <= 0.0f) // Unmoveable object
			return;
		
		float forceX = this.externalForceX - (this.friction * this.velocityX);
		float forceY = this.externalForceY - (this.friction * this.velocityY);
		
		this.accelerationX = forceX / this.mass;
		
		this.accelerationY = forceY / this.mass;

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
		}
		if (Math.abs(velocityY) < TOLERANCE) {
			velocityY = 0.0f;
		}

	}
}
