package gdw.gameplay.Player;

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
	public static final int COMPONENT_TYPE = 11;

	protected PlayerWeaponComponent(ComponentTemplate template)
	{
		super(template);
		currentColor = new GameColor();
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
				//((ColorableComponent)other.addComponent(ColorableComponent.COMPONENT_TYPE)).
				//TODO wartena auf implementierung von ColorableComponent
			}
		}
	}

	public GameColor getCurrentColor()
	{
		return currentColor;
	}
	
	
}
