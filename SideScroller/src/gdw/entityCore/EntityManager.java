package gdw.entityCore;

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
	public void loadEntities(String fileName){
		//TODO: Implement
	}
	void unregisterEntity(Entity entity){
		entities.remove(entity.getID());
	}
}
