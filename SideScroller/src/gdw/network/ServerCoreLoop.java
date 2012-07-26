package gdw.network;

public class ServerCoreLoop extends Thread
{
	
	private final long SLEEPCONST = 250L;
	
	private SideScrollerServer ref;
	
	public ServerCoreLoop(SideScrollerServer ref)
	{
		this.ref = ref;
	}
	
	@Override
	public void run()
	{
		long oldVal = System.currentTimeMillis();

		while(!this.isInterrupted())
		{
			if(this.ref.getCurState()== SideScrollerServer.ServerGameStates.WAITING)
			{
				try
				{
					sleep(SLEEPCONST);
				} catch (InterruptedException e)
				{
					return;
				}
			}else if(this.ref.getCurState() == SideScrollerServer.ServerGameStates.START)
			{
				//init
				this.ref.startComplete();
			}
			long curVal = System.currentTimeMillis();
			float delta = curVal -  oldVal;
			
			//updates laufen lassen
			
				
				
		}
	}
	
}
