package gdw.network;

public class Ghost
{
	private static final float MAX_THRESHOLD = 1.0f;
	
	private float posX;
	private float posY;
	
	private float velocityX;
	private float velocityY;
	
	public final boolean gotThing;
	
	public Ghost(float posX, float posY)
	{
		this.posX = posX;
		this.posY = posY;
		
		this.velocityX = 0.0f;
		this.velocityY = 0.0f;
		
		this.gotThing = NetSubSystem.instance().isServer();
	}
	
	public void simulate(float deltaT)
	{
		this.posX += deltaT * velocityX;
		this.posY += deltaT * velocityY;
	}
	
	public boolean compareWithThing()
	{
		if(!this.gotThing)
			return false;
		
	}
	
	public void correct(float posX, float posY, float velocityX, float velocityY, float roundTip)
	{
		this.posX = posX;
		this.posY = posY;
		
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		
		this.simulate(roundTip);	
		
		//TODO falsch interpoliert eventuell 2. set an attributen
	}
	
	
}
