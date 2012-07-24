package gdw.entityCore;

public class StaticEntityReference extends EntityReference {
	private int id;
	
	public StaticEntityReference(int id) {
		this.id = id;
	}

	@Override
	public int getID() {
		return id;
	}

}
