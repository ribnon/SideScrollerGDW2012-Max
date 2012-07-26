package gdw.entityCore;

import gdw.network.NetSubSystem;

import java.util.ArrayList;
import java.util.HashMap;

public class EntityTemplate {
	private String name;
	private ArrayList<String> baseTemplates;
	private HashMap<String,ComponentTemplate> componentTemplateMap=new HashMap<String,ComponentTemplate>();
	private HashMap<String,HashMap<String,String>> componentParamsMap;
	
	public EntityTemplate(String name, ArrayList<String> baseTemplates, HashMap<String, HashMap<String, String> > componentParamsMap){
		this.name = name;
		this.baseTemplates = baseTemplates;
		this.componentParamsMap = componentParamsMap;
		
		for(String baseTemplateName: baseTemplates){
			EntityTemplate baseTemplate = EntityTemplateManager.getInstance().getEntityTemplate(baseTemplateName);
			if(baseTemplate!=null){
				mergeInBaseParams(baseTemplate.componentParamsMap);
			}
		}
		
		for(String compName: componentParamsMap.keySet()){
			ComponentTemplate compTemplate = ComponentTemplateFactory.getInstance().createComponentTemplate(compName, componentParamsMap.get(compName));
			if(compTemplate==null) continue;
			this.componentTemplateMap.put(compName, compTemplate);
		}
	}

	private void mergeInBaseParams(HashMap<String,HashMap<String,String>> baseCompParamsMap){
		for(String baseCompName: baseCompParamsMap.keySet()){
			if(!componentParamsMap.containsKey(baseCompName)){
				componentParamsMap.put(baseCompName, new HashMap<String,String>());
			}
			HashMap<String,String> myParamMap = componentParamsMap.get(baseCompName);
			HashMap<String,String> baseParamMap = baseCompParamsMap.get(baseCompName);
			for(String baseParamName: baseParamMap.keySet()){
				if(!myParamMap.containsKey(baseParamName)){
					myParamMap.put(baseParamName, baseParamMap.get(baseParamName));
				}
			}
		}
	}
	
	//Wird von server-seitigem Code aufgerufen um Entities zu spawnen:
	public Entity createEntity(float whereX,float whereY, float orientation){
		if(EntityManager.getInstance()==null)
			System.out.println("EntityManager null");
		int id = EntityManager.getInstance().getNextID();
		if(!EntityManager.getInstance().isOfflineMode()) NetSubSystem.getInstance().sendSpawn(name,id,whereX,whereY,orientation);
		return createEntity(id, whereX, whereY, orientation);
	}
	
	//Wird vom Netzwerkcode auf dem Client aufgerufen, um Entities zu replizieren:
	public Entity createEntity(int id, float whereX,float whereY, float orientation){
		Entity ent = EntityManager.getInstance().createEntity(id, whereX, whereY, orientation, this);
		for(ComponentTemplate compTemplate: componentTemplateMap.values()){
			if(!EntityManager.getInstance().isOfflineMode()) if(compTemplate.isThingOnly() && !NetSubSystem.getInstance().isServer()) continue;
			if(compTemplate == null)
				System.out.println("compTemplate null");
			ent.addComponent(compTemplate.createComponent());
		}
		ent.message(new EntityConstructedMessage());
		return ent;
	}
}
