package gdw.gameplay.shooter;

import java.util.ArrayList;

public class Shooter
{
	ArrayList<String> projectiles;
	
	public Shooter()
	{
		projectiles = new ArrayList<String>();
	}
	
	public void addProjectile(String name)
	{
		projectiles.add(name);
	}
	
	public void removeProjectile(String name)
	{
		projectiles.remove(name);
	}
	
	public String getProjectile(int i)
	{
		return projectiles.get(i % projectiles.size());
	}
	
	public int getProjectileCount()
	{
		return projectiles.size();
	}
}
