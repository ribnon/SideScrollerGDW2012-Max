package gdw.gameplay.color;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.EntityReference;
import gdw.gameplay.GameColor;

public class ColorableComponentTemplate extends ComponentTemplate
{

	private GameColor targetColor;
	private GameColor currentColor;
	private float remainingTimeToFade;
	private float fadeTime;
	private float friendlyDuration;
	private float friendlyTimer;
	private int finishedCount;
	private int segmentCount;
	private EntityReference notifyOnClear;

	protected ColorableComponentTemplate(HashMap<String, String> params)
	{
		super(params);
		String bit = getStringParam("targetColor", "0;0;0");
		String[] bitArr = bit.split(";");

		if (bitArr.length == 3)
		{
			targetColor = new GameColor(bitArr[0].equals("1"),
					bitArr[1].equals("1"), bitArr[2].equals("1"));
		} else
		{
			targetColor = new GameColor();
		}

		bit = getStringParam("currentColor", "0;0;0");
		bitArr = bit.split(";");

		if (bitArr.length == 3)
		{
			currentColor = new GameColor(bitArr[0].equals("1"),
					bitArr[1].equals("1"), bitArr[2].equals("1"));
		} else
		{
			currentColor = new GameColor();
		}

		remainingTimeToFade = getFloatParam("remainingTimeToFade", 0.0f);
		fadeTime = getFloatParam("fadeTime", 1.0f);
		friendlyDuration = getFloatParam("friendlyDuration", 1.0f);
		friendlyTimer = getFloatParam("friendlyTimer", 1.0f);
		finishedCount = getIntegerParam("finishedCount", 0);
		segmentCount = getIntegerParam("segmentCount", 0);
		notifyOnClear = getEntityReferenceParam("notifyOnClear");
	}

	@Override
	public Component createComponent()
	{
		return new ColorableComponent(this);
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

	public float getFadeTime()
	{
		return fadeTime;
	}

	public float getFriendlyDuration()
	{
		return friendlyDuration;
	}


	public float getFriendlyTimer()
	{
		return friendlyTimer;
	}

	public int getFinishedCount()
	{
		return finishedCount;
	}

	public int getSegmentCount()
	{
		return segmentCount;
	}

	public EntityReference getNotifyOnClear()
	{
		return notifyOnClear;
	}
	
	
}
