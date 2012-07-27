package gdw.gameplay.color;

import java.nio.FloatBuffer;
import java.util.HashMap;

import org.newdawn.slick.Color;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class FadeInComponentTemplate extends ComponentTemplate {

	private Color startColor;
	private Color endColor;
	private float fadeTime;
	private float fadeProgress;
	
	public FadeInComponentTemplate(HashMap<String, String> params) {
		super(params);
		
		fadeTime = getFloatParam("fadeTime",0.0f);
		fadeProgress = getFloatParam("fadeProgress", 0.0f);
		
		String[] colors = getStringParam("startColor","0;0;0;0").split(";");
		
		startColor = new Color(
				Integer.valueOf(colors[0]),
				Integer.valueOf(colors[1]), 
				Integer.valueOf(colors[2]),
				Integer.valueOf(colors[3])
				);
		
		colors = getStringParam("endColor","255;255;255;255").split(";");
		
		endColor = new Color(
				Integer.valueOf(colors[0]),
				Integer.valueOf(colors[1]), 
				Integer.valueOf(colors[2]),
				Integer.valueOf(colors[3])
				); 
		
	}

	@Override
	public Component createComponent() {
		// TODO Auto-generated method stub
		return new FadeInComponent(this);
	}

	
	

	public Color getStartColor() {
		return startColor;
	}

	public void setStartColor(Color startColor) {
		this.startColor = startColor;
	}

	public Color getEndColor() {
		return endColor;
	}

	public void setEndColor(Color endColor) {
		this.endColor = endColor;
	}

	public float getFadeTime() {
		return fadeTime;
	}

	public void setFadeTime(float fadeTime) {
		this.fadeTime = fadeTime;
	}

	public float getFadeProgress() {
		return fadeProgress;
	}

	public void setFadeProgress(float fadeProgress) {
		this.fadeProgress = fadeProgress;
	}


}
