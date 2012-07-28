package gdw.astroids.components;

import gdw.collisionDetection.CollisionDetectionMessage;
import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Entity;
import gdw.entityCore.EntityManager;
import gdw.entityCore.Message;

public class DestroyableComponent extends Component {

	private int life;
	public int getLife() {
		return life;
	}


	public void setLife(int life) {
		this.life = life;
	}


	public int getDestroyPower() {
		return destroyPower;
	}


	public void setDestroyPower(int destroyPower) {
		this.destroyPower = destroyPower;
	}


	public int getDestroyGroup() {
		return destroyGroup;
	}


	public void setDestroyGroup(int destroyGroup) {
		this.destroyGroup = destroyGroup;
	}

	private int destroyPower;
	private int destroyGroup;
	
	public DestroyableComponent(ComponentTemplate template) {
		super(template);
		DestroyableComponentTemplate t = (DestroyableComponentTemplate)template;
		
		life = t.life;
		destroyPower = t.destroyPower;
		destroyGroup = t.destroyGroup;
	}

	public static final int COMPONENT_TYPE = 1003;
	
	@Override
	public int getComponentTypeID() {
		// TODO Auto-generated method stub
		return COMPONENT_TYPE;
	}
	
	
	@Override
	public void onMessage(Message msg) {
		super.onMessage(msg);
		if(msg instanceof CollisionDetectionMessage) {
			CollisionDetectionMessage cmsg = (CollisionDetectionMessage)msg;
			
			Entity c1 = EntityManager.getInstance().getEntity(cmsg.getIDCandidate1());
			Entity c2 = EntityManager.getInstance().getEntity(cmsg.getIDCandidate2());
			
			if((c1.getComponent(COMPONENT_TYPE) != null) 
			&& (c2.getComponent(COMPONENT_TYPE)!=null)) {
				DestroyableComponent c1_destroy = (DestroyableComponent)c1.getComponent(COMPONENT_TYPE);
				DestroyableComponent c2_destroy = (DestroyableComponent)c2.getComponent(COMPONENT_TYPE);
				
				if((c1_destroy.destroyGroup ^ c2_destroy.destroyGroup) != 0) {
					c1_destroy.life -= c2_destroy.destroyPower;
					c2_destroy.life -= c1_destroy.destroyPower;
				}
			}
		}
	}

}
