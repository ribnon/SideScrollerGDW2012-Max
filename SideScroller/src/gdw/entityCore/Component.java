package gdw.entityCore;

public abstract class Component {
	private Entity owner;
	private ComponentTemplate template;
	
	protected Component(ComponentTemplate template) {
		this.template = template;
	}
	
	protected void destroy(){
	}
	
	public Entity getOwner() {
		return owner;
	}

	void setOwner(Entity owner) {
		this.owner = owner;
	}

	public ComponentTemplate getTemplate() {
		return template;
	}

	public abstract int getComponentTypeID();

	public void onMessage(Message msg) {
	}

	public void tick(float deltaTime) {
	}
}
