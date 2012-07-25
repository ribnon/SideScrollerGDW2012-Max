package Physics;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

import java.util.HashMap;

public class SimulationComponentTemplate extends ComponentTemplate {

	private float velocityX;
	private float velocityY;
	private float accelerationX;
	private float accelerationY;
	private float mass;
	private float friction;
	private float externalForceX;
	private float externalForceY;

	public float getVelocityX() {
		return velocityX;
	}

	public float getVelocityY() {
		return velocityY;
	}

	public float getAccelerationX() {
		return accelerationX;
	}

	public float getAccelerationY() {
		return accelerationY;
	}

	public float getMass() {
		return mass;
	}

	public float getFriction() {
		return friction;
	}

	public float getExternalForceX() {
		return externalForceX;
	}

	public float getExternalForceY() {
		return externalForceY;
	}

	public SimulationComponentTemplate(HashMap<String, String> params) {
		super(params);

		friction = getFloatParam("friction", 0.0f);
		mass = getFloatParam("mass", 0.0f);

		externalForceX = getFloatParam("externalForceX", 0.0f);
		externalForceY = getFloatParam("externalForceY", 0.0f);

		velocityX = getFloatParam("velocityX", 0.0f);
		velocityY = getFloatParam("velocityY", 0.0f);

		accelerationX = getFloatParam("accelerationX", 0.0f);
		accelerationY = getFloatParam("accelerationY", 0.0f);

	}

	public Component createComponent() {
		return new SimulationComponent(this);
	}

	public boolean isThingOnly() {
		return false;
	}

}
