package gdw.entityCore;

public abstract class EntityReference {
	public abstract int getID();
	public Entity getEntity(){
		return EntityManager.getInstance().getEntity(getID());
	}
}
