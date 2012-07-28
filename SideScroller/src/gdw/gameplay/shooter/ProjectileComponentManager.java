package gdw.gameplay.shooter;

import gdw.collisionDetection.CollisionDetectionComponentManager;

import java.util.ArrayList;

public class ProjectileComponentManager
{
	private static ProjectileComponentManager pcm;
	
	ArrayList<ProjectileComponent> projectileList;
	
	private ProjectileComponentManager()
	{
		pcm = this;
		projectileList = new ArrayList<ProjectileComponent>();
	}
	
	public static ProjectileComponentManager getInstance()
	{
		if (pcm == null) pcm = new ProjectileComponentManager();
		return pcm;
	}
	
	public void registerProjectileComponent(ProjectileComponent p)
	{
		projectileList.add(p);
	}
	
	public void unregisterProjectileComponent(ProjectileComponent p)
	{
		projectileList.remove(p);
	}
	
	public void simulateProjectiles(float deltaTime)
	{
		int simSteps = 5;
		float dt = deltaTime / 5;
		
		CollisionDetectionComponentManager cdcm = CollisionDetectionComponentManager.getInstance();
		
		for(int i=0;i<simSteps;++i) {
			for (ProjectileComponent p : projectileList) {
				p.simulate(dt);
				cdcm.detectCollisions(p.getOwner());
			}
		}
	}
}
