package gdw.entityCore;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.tiled.TiledMap;

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
		reinitialize();
	}

	private HashMap<String, EntityTemplate> entityTemplates = new HashMap<String, EntityTemplate>();
	
	public void loadEntityTemplates(String fileName) throws IOException{
		BufferedReader rdr = new BufferedReader(new FileReader(fileName));
		String line=null;
		String templateName=null;
		String componentName=null;
		ArrayList<String> baseTemplates = null;
		HashMap<String,HashMap<String, String>> componentParamsMap=null;
		while((line=rdr.readLine())!=null){
			if(line.length()==0) continue;
			//ByteOrderMark entfernen:
			if(line.charAt(0)==65279){
				line = line.substring(1);
			}
			line = line.trim();
			if(line.length()==0) continue;
			if(line.charAt(0)=='#') continue;
			int equalsPos = line.indexOf('=');
			if(line.startsWith("Template")){
				if(line.length()<10) continue;
				if(line.charAt(8)!=' ') continue;
				String templateNameStr=line.substring(9);
				if(templateNameStr.length()==0) templateNameStr=null;
				if(templateName!=null){
					entityTemplates.put(templateName, new EntityTemplate(templateName, baseTemplates, componentParamsMap));
				}
				templateName=templateNameStr;
				baseTemplates=new ArrayList<String>();
				componentParamsMap=new HashMap<String, HashMap<String,String>>();
			}
			else if(line.startsWith("Component")){
				if(templateName==null) continue;
				if(line.length()<11) continue;
				if(line.charAt(9)!=' ') continue;
				String compNameStr=line.substring(10);
				if(compNameStr.length()==0) compNameStr=null;
				componentName=compNameStr;
				if(!componentParamsMap.containsKey(componentName)){
					componentParamsMap.put(componentName, new HashMap<String,String>());
				}
			}
			else if(line.startsWith("Base")){
				if(templateName==null) continue;
				if(line.length()<6) continue;
				if(line.charAt(4)!=' ') continue;
				String baseTemplateNameStr=line.substring(5);
				if(baseTemplateNameStr.length()==0) continue;
				baseTemplates.add(baseTemplateNameStr);
			}
			else if(line.startsWith("Include")){
				if(line.length()<9) continue;
				if(line.charAt(7)!=' ') continue;
				String includeFileNameStr=line.substring(8);
				if(includeFileNameStr.length()==0) continue;
				try{
					loadEntityTemplates(includeFileNameStr);
				}
				catch(IOException e){
					System.err.println("IOException beim Lesen eines Includefiles: " + e.getMessage());
					e.printStackTrace();
				}
			}
			else if(equalsPos>0){
				if(componentName==null) continue;
				String paramName = line.substring(0, equalsPos);
				String paramValue = line.substring(equalsPos+1);
				if(paramName.length()==0) continue;
				componentParamsMap.get(componentName).put(paramName, paramValue);
			}
			else continue;
		}
		if(templateName!=null){
			entityTemplates.put(templateName, new EntityTemplate(templateName, baseTemplates, componentParamsMap));
		}
	}
	
	public void loadEntityTemplatesFromLevel(){
		String mapTemplatesPrefix = "FromMap_";
		MyTiledMap map = Level.getInstance().getMap();
		int objectGroups = map.getObjectGroupCount();
		for(int og=0;og<objectGroups;++og){
			int groupObjects = map.getObjectCount(og);
			for(int go=0;go<groupObjects;++go){
				HashMap<String,HashMap<String, String>> componentParamsMap=new HashMap<String, HashMap<String,String>>();
				ArrayList<String> propNames =  map.getObjectPropertyNames(og, go);
				for(String propName: propNames){
					int pointPos = propName.indexOf('.');
					if(pointPos<1) continue;
					String compName = propName.substring(0, pointPos);
					if(!componentParamsMap.containsKey(compName)) componentParamsMap.put(compName, new HashMap<String,String>());
					String paramName = propName.substring(pointPos+1);
					if (paramName.length()==0) continue;
					String propVal = map.getObjectProperty(og, go, paramName, null);
					if(propVal!=null) componentParamsMap.get(compName).put(paramName, propVal);
				}
				String[] templateStrings = map.getObjectProperty(og, go, "Template", "").split(";");
				ArrayList<String> baseTemplateNames = new ArrayList<String>();
				for(String templateString: templateStrings){
					if(templateString.length()==0) continue;
					baseTemplateNames.add(templateString);
				}
//				String objectType = map.getObjectType(og, go);
//				if(objectType.length()>0){
//					baseTemplateNames.add(objectType);
//				}
				if(!componentParamsMap.containsKey("OOBoxCollisionDetection")){
					componentParamsMap.put("OOBoxCollisionDetection", new HashMap<String,String>());
					componentParamsMap.get("OOBoxCollisionDetection").put("halfExtentX",Float.toString(map.getObjectWidth(og, go)*0.5f));
					componentParamsMap.get("OOBoxCollisionDetection").put("halfExtentY",Float.toString(map.getObjectHeight(og, go)*0.5f));
				}
				String templateName = mapTemplatesPrefix + map.getObjectName(og, go);
				entityTemplates.put(templateName, new EntityTemplate(templateName, baseTemplateNames, componentParamsMap));
			}
		}
	}
	
	public void reinitialize(){
		entityTemplates.clear();
		makeCollisionBoxTemplate();
	}
	
	private void makeCollisionBoxTemplate(){
		TiledMap map = Level.getInstance().getMap();
		int tileWidth = map.getTileWidth();
		int tileHeight = map.getTileHeight();
		HashMap<String,HashMap<String,String>> compParams = new HashMap<String,HashMap<String,String>>();
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("halfExtentX", Float.toString(tileWidth*0.5f));
		params.put("halfExtentY", Float.toString(tileHeight*0.5f));
		compParams.put("AABoxCollisionDetection", params);
		params = new HashMap<String,String>();
		params.put("impassableFromTop", "1");
		params.put("impassableFromSide", "1");
		compParams.put("CollisionReaction", params);
		entityTemplates.put(" CollisionTile <internal> ", new EntityTemplate(" CollisionTile <internal> ", new ArrayList<String>(), compParams));
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
