package gdw.gameplay.color;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.gameplay.GameColor;

public class ColorableComponent extends Component
{

	public static final int COMPONENT_TYPE = 22;

	private GameColor targetColor;
	private GameColor currentColor;
	private float remainingTimeToFade;
	private float fadeTime;
	private float friendlyDuration;
	private float friendlyTimer;
	private int finishedCount;
	private int segmentCount;

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
		
		//wenn finischedCounter = segmentCounter  := setzte enemy auf hostile false
	}

	@Override
	public int getComponentTypeID()
	{
		return COMPONENT_TYPE;
	}
	
	public void mix(GameColor color)
	{
		
	}
}
