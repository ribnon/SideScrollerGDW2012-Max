package gdw.entityCore;

import java.util.HashMap;

public class EntityTemplateManager {
	//Singleton-Stuff:
	private static EntityTemplateManager instance = null;
	public static EntityTemplateManager getInstance(){
		if(instance==null){
			instance = new EntityTemplateManager();
		}
		return instance;
	}
	private EntityTemplateManager(){
	}

	private HashMap<String, EntityTemplate> entityTemplates = new HashMap<String, EntityTemplate>();
	
	public void loadEntityTemplates(String fileName){
		//TODO Implement
	}
	
	public EntityTemplate getEntityTemplate(String name){
		if(entityTemplates.containsKey(name)){
			return entityTemplates.get(name);
		}
		else{
			return null;
		}
	}
}
