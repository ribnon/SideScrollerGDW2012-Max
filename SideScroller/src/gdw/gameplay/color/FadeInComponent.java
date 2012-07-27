package gdw.gameplay.color;

import org.newdawn.slick.Color;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Message;
import gdw.graphics.SpriteComponent;

public class FadeInComponent extends Component {

	private Color startColor;
	private Color endColor;
	private float fadeTime;
	private float fadeProgress;
	
	public static final int COMPONENT_TYPE = 28;
	
	public FadeInComponent(ComponentTemplate template) {
		super(template);
		FadeInComponentTemplate t = (FadeInComponentTemplate)template;
		fadeProgress = t.getFadeProgress();
		fadeTime = t.getFadeTime();
		
		Color origColor = t.getStartColor();
		startColor = new Color(origColor.r,origColor.g,origColor.b,origColor.a);
		
		origColor = t.getEndColor();
		endColor = new Color(origColor.r,origColor.g,origColor.b,origColor.a);
		
		
	}

	@Override
	public int getComponentTypeID() {
		// TODO Auto-generated method stub
		return COMPONENT_TYPE;
	}
	
	@Override
	public void onMessage(Message msg) {
		
	}
	
	@Override
	public void tick(float deltaTime) {
		SpriteComponent sprComp = (SpriteComponent) this.getOwner().getComponent(SpriteComponent.COMPONENT_TYPE);
		if(sprComp != null) {
			Color interpolate = new Color(
					startColor.r + (fadeTime-fadeProgress)*(endColor.r - startColor.r),
					startColor.g + (fadeTime-fadeProgress)*(endColor.g - startColor.g),
					startColor.b + (fadeTime-fadeProgress)*(endColor.b - startColor.b),
					startColor.a + (fadeTime-fadeProgress)*(endColor.a - startColor.a)
					);
			sprComp.setFilter(interpolate);
		}
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
