package gdw.gameplay.color;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.gameplay.GameColor;

public class ColorableComponentTemplate extends ComponentTemplate {

	private GameColor targetColor;
	private GameColor currentColor;
	private float remainingTimeToFade;
	private float fadeTime;
	private float friendlyDuration;
	private float friendlyTimer;
	private int   finishedCount;
	private int   segmentCount;
	
	protected ColorableComponentTemplate(HashMap<String, String> params)
	{
		super(params);
		
		
		
		
		String bit = getStringParam("targetColor", "000");
		
		targetColor = new GameColor(bit.charAt(0) == '1', bit.charAt(1) == '1', bit.charAt(2) == '1');
		
		bit = getStringParam("currentColor", "000");
		
		currentColor = new GameColor(bit.charAt(0) == '1', bit.charAt(1) == '1', bit.charAt(2) == '1');
		
		
		
		remainingTimeToFade = getFloatParam("remainingTimeToFade", 0.0f);
		fadeTime = getFloatParam("fadeTime", 1.0f);
		friendlyDuration = getFloatParam("friendlyDuration", 1.0f);
		friendlyTimer = getFloatParam("friendlyTimer", 1.0f);
		finishedCount = getIntegerParam("finishedCount", 0);
		segmentCount = getIntegerParam("segmentCount", 0);
	}
	
	@Override
	public Component createComponent() {
		// TODO Auto-generated method stub
		return new ColorableComponent(this);
	}

	public GameColor getTargetColor() {
		return targetColor;
	}

	public void setTargetColor(GameColor targetColor) {
		this.targetColor = targetColor;
	}

	public GameColor getCurrentColor() {
		return currentColor;
	}

	public void setCurrentColor(GameColor currentColor) {
		this.currentColor = currentColor;
	}

	public float getRemainingTimeToFade() {
		return remainingTimeToFade;
	}

	public void setRemainingTimeToFade(float remainingTimeToFade) {
		this.remainingTimeToFade = remainingTimeToFade;
	}

	public float getFadeTime() {
		return fadeTime;
	}

	public void setFadeTime(float fadeTime) {
		this.fadeTime = fadeTime;
	}

	public float getFriendlyDuration() {
		return friendlyDuration;
	}

	public void setFriendlyDuration(float friendlyDuration) {
		this.friendlyDuration = friendlyDuration;
	}

	public float getFriendlyTimer() {
		return friendlyTimer;
	}

	public void setFriendlyTimer(float friendlyTimer) {
		this.friendlyTimer = friendlyTimer;
	}

	public int getFinishedCount() {
		return finishedCount;
	}

	public void setFinishedCount(int finishedCount) {
		this.finishedCount = finishedCount;
	}

	public int getSegmentCount() {
		return segmentCount;
	}

	public void setSegmentCount(int segmentCount) {
		this.segmentCount = segmentCount;
	}
}
