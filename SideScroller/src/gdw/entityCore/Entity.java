package gdw.entityCore;

import gdw.network.NetSubSystem;

import java.util.HashMap;

public class Entity {
	private int id;
	private float posX;
	private float posY;
	private float orientation;
	private HashMap<Integer, Component> components = new HashMap<Integer, Component>();
	private EntityTemplate template;
	private boolean destroyFlag=false;
	
	//package-private um Erzeugung nur dorch Klassen aus dem Entity-Package zuzulassen:
	//Erzeugung für Nutzerklassen über EntityManager bzw. EntityTemplate
	Entity(int id, EntityTemplate template) {
		this.id=id;
		this.template = template;
	}
	void destroy(){
		
		for(Component comp : components.values()){
			comp.destroy();
		}
		components.clear();
		EntityManager.getInstance().unregisterEntity(this);
	}
	
	public void markForDestroy(){
		if(!EntityManager.getInstance().isOfflineMode()) if(NetSubSystem.getInstance().isServer()) NetSubSystem.getInstance().sendDeSpawn(id);
		destroyFlag=true;
	}
	public boolean getDestroyFlag(){
		return destroyFlag;
	}
	public int getID(){
		return id;
	}
	
	public float getPosX(){
		return posX;
	}

	public float getPosY(){
		return posY;
	}
	
	public void setPos(float x, float y){
		posX = x;
		posY = y;
	}
	
	public void setPosX(float x){
		posX=x;
	}
	public void setPosY(float y){
		posY=y;
	}
	
	public float getOrientation() {
		return orientation;
	}
	public void setOrientation(float orientation) {
		this.orientation = orientation;
	}
	public void addComponent(Component comp){
		components.put(comp.getComponentTypeID(), comp);
		comp.setOwner(this);
	}
	public Component getComponent(int componentTypeID){
		return components.get(componentTypeID);
	}
	public void removeComponent(int componentTypeID){
		components.remove(componentTypeID).destroy();
	}
	public void removeComponent(Component comp){
		Integer tmp=null;
		for(Integer key: components.keySet()){
			if(components.get(key)==comp){
				tmp=key;
				break;
			}
		}
		if(tmp!=null){
			removeComponent(tmp);
		}
	}
	public void message(Message msg){
		for(Component comp: components.values()){
			comp.onMessage(msg);
		}
	}
	public EntityTemplate getTemplate() {
		return template;
	}
	public void tick(float deltaTime){
		for(Component comp: components.values()){
			comp.tick(deltaTime);
		}
	}
	public EntityReference getReference(){
		return new StaticEntityReference(id);
	}
	public EntityReference registerNamed(String name){
		NamedEntityReference.setEntityID(name, id);
		return new NamedEntityReference(name);
	}
}
