package gdw.gameplay.player;

import collisionDetection.CollisionDetectionMessage;
import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Entity;
import gdw.entityCore.EntityManager;
import gdw.entityCore.Message;
import gdw.gameplay.GameColor;
import gdw.gameplay.color.ColorSourceComponent;
import gdw.gameplay.color.ColorableComponent;

public class PlayerWeaponComponent extends Component
{
	private GameColor currentColor;
	private float healthIncrement;
	public static final int COMPONENT_TYPE = 11;

	protected PlayerWeaponComponent(ComponentTemplate template)
	{
		super(template);
		if((template != null)&&(template instanceof PlayerWeaponComponentTemplate))
		{
			PlayerWeaponComponentTemplate t = (PlayerWeaponComponentTemplate) template;
			this.currentColor = t.getCurrentColor();
			this.healthIncrement = t.getHealthIncrement();
		}
	}

	@Override
	public int getComponentTypeID()
	{
		return COMPONENT_TYPE;
	}

	@Override
	public void onMessage(Message msg)
	{
		if(msg instanceof CollisionDetectionMessage)
		{
			Entity other = null;
			if(((CollisionDetectionMessage) msg).getIDCandidate1()!= this.getOwner().getID())
			{
				other =EntityManager.getInstance().getEntity(((CollisionDetectionMessage) msg).getIDCandidate1());
			}else
			{
				other =EntityManager.getInstance().getEntity(((CollisionDetectionMessage) msg).getIDCandidate2());
			}
			
			
			if(other.getComponent(ColorSourceComponent.COMPONENT_TYPE)!= null)
			{
				//sourcecolor
				this.currentColor = 
				((ColorSourceComponent)other.getComponent(ColorSourceComponent.COMPONENT_TYPE)).getColor();
			}else if(other.getComponent(ColorableComponent.COMPONENT_TYPE)!= null)
			{
				((ColorableComponent)other.getComponent(ColorableComponent.COMPONENT_TYPE)).mix(currentColor);
			}
		}
	}

	public GameColor getCurrentColor()
	{
		return currentColor;
	}

	public float getHealthIncrement()
	{
		return healthIncrement;
	}
}
