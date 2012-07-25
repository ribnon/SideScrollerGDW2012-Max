package gdw.gameplay.color;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.gameplay.GameColor;

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

	protected ColorableComponent(ComponentTemplate template)
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
		}
	}

	public void tick(float deltaTime)
	{
		if(finishedCount == segmentCount)
		{
			//wenn komplett
			friendlyTimer -= deltaTime;
			if(friendlyTimer <= 0.0f)
			{
				//verlier ein segment
				segmentCount--;
				//((EnemyBehaviorCompoent)this.getOwner().getComponent(EnemyBehaviorComponent.COMPONENT_TYPE)).
				//TODO set enemy hostile true
				
			}
		}else if(!currentColor.isTransparent())
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
				//TODO set enemy auf no hostile
				this.friendlyTimer = this.friendlyDuration;
			}else
			{
				this.remainingTimeToFade = this.fadeTime;
			}
		}else
		{
			this.remainingTimeToFade = this.fadeTime;
		}
	}
}
