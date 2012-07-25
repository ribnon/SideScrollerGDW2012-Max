package gdw.gameplay.Player;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;


public class PlayerBehaviorComponentTemplate extends ComponentTemplate {

	private float healthPercent;
	private float healthIncrement;
	
	private float deathTimer;
	private float deathTimerDuration;
	
	private float hitDuration;
	private float hitActive;
	
	public float getHealthPercent() {
		return healthPercent;
	}

	public float getHealthIncrement() {
		return healthIncrement;
	}

	public float getDeathTimer() {
		return deathTimer;
	}

	public float getDeathTimerDuration() {
		return deathTimerDuration;
	}

	public float getHitDuration() {
		return hitDuration;
	}

	public float getHitActive() {
		return hitActive;
	}

	protected PlayerBehaviorComponentTemplate(HashMap<String, String> params) {
		super(params);
		healthPercent = getFloatParam("HealthPercent");
		healthIncrement = getFloatParam("HealthIncrement");
		deathTimer = getFloatParam("DeathTimer");
		deathTimerDuration = getFloatParam("DeathTimerDuration");
		hitDuration = getFloatParam("HitDuration");
		hitActive = getFloatParam("HitActive");
	}

	@Override
	public Component createComponent() {
		return new PlayerBehaviorComponent(this);
	}

}
