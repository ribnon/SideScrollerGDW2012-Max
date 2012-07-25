package gdw.entityCore;

import java.util.HashMap;

import collisionDetection.AABoxCollisionDetectionComponent;
import collisionDetection.AABoxCollisionDetectionComponentTemplate;

import Physics.SimulationComponentTemplate;

public class ComponentTemplateFactory {
	//Singleton-Stuff:
	private static ComponentTemplateFactory instance = null;
	public static ComponentTemplateFactory getInstance(){
		if(instance==null){
			instance = new ComponentTemplateFactory();
		}
		return instance;
	}
	private ComponentTemplateFactory(){
	}

	public ComponentTemplate createComponentTemplate(String name, HashMap<String, String> params){
		if(name.equals("SimulationComponent")) {
			return new SimulationComponentTemplate(params);
		}
		if(name.equals("AABoxCollisionDetectionComponent")) {
			return new AABoxCollisionDetectionComponentTemplate(params);
		}
		return null;
	}
}
