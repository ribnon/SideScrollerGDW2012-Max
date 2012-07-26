package gdw.gameplay.player;

import java.util.LinkedList;

public class PlayerSubSystem
{
	private static PlayerSubSystem singleton;
	
	private LinkedList<PlayerBehaviorComponent> listOfPlayerBehaviorComponents;
	
	private PlayerSubSystem()
	{
		this.listOfPlayerBehaviorComponents = new LinkedList<PlayerBehaviorComponent>();
	}
	
	public static PlayerSubSystem getInstance()
	{
		if(PlayerSubSystem.singleton == null)
		{
			PlayerSubSystem.singleton = new PlayerSubSystem();
		}
		return PlayerSubSystem.singleton;
	}
	
	@SuppressWarnings("unchecked")
	public LinkedList<PlayerBehaviorComponent> getAllPlayerBehaviorComponent()
	{
		return (LinkedList<PlayerBehaviorComponent>) this.listOfPlayerBehaviorComponents.clone();
	}
	
	public void addPlayerBehaviorComponent (PlayerBehaviorComponent item)
	{
		this.listOfPlayerBehaviorComponents.add(item);
	}
	
	public void removePlayerBehaviorComponent(PlayerBehaviorComponent item)
	{
		this.listOfPlayerBehaviorComponents.remove(item);
	}
}
