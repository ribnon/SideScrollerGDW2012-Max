package gdw.gameplay.color;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.EntityManager;
import gdw.entityCore.EntityReference;
import gdw.gameplay.GameColor;
import gdw.gameplay.color.messageType.ColorableClearMessage;
import gdw.gameplay.enemy.EnemyBehaviorComponent;

public class ColorableComponent extends Component
{

	public static final int COMPONENT_TYPE = 22;

	private GameColor targetColor;
	private GameColor currentColor;
	private float remainingTimeToFade;//counter
	private float fadeTime;//fest
	private float friendlyDuration;//fest
	private float friendlyTimer;//counter
	private int finishedCount;//fest
	private int segmentCount;//counter
	
	private EntityReference notifyOnClear;

	public ColorableComponent(ComponentTemplate template)
	{
		super(template);

		if (template != null && template instanceof ColorableComponentTemplate)
		{
			ColorableComponentTemplate temp = (ColorableComponentTemplate) template;
			targetColor = temp.getTargetColor();
			currentColor = temp.getCurrentColor();
			remainingTimeToFade = temp.getRemainingTimeToFade();
			fadeTime = temp.getFadeTime();
			friendlyDuration = temp.getFriendlyDuration();
			friendlyTimer = temp.getFriendlyTimer();
			finishedCount = temp.getFinishedCount();
			segmentCount = temp.getSegmentCount();
			notifyOnClear = temp.getNotifyOnClear();
		}
	}

	public void tick(float deltaTime)
	{
		/*if(finishedCount == segmentCount)
		{
			//wenn komplett
			friendlyTimer -= deltaTime;
			if(friendlyTimer <= 0.0f)
			{
				//verlier ein segment
				segmentCount--;
				((EnemyBehaviorComponent)this.getOwner().getComponent(EnemyBehaviorComponent.COMPONENT_TYPE)).setHostile(true);				
			}else*/
		if(!currentColor.isTransparent())
		{
			//auf fade testen
			this.remainingTimeToFade -= deltaTime;
			if(remainingTimeToFade <= 0.0f)
			{
				this.currentColor.setToTransparent();
			}
		}
	}

	@Override
	public int getComponentTypeID()
	{
		return ColorableComponent.COMPONENT_TYPE;
	}
	
	public void mix(GameColor color)
	{
		if(this.segmentCount == this.finishedCount)
			return;
		
		if(!this.currentColor.isTransparent())
		{
			color.mix(color);
		}else
		{
			this.currentColor = color.clone();
		}
		
		//test auf target farbe
		if(this.targetColor.equals(this.currentColor))
		{
			this.segmentCount++;
			if(this.segmentCount == this.finishedCount)
			{
				((EnemyBehaviorComponent)this.getOwner().getComponent(EnemyBehaviorComponent.COMPONENT_TYPE)).setHostile(false);
				EntityManager.getInstance().getEntity(this.notifyOnClear.getID()).message(new ColorableClearMessage());
				//this.friendlyTimer = this.friendlyDuration;
			}else
			{
				this.remainingTimeToFade = this.fadeTime;
			}
		}else
		{
			this.remainingTimeToFade = this.fadeTime;
		}
	}

	public float getFadeTime()
	{
		return fadeTime;
	}

	public void setFadeTime(float fadeTime)
	{
		this.fadeTime = fadeTime;
	}

	public float getFriendlyDuration()
	{
		return friendlyDuration;
	}

	public void setFriendlyDuration(float friendlyDuration)
	{
		this.friendlyDuration = friendlyDuration;
	}

	public int getSegmentCount()
	{
		return segmentCount;
	}

	public void setSegmentCount(int segmentCount)
	{
		this.segmentCount = segmentCount;
	}

	public GameColor getTargetColor()
	{
		return targetColor;
	}

	public GameColor getCurrentColor()
	{
		return currentColor;
	}

	public float getRemainingTimeToFade()
	{
		return remainingTimeToFade;
	}

	public float getFriendlyTimer()
	{
		return friendlyTimer;
	}

	public int getFinishedCount()
	{
		return finishedCount;
	}
	
	
}
