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

	//Wird von server-seitigem Code aufgerufen um Entities zu spawnen:
	public Entity createEntity(float whereX,float whereY, float orientation){
		return null;//TODO: Implement
	}
	
	//Wird vom Netzwerkcode auf dem Client aufgerufen, um Entities zu replizieren:
	public Entity createEntity(int id, float whereX,float whereY, float orientation){
		return null;//TODO: Implement
	}
}
