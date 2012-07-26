package gdw.gameplay.player;

import gdw.control.PlayerInputComponent;
import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Entity;
import gdw.entityCore.EntityManager;
import gdw.entityCore.Message;
import gdw.gameplay.GameColor;
import gdw.gameplay.color.ColorSourceComponent;
import gdw.gameplay.color.ColorableComponent;
import gdw.genericBehavior.AttachableComponent;
import gdw.genericBehavior.AttachmentComponent;
import collisionDetection.CollisionDetectionMessage;

public class PlayerWeaponComponent extends Component
{
	private GameColor currentColor;
	private float healthIncrement;
	public static final int COMPONENT_TYPE = 11;
	
	private int hitState;
	
	private static final int HIT_STATE_PRE_IDLE = 0;
	private static final int HIT_STATE_IDLE = 1;
	private static final int HIT_STATE_ACTION = 2;
	
	private float pivotDistance;
	
	protected PlayerWeaponComponent(ComponentTemplate template)
	{
		super(template);
		currentColor = new GameColor();
		
		hitState = HIT_STATE_PRE_IDLE;
		pivotDistance = 0.0f;
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
				other = EntityManager.getInstance().getEntity(((CollisionDetectionMessage) msg).getIDCandidate1());
			else other = EntityManager.getInstance().getEntity(((CollisionDetectionMessage) msg).getIDCandidate2());
			
			if(other.getComponent(ColorSourceComponent.COMPONENT_TYPE)!= null)
				this.currentColor = ((ColorSourceComponent)other.getComponent(ColorSourceComponent.COMPONENT_TYPE)).getColor();
			else if(other.getComponent(ColorableComponent.COMPONENT_TYPE)!= null)
				((ColorableComponent)other.getComponent(ColorableComponent.COMPONENT_TYPE)).mix(currentColor);
		}
	}

	public GameColor getCurrentColor()
	{
		return currentColor;
	}
	
	public void tick(float deltaTime)
	{
		AttachableComponent attachable = (AttachableComponent) getOwner().getComponent(AttachableComponent.COMPONENT_TYPE);
		if (attachable == null) return;
		
		Entity player = EntityManager.getInstance().getEntity(attachable.getAttachedToEntityID());
		Entity weapon = getOwner();
		
		PlayerBehaviorComponent behavior = (PlayerBehaviorComponent) player.getComponent(PlayerBehaviorComponent.COMPONENT_TYPE);
		AttachmentComponent attachment = (AttachmentComponent) player.getComponent(AttachmentComponent.COMPONENT_TYPE);
		PlayerInputComponent input = (PlayerInputComponent) player.getComponent(PlayerInputComponent.COMPONENT_TYPE);
				
		if (player == null) return;
		if (behavior == null) return;
		if (input == null) return;
		
		switch (hitState)
		{
			case HIT_STATE_PRE_IDLE:
				float idleAngle = behavior.getIdleAngle();
				float pivotX = player.getPosX() + attachment.getAttachPointX();
				float pivotY = player.getPosY() + attachment.getAttachPointY();
				float wPosX = weapon.getPosX();
				float wPosY = weapon.getPosY();
				float distX = pivotX - wPosX;
				float distY = pivotY - wPosY;
				pivotDistance = (float) Math.sqrt(distX * distX + distY * distY);
				setAngleAndPosition(idleAngle, input.getDirectionIsRight());
				hitState = HIT_STATE_IDLE;
				break;
				
			case HIT_STATE_IDLE:
				idleAngle = behavior.getIdleAngle();
				setAngleAndPosition(idleAngle, input.getDirectionIsRight());
				
			case HIT_STATE_ACTION:
				float hitActive = behavior.getHitActive();
				
				if (hitActive >= 1.0f)
					hitState = HIT_STATE_IDLE;
				else
				{
					float startAngle = behavior.getStartAngle();
					float hitAngle = behavior.getHitAngle();
					float hitAngleInterpolated = hitActive * hitAngle;
					setAngleAndPosition(startAngle - hitAngleInterpolated, input.getDirectionIsRight());
				}
		}
	}
	
	private void setAngleAndPosition(float angle, boolean right)
	{
		float radAngle = (float) Math.toRadians(angle);
		AttachableComponent attachable = (AttachableComponent) getOwner().getComponent(AttachableComponent.COMPONENT_TYPE);
		Entity player = EntityManager.getInstance().getEntity(attachable.getAttachedToEntityID());
		Entity weapon = getOwner();
		AttachmentComponent attachment = (AttachmentComponent) player.getComponent(AttachmentComponent.COMPONENT_TYPE);
		
		float attachmentX = attachment.getAttachPointX();
		float attachmentY = attachment.getAttachPointY();
		float playerX = player.getPosX();
		float playerY = player.getPosY();
		
		Math.abs(attachmentX);
		
		if (!right)
		{
			angle *= -1;
			attachmentX *= -1;
		}
		
		float weaponPosY = -pivotDistance;
		float newWeaponPosX = (float)(-Math.sin(radAngle) * weaponPosY);
		float newWeaponPosY = (float)(Math.cos(radAngle) * weaponPosY);
		
		newWeaponPosX += playerX + attachmentX;
		newWeaponPosY += playerY + attachmentY;
		
		attachment.setAttachOrientation(angle);
		attachment.setAttachPointX(attachmentX);
		attachment.setAttachPointY(attachmentY);
		
		weapon.setOrientation(angle);
		weapon.setPos(newWeaponPosX, newWeaponPosY);
	}
}
