package gdw.network;

import gdw.entityCore.Entity;

public class Ghost
{
	private static final float MAX_THRESHOLD_INQUAD = 1.0f;
	private static final float INTERPOLATE_TIME = 500f;
	
	private float posX;
	private float posY;
	
	private float posThingX;
	private float posThingY;
	
	
	private float velocityX;
	private float velocityY;
	
	private float velocityThingX;
	private float velocityThingY;
	
	public final boolean gotThing;
	
	private float remaingSteps;
	
	public Ghost()
	{
		this.posX = 0.0f;
		this.posY = 0.0f;
		
		this.velocityX = 0.0f;
		this.velocityY = 0.0f;
		
		this.gotThing = NetSubSystem.getInstance().isServer();
		
		this.posThingX = 0.0f;
		this.posThingY = 0.0f;
		
		this.velocityThingX = 0.0f;
		this.velocityThingY = 0.0f;
		
		this.remaingSteps = 0.0f;
		
	}
	
	
	public void simulate(float deltaT)
	{
		this.posX += deltaT * velocityX;
		this.posY += deltaT * velocityY;
		
		if(this.remaingSteps > 0.0f)
		{
			//simulate thingdata
			this.posThingX += this.velocityThingX * deltaT;
			this.posThingY += this.velocityThingY * deltaT;
			
			this.remaingSteps -= deltaT;
			if(this.remaingSteps < 0.0f)
			{
				this.remaingSteps = 0.0f;
			}
			float lerpFactor = (INTERPOLATE_TIME - this.remaingSteps)/ INTERPOLATE_TIME;
			
			//interpolate...
			this.posX = this.posX * (1.0f -lerpFactor) + this.posThingX * lerpFactor;
			this.posY = this.posY * (1.0f -lerpFactor) + this.posThingY * lerpFactor;
			
			this.velocityX = this.velocityX *(1.0f-lerpFactor) + this.velocityX * lerpFactor;
			this.velocityY = this.velocityY *(1.0f-lerpFactor) + this.velocityY * lerpFactor;
		}
	}
	
	public boolean checkAgainstThing(Entity ent)
	{
		if(!this.gotThing)
			return false;
		float offsetX = Math.abs(ent.getPosX()-this.posX);
		float offsetY = Math.abs(ent.getPosY()-this.posY);
		
		float offset = offsetX * offsetX + offsetY * offsetY;
		
		return offset > MAX_THRESHOLD_INQUAD;
		
	}
	
	public void correct(float posX, float posY, float velocityX, float velocityY, float roundTip)
	{
		this.posThingX = posX;
		this.posThingY = posY;
		
		this.velocityThingX = velocityX;
		this.velocityThingY = velocityY;
		
		this.posThingX += velocityX * roundTip;
		this.posThingY += velocityY * roundTip;
		
		this.remaingSteps = INTERPOLATE_TIME;
	}
	
	public void initialise(float x, float y)
	{
		this.posX = x;
		this.posY = y;
	}
	
	public Ghost clone()
	{
		Ghost result = new Ghost();
		result.posThingX = this.posThingX;
		result.posThingY = this.posThingY;
		result.posX = this.posX;
		result.posY = this.posY;
		result.velocityThingX = this.velocityThingX;
		result.velocityThingY = this.velocityThingY;
		result.velocityX = this.velocityX;
		result.velocityY = this.velocityY;
		return result;
	}
	
	
}
