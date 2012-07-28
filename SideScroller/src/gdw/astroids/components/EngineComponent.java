package gdw.astroids.components;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class EngineComponent extends Component {

	float power;
	float breakPower;
	float rotation;
	
	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public float getPower() {
		return power;
	}

	public void setPower(float power) {
		this.power = power;
	}

	public float getBreakPower() {
		return breakPower;
	}

	public void setBreakPower(float breakPower) {
		this.breakPower = breakPower;
	}

	public EngineComponent(ComponentTemplate template) {
		super(template);
		EngineComponentTemplate t = (EngineComponentTemplate)template;
		power = t.power;
		breakPower = t.breakPower;
		rotation = t.rotation;
	}

	public static final int COMPONENT_TYPE = 1001;
	
	@Override
	public int getComponentTypeID() {
		// TODO Auto-generated method stub
		return COMPONENT_TYPE;
	}

}
