package gdw.entityCore;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class EntityManager {
	//Singleton-Stuff:
	private static EntityManager instance = null;
	public static EntityManager getInstance(){
		if(instance==null){
			instance = new EntityManager();
		}
		return instance;
	}
	private EntityManager(){
	}
	
	private HashMap<Integer, Entity> entities = new HashMap<Integer, Entity>();
	private int nextID = 1;
	private boolean offlineMode=false;
	
	public boolean isOfflineMode() {
		return offlineMode;
	}
	public void setOfflineMode(boolean offlineMode) {
		this.offlineMode = offlineMode;
	}
	public int getNextID(){
		return nextID++;
	}
	Entity createEntity(int id,float posX, float posY, float orientation, EntityTemplate template){
		Entity ent = new Entity(id, template);
		ent.setPos(posX, posY);
		ent.setOrientation(orientation);
		entities.put(id, ent);
		return ent;
	}
	public Entity getEntity(int id){
		if(entities.containsKey(id)){
			return entities.get(id);
		}
		else{
			return null;
		}
	}
	public void loadEntities(String fileName) throws IOException{
		BufferedReader rdr = new BufferedReader(new FileReader(fileName));
		String line=null;
		while((line=rdr.readLine())!=null){
			if(line.length()==0) continue;
			//ByteOrderMark entfernen:
			if(line.charAt(0)==65279){
				line = line.substring(1);
			}
			line = line.trim();
			if(line.length()==0) continue;
			if(line.charAt(0)=='#') continue;
			if(line.startsWith("Entity")){
				String[] tokens = line.split(" ");
				String templateName=null;
				String posXStr=null;
				String posYStr=null;
				String orientationStr=null;
				String entityName=null;
				if(tokens.length == 5){
					templateName=tokens[1];
					posXStr=tokens[2];
					posYStr=tokens[3];
					orientationStr=tokens[4];
				}
				else if(tokens.length == 7){
					templateName=tokens[1];
					posXStr=tokens[2];
					posYStr=tokens[3];
					orientationStr=tokens[4];
					if(tokens[5].equalsIgnoreCase("as")){
						entityName=tokens[6];
					}
				}
				else continue;
				EntityTemplate template = EntityTemplateManager.getInstance().getEntityTemplate(templateName);
				if(template==null) continue;
				try {
					float posX = Float.parseFloat(posXStr);
					float posY = Float.parseFloat(posYStr);
					float orientation = Float.parseFloat(orientationStr);
					Entity ent = template.createEntity(posX, posY, orientation);
					if(entityName!=null){
						NamedEntityReference.setEntityID(entityName, ent.getID());
					}
				} catch (NumberFormatException e) {
					continue;
				}	
			}
			else continue;
		}
	}
	public void loadEntitiesFromLevel(){
		//TODO: Implement
	}
	
	/**
	 * Nuke the site from Orbit.
	 */
	public void deleteAllEntities(){
		for(Entity ent: entities.values()){
			ent.destroy();
		}
	}
	
	void unregisterEntity(Entity entity){
		entities.remove(entity.getID());
	}
}
