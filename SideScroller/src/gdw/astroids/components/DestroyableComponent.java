package gdw.astroids.components;

import gdw.astroids.sound.SoundPlayer;
import gdw.collisionDetection.CollisionDetectionMessage;
import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Entity;
import gdw.entityCore.EntityManager;
import gdw.entityCore.EntityTemplate;
import gdw.entityCore.EntityTemplateManager;
import gdw.entityCore.Message;
import gdw.gameplay.shooter.ProjectileComponent;

public class DestroyableComponent extends Component {

	private int life;
	
	private boolean wasDestroyed;
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
		
		wasDestroyed = false;
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
			&& (c2.getComponent(COMPONENT_TYPE) != null)) {
				DestroyableComponent c1_destroy = (DestroyableComponent)c1.getComponent(COMPONENT_TYPE);
				DestroyableComponent c2_destroy = (DestroyableComponent)c2.getComponent(COMPONENT_TYPE);
				
				if((c1_destroy.destroyGroup & c2_destroy.destroyGroup) != 0 && (c1_destroy.destroyGroup != c2_destroy.destroyGroup)
					&& (!c1_destroy.wasDestroyed || !c2_destroy.wasDestroyed)) {
					c1_destroy.life -= c2_destroy.destroyPower;
					if(c1_destroy.life <= 0 && !c1_destroy.wasDestroyed) {
						createDestruction();
						c1.markForDestroy();
						c1_destroy.wasDestroyed = true;
					}
					
					c2_destroy.life -= c1_destroy.destroyPower;
					if(c2_destroy.life <= 0 && !c2_destroy.wasDestroyed) {
						c2_destroy.createDestruction();
						c2.markForDestroy();
						c2_destroy.wasDestroyed = true;
					}
				}
			}
		}
	}
	
	public void createDestruction() {
		EntityTemplate template = null;
		if (getOwner().getComponent(ProjectileComponent.COMPONENT_TYPE) != null) {
			template = EntityTemplateManager.getInstance().getEntityTemplate("Explosion");
			template.createEntity(getOwner().getPosX(), getOwner().getPosY(), 0.0f);
		}
		if (getOwner().getComponent(AstroidsPlayerMarkerComponent.COMPONENT_TYPE) != null) {
			template = EntityTemplateManager.getInstance().getEntityTemplate("PlayerExplosion");
			template.createEntity(getOwner().getPosX(), getOwner().getPosY(), 0.0f);
			SoundPlayer.getInstance().playSound(SoundPlayer.SOUND_EXPLOSION);
		}
		if (getOwner().getComponent(AstroidsAstroidMarkerComponent.COMPONENT_TYPE) != null) {
			template = EntityTemplateManager.getInstance().getEntityTemplate("AstroidExplosion");
			template.createEntity(getOwner().getPosX(), getOwner().getPosY(), 0.0f);
			SoundPlayer.getInstance().playSound(SoundPlayer.SOUND_EXPLOSION);
		}
		
		if(getOwner().getComponent(DecayComponent.COMPONENT_TYPE) != null) {
			DecayComponent dc = (DecayComponent)getOwner().getComponent(DecayComponent.COMPONENT_TYPE);
			for(int p=0;p<dc.getDecayIn().length;++p) {
				System.out.println("Spawn "+dc.getDecayIn()[p]);
				template = EntityTemplateManager.getInstance().getEntityTemplate(dc.getDecayIn()[p]);
				float toSpawn = (((float)Math.random()) * dc.getAveragePieces()[p]);
				toSpawn = Math.max(dc.getSpawnRange()[p][0], Math.min(toSpawn, dc.getSpawnRange()[p][1]));
//				System.out.println(""+toSpawn);
				for(int i=0;i<(int)toSpawn;++i) {
					template.createEntity(getOwner().getPosX(), getOwner().getPosY(), 0);
				}
			}
		}
	}

}
