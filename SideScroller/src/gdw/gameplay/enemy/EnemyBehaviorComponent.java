package gdw.gameplay.enemy;

import java.util.LinkedList;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.StaticEntityReference;
import gdw.gameplay.player.PlayerBehaviorComponent;
import gdw.gameplay.player.PlayerSubSystem;
import gdw.genericBehavior.FollowComponent;


public class EnemyBehaviorComponent extends Component
{

	public final static int COMPONENT_TYPE = 12;

	private boolean hostile;
	private float aggroRange;
	private String shootEntityTemplate;

	public EnemyBehaviorComponent(ComponentTemplate template)
	{
		super(template);
		
		if (template != null && template instanceof EnemyBehaviorComponentTemplate)
		{
			EnemyBehaviorComponentTemplate temp = (EnemyBehaviorComponentTemplate) template;
			this.hostile = temp.isHostile();
			this.aggroRange = temp.getAggroRange();
			this.shootEntityTemplate = temp.getShootEntityTemplate();
		}
	}
	
	
	public void tick(float deltaTime)
	{
		//wenn hostile false dann followcomponenten leeren
		if(!hostile)
		{
			((FollowComponent)this.getOwner().getComponent(FollowComponent.COMPONENT_TYPE)).setTargetEntityID(null);
		}else
		{
			//hole alle Spielerkomponenten
			LinkedList<PlayerBehaviorComponent> playerList = PlayerSubSystem.getInstance().getAllPlayerBehaviorComponent();
			float closestRange = aggroRange * aggroRange;
			PlayerBehaviorComponent closest = null;
			float myX = this.getOwner().getPosX();
			float myY = this.getOwner().getPosY();
			for(PlayerBehaviorComponent pComp : playerList)
			{
				//test dist
				float distX = pComp.getOwner().getPosX() - myX;
				float distY = pComp.getOwner().getPosY() - myY;
				float dis = distX * distX + distY * distY;
				if(dis < closestRange)
				{
					closestRange = dis;
					closest = pComp;
				}
			}
			if(closest != null)
			{
				StaticEntityReference ref = new StaticEntityReference(closest.getOwner().getID());
				((FollowComponent)this.getOwner().getComponent(FollowComponent.COMPONENT_TYPE))
				.setTargetEntityID(ref);
			}
		}
	}

	@Override
	public int getComponentTypeID()
	{
		return EnemyBehaviorComponent.COMPONENT_TYPE;
	}
	
	public boolean isHostile()
	{
		return hostile;
	}


	public void setHostile(boolean hostile)
	{
		this.hostile = hostile;
	}


	public float getAggroRange()
	{
		return aggroRange;
	}


	public void setAggroRange(float aggroRange)
	{
		this.aggroRange = aggroRange;
	}


	public String getShootEntityTemplate()
	{
		return shootEntityTemplate;
	}


	public void setShootEntityTemplate(String shootEntityTemplate)
	{
		this.shootEntityTemplate = shootEntityTemplate;
	}
}
