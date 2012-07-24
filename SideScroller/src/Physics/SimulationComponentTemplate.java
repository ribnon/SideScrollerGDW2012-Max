package Physics;

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

	protected SimulationComponentTemplate(HashMap<String, String> params) {
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

	public String getStringParam(String name, String dflt) {
		if (params.containsKey(name)) {
			return params.get(name);
		} else {
			return dflt;
		}
	}

	public float getFloatParam(String name) {
		return getFloatParam(name, 0.0f);
	}

	public String getStringParam(String name) {
		return getStringParam(name, "");
	}

	public int getIntParam(String name) {
		return getIntParam(name, 0);
	}

	public float getFloatParam(String name, float dflt) {
		if (params.containsKey(name))
			return Float.valueOf(params.get(name));
		else
			return dflt;
	}

	public int getIntParam(String name, int dflt) {
		if (params.containsKey(name)) {
			return Integer.valueOf(params.get(name));
		} else {
			return dflt;
		}
	}

	public boolean isThingOnly() {
		return false;
	}

}
