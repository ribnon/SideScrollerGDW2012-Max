package gdw.entityCore;

import java.util.HashMap;

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
		return null;
	}
}
