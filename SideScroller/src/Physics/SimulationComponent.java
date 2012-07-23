package Physics;
class Component {
	
};
class ComponentTemplate {
	
};

public class SimulationComponent extends Component {
	private float velocityX;
	private float velocityY;
	private float accelerationX;
	private float accelerationY;
	private float mass;
	private float friction;
	private float externalForceX;
	private float externalForceY;
	private boolean active;
	
	
	public SimulationComponent(ComponentTemplate template) {
		
	}
	
	protected void destroy() {
		throw new UnsupportedOperationException();
	}
	
	public void addForce(float x, float y) {
		
	}
	
	public float getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(float velocityX) {
		this.velocityX = velocityX;
	}

	public float getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(float velocityY) {
		this.velocityY = velocityY;
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

	public void setExternalForceX(float externalForceX) {
		this.externalForceX = externalForceX;
	}

	public float getExternalForceY() {
		return externalForceY;
	}

	public void setExternalForceY(float externalForceY) {
		this.externalForceY = externalForceY;
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
		this.accelerationX += this.externalForceX / this.mass;
		this.accelerationY += this.externalForceY / this.mass;
		
		this.velocityX += this.accelerationX * deltaTime;
		this.velocityY += this.accelerationY * deltaTime;
	}
}
