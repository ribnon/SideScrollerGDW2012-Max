package gdw.entityCore;

import java.util.ArrayList;
import java.util.HashMap;

public class EntityTemplate {
	private String name;
	private ArrayList<String> baseTemplates;
	private HashMap<String,ComponentTemplate> componentTemplateMap;
	private HashMap<String,HashMap<String,String>> componentParamsMap;
	
	public EntityTemplate(String name, ArrayList<String> baseTemplates, HashMap<String, HashMap<String, String> > componentParamsMap){
		
	}

	public Entity createEntity(float whereX,float whereY){
		return null;
	}
}
