package gdw.astroids.components.spawn;

import gdw.astroids.components.random.RandomPlacementComponent;
import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Entity;
import gdw.entityCore.EntityManager;
import gdw.entityCore.EntityTemplate;
import gdw.entityCore.EntityTemplateManager;

public class SpawnAreaComponent extends Component {

	private int areaX;
	private int areaY;
	private String[] spawns;
	private float[] spawnTime;
	private float[] leftSpawnTime;
	
	public static final int COMPONENT_TYPE = 1011;
	
	public SpawnAreaComponent(ComponentTemplate template) {
		super(template);
		SpawnAreaComponentTemplate t = (SpawnAreaComponentTemplate)template;
		areaX = t.areaX;
		areaY = t.areaY;
		
		
		spawns = new String[t.spawns.length];
		for(int i=0;i<t.spawns.length;++i)
			spawns[i] = t.spawns[i];
		
		spawnTime = new float[t.spawnTime.length];
		leftSpawnTime = new float[t.spawnTime.length];
		for(int i=0;i<t.spawns.length;++i) {
			spawnTime[i] = t.spawnTime[i];
			leftSpawnTime[i] = spawnTime[i];
		}
	}
	
	@Override
	public void tick(float deltaTime) {
		super.tick(deltaTime);
		
		for(int i=0;i<spawns.length;++i) {
			leftSpawnTime[i] -= deltaTime;
			if(leftSpawnTime[i] < 0) {
				EntityTemplate template = EntityTemplateManager.getInstance().getEntityTemplate(spawns[i]);
				float rX = (float)Math.random() * areaX;
				float rY = (float)Math.random() * areaY;
				Entity e = template.createEntity(getOwner().getPosX()+rX, getOwner().getPosY()+rY, 0);
				if(e.getComponent(RandomPlacementComponent.COMPONENT_TYPE)!=null) {
					RandomPlacementComponent rpc = (RandomPlacementComponent)e.getComponent(RandomPlacementComponent.COMPONENT_TYPE);
					rpc.setIgnoreRandom(true);
				}
				
//				System.out.println(""+toSpawn);
				
			}
		}
		
	}

	@Override
	public int getComponentTypeID() {
		return COMPONENT_TYPE;
	}

}
