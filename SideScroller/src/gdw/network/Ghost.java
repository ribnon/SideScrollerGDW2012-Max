package gdw.network;

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
	
	public Ghost(float posX, float posY)
	{
		this.posX = posX;
		this.posY = posY;
		
		this.velocityX = 0.0f;
		this.velocityY = 0.0f;
		
		this.gotThing = NetSubSystem.instance().isServer();
		
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
			
			float lerpFactor = this.remaingSteps;
			
			//interpolate...
			
		}
	}
	
	public boolean compareWithThing(int myEntityID)
	{
		if(!this.gotThing)
			return true;
		/*Entity thing = EntityManager.instance().getEntity(myEntityID);
		float offsetX = Math.abs(thing.getX()-this.posX);
		float offsetY = Math.abs(thing.getY()-this.posY);
		
		float offset = offsetX * offsetX + offsetY * offsetY;
		
		*/
		
		//debug
			float offset = 0;
		//debug
		return offset <= MAX_THRESHOLD_INQUAD;
		
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
	
	
}
